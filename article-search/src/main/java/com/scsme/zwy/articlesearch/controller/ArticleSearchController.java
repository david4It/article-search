package com.scsme.zwy.articlesearch.controller;

import com.scsme.zwy.articlesearch.commonJson.ComJsonUtil;
import com.scsme.zwy.articlesearch.commonJson.ExtLimit;
import com.scsme.zwy.articlesearch.commonJson.FinalJson;
import com.scsme.zwy.articlesearch.service.ArticleSearchService;
import com.scsme.zwy.articlesearch.util.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ArticleSearchController {
    @Autowired ArticleSearchService service;

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
