package com.scsme.zwy.search.controller;

import com.scsme.zwy.search.commonJson.ComJsonUtil;
import com.scsme.zwy.search.commonJson.ExtLimit;
import com.scsme.zwy.search.commonJson.FinalJson;
import com.scsme.zwy.search.service.SearchService;
import com.scsme.zwy.search.util.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SearchController {
    @Autowired SearchService service;

    @PostMapping("/article")
    public ComJsonUtil searchArticle(@RequestBody ComJsonUtil comJsonUtil) throws Exception{
        ComJsonUtil jUBean = JsonMapper.string2Obj(JsonMapper.obj2String(comJsonUtil),new TypeReference<ComJsonUtil>() {});
        Map<String,Object> requestMap = (Map)jUBean.getData();
        ExtLimit limit = jUBean.getExtlimit();
        if (requestMap == null || requestMap.size() == 0) {
            comJsonUtil.getInfo().setMessage("查询数据为空");
            comJsonUtil.getInfo().setStatus(FinalJson.STATUS_NOTACCEPTABLE);
        } else {
            JSONObject jsonObject = service.findAllArticleByElasticsearch(requestMap, limit);
        }
        return comJsonUtil;
    }
}
