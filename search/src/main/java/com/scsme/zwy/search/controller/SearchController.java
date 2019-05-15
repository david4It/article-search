package com.scsme.zwy.search.controller;

import com.scsme.zwy.search.commonJson.ComJsonUtil;
import com.scsme.zwy.search.commonJson.ExtLimit;
import com.scsme.zwy.search.commonJson.FinalJson;
import com.scsme.zwy.search.entity.Policy;
import com.scsme.zwy.search.service.SearchService;
import com.scsme.zwy.search.util.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
public class SearchController {
    @Autowired SearchService service;

    @PostMapping("/policy")
    public ComJsonUtil searchPolicy(@RequestBody ComJsonUtil comJsonUtil) throws Exception{
        ComJsonUtil jUBean = JsonMapper.string2Obj(JsonMapper.obj2String(comJsonUtil),new TypeReference<ComJsonUtil>() {});
        Map<String,Object> requestMap = (Map)jUBean.getData();
        ExtLimit limit = jUBean.getExtlimit();
        if (requestMap == null || requestMap.size() == 0) {
            log.info("Request map is empty or null");
            jUBean.getInfo().setMessage("查询数据为空");
            jUBean.getInfo().setStatus(FinalJson.STATUS_NOTACCEPTABLE);
        } else {
            List<Policy> policies = service.findPolicyByElasticsearch(requestMap, limit);
            jUBean.setData(policies);
        }
        return jUBean;
    }
}
