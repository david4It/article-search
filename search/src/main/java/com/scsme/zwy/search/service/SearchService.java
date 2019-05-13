package com.scsme.zwy.search.service;

import com.scsme.zwy.search.commonJson.ExtLimit;
import org.json.JSONObject;

import java.util.Map;

public interface SearchService {
    JSONObject findAllArticleByElasticsearch(Map<String, Object> requestMap, ExtLimit limit) throws Exception;
}
