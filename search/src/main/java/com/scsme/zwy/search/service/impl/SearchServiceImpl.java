package com.scsme.zwy.search.service.impl;

import com.scsme.zwy.search.commonJson.ExtLimit;
import com.scsme.zwy.search.entity.Policy;
import com.scsme.zwy.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("searchService")
public class SearchServiceImpl implements SearchService {
  private static final String KEYWORD_SUFFIX = ".keyword";
  private static final String HIGH_LIGHT_PRE_TAGS = "<span style='color:red'>";
  private static final String HIGH_LIGHT_POST_TAGS = "</span>";
  private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  @Autowired ElasticsearchTemplate elasticsearchTemplate;

  @Override
  public List<Policy> findPolicyByElasticsearch(Map<String, Object> requestMap, ExtLimit limit)
      throws Exception {
    List<Policy> result = new ArrayList<>();
    String queryValue = (String) requestMap.get(Policy.CONTENT);
    List<String> queryTagList = (List<String>) requestMap.get(Policy.TAGS);
    log.info("Query content is ::::: {}", queryValue);
    log.info("Query tag list is ::::: {}", queryTagList);
    BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
    // 根据标签搜索
    if (queryTagList != null && queryTagList.size() > 0) {
      boolQueryBuilder.must(new TermsQueryBuilder(Policy.TAGS + KEYWORD_SUFFIX, queryTagList));
    }
    // 根据输入内容搜索(标题和内容)
    if (!StringUtils.isEmpty(queryValue)) {
      BoolQueryBuilder contentQuery = new BoolQueryBuilder();
      contentQuery.should(new TermQueryBuilder(Policy.CONTENT, queryValue));
      contentQuery.should(new TermQueryBuilder(Policy.TITLE, queryValue));
      boolQueryBuilder.must(contentQuery);
    }
    SearchQuery searchQuery =
        new NativeSearchQueryBuilder()
            .withQuery(boolQueryBuilder)
            .withHighlightBuilder(
                new HighlightBuilder().preTags(HIGH_LIGHT_PRE_TAGS).postTags(HIGH_LIGHT_POST_TAGS))
            .withHighlightFields(
                new HighlightBuilder.Field(Policy.TITLE),
                new HighlightBuilder.Field(Policy.CONTENT))
            .withPageable(PageRequest.of(limit.getPageindex(), limit.getPagesize()))
            .build();
    AggregatedPage<Policy> policies =
        elasticsearchTemplate.queryForPage(
            searchQuery,
            Policy.class,
            new SearchResultMapper() {
              // 重写mapResults方法，在方法中处理highlight内容
              @Override
              public <T> AggregatedPage<T> mapResults(
                  SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                // 返回查询的总数
                limit.setCount(Long.valueOf(searchResponse.getHits().getTotalHits()).intValue());
                List<Policy> chunk = new ArrayList<>();
                for (SearchHit searchHit : searchResponse.getHits()) {
                  if (searchResponse.getHits().getHits().length <= 0) {
                    return null;
                  }
                  // 封装Policy对象
                  Policy policy = new Policy();
                  policy.setReleaseId((Integer) searchHit.getSourceAsMap().get(Policy.RELEASE_ID));
                  policy.setContent((String) searchHit.getSourceAsMap().get(Policy.CONTENT));
                  policy.setTags((List<String>) searchHit.getSourceAsMap().get(Policy.TAGS));
                  policy.setTitle((String) searchHit.getSourceAsMap().get(Policy.TITLE));
                  SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
                  // 处理UTC零时区数据
                  sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                  String updateTimeString =
                      (String) searchHit.getSourceAsMap().get(Policy.UPDATE_TIME);
                  try {
                    policy.setUpdateTime(sdf.parse(updateTimeString));
                  } catch (ParseException e) {
                    log.error("Parse string to date failed");
                    log.error(e.getLocalizedMessage());
                  }
                  // 处理highlight数据
                  Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
                  if (highlightFieldMap != null && highlightFieldMap.size() > 0) {
                    HighlightField contentField = highlightFieldMap.get(Policy.CONTENT);
                    HighlightField titleField = highlightFieldMap.get(Policy.TITLE);
                    if (contentField != null) {
                      policy.setHighlighterContent(contentField.getFragments()[0].string());
                    }
                    if (titleField != null) {
                      policy.setHighlighterTitle(titleField.getFragments()[0].string());
                    }
                  }
                  log.debug("Policy detail info without content ::::: {}", policy.toString());
                  chunk.add(policy);
                }
                if (chunk.size() > 0) {
                  return new AggregatedPageImpl<T>((List<T>) chunk);
                }
                return null;
              }
            });
    // 直接使用elasticsearchTemplate.queryForList方法会调用DefaultResultMapper.populateScriptFields方法对Policy对象的fields进行赋值
    // 然而updateTime在elasticsearch中包含了timezone信息，无法直接被@JsonFormat(pattern="yyyy-MM-dd
    // HH:mm:ss",timezone="GMT+8")处理，这里需要注意
    // List<Policy> temp = elasticsearchTemplate.queryForList(searchQuery, Policy.class);
    if (policies != null && policies.getContent() != null) {
      result = policies.getContent();
    }
    return result;
  }
}
