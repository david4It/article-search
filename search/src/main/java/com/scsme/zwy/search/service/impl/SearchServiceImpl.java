package com.scsme.zwy.search.service.impl;

import com.scsme.zwy.search.commonJson.ExtLimit;
import com.scsme.zwy.search.entity.Article;
import com.scsme.zwy.search.service.SearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service("searchService")
public class SearchServiceImpl implements SearchService
{
    private static final String QUERY_FIELD_KEY="content";
    private static final String TAGS_FIELD_KEY="tags";

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Override
    public JSONObject findAllArticleByElasticsearch(Map<String,Object> requestMap, ExtLimit limit) throws Exception {
        String queryValue = requestMap.get(QUERY_FIELD_KEY).toString();
        List<String> queryTagList = (List<String>)requestMap.get(TAGS_FIELD_KEY);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(new BoolQueryBuilder()
                .must(new TermsQueryBuilder(TAGS_FIELD_KEY, queryTagList))
                .must(new TermsQueryBuilder(QUERY_FIELD_KEY, queryValue)))
                .withHighlightBuilder(new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>"))
                .withHighlightFields(new HighlightBuilder.Field(QUERY_FIELD_KEY))
                .withPageable(PageRequest.of(limit.getPageindex(), limit.getPagesize())).build();
        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        System.out.printf("xxxxx");
        return null;
    }
}
