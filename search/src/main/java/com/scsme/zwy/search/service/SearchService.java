package com.scsme.zwy.search.service;

import com.scsme.zwy.search.commonJson.ExtLimit;
import com.scsme.zwy.search.entity.Policy;
import java.util.List;
import java.util.Map;

public interface SearchService {
    List<Policy> findPolicyByElasticsearch(Map<String,Object> requestMap, ExtLimit limit) throws Exception;
}
