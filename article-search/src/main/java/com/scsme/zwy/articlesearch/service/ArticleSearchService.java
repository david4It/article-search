package com.scsme.zwy.articlesearch.service;

import com.scsme.zwy.articlesearch.commonJson.ExtLimit;
import org.json.JSONObject;

import java.util.Map;

public interface ArticleSearchService {
    JSONObject findAllArticleByElasticsearch(Map<String, Object> requestMap, ExtLimit limit) throws Exception;
}
