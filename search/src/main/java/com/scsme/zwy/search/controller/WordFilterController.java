package com.scsme.zwy.search.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.scsme.zwy.search.commonJson.ComJsonUtil;
import com.scsme.zwy.search.commonJson.FinalJson;
import com.scsme.zwy.search.entity.Policy;
import com.scsme.zwy.search.service.WordFilterService;
import com.scsme.zwy.search.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class WordFilterController {
    private static final String CONTENT_FIELD = "content";
    @Autowired WordFilterService wordFilterService;
  @PostMapping("/wordFilter")
  public ComJsonUtil wordFilter(@RequestBody ComJsonUtil comJsonUtil) throws Exception {
    ComJsonUtil jUBean =
        JsonMapper.string2Obj(
            JsonMapper.obj2String(comJsonUtil), new TypeReference<ComJsonUtil>() {});
    Map<String, Object> requestMap = (Map) jUBean.getData();

      if (requestMap == null || requestMap.size() == 0) {
          log.info("Request map is empty or null");
          jUBean.getInfo().setMessage("查询数据为空");
          jUBean.getInfo().setStatus(FinalJson.STATUS_NOTACCEPTABLE);
      } else {
          List<String> results = wordFilterService.replaceContent((List<String>)requestMap.get(CONTENT_FIELD));
          jUBean.setData(results);
      }
      return jUBean;
  }
}
