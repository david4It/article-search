package com.scsme.zwy.search.commonJson;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2017-06-13.
 */
@Data
public class ComJsonUtil {
    /**
     * 请求参数
     */
    private Object data;
    private List<Rules> rules;
    /**
     * 公共参数
     */
    private Info info;
    /**
     * 分页数据
     */
    private ExtLimit extlimit;

    /**
     * 其他参数
     */
    private Object other;
}
