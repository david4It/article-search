package com.scsme.zwy.articlesearch.commonJson;

import lombok.ToString;

/**
 * Created by Administrator on 2017/5/24.
 */
@ToString
public class Info {
    /**
     * 模块
     */
    private String type;
    /**
     * 请求功能
     */
    private String action;
    /**
     * APP校验码
     */
    private String appkey;
    /**
     * APP用户来源
     */
    private String usource;
    /**
     * 用户校验规则
     */
    private String token;
    /**
     * 其它校验码
     */
    private String checkcode;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 需要跳转地址或者请求来源地址等
     */
    private String url;
    /**
     * 处理的结果（参见FinalJson中的定义）
     */
    private Integer status;
    /**
     * 附加信息，错误等情况下将原因返回.如果是添加，将新主键保存到此信息
     */
    private String message;
    /**
     * 版本信息。部分数据可能需要版本信息进行判断
     */
    private String version;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getUsource() {
        return usource;
    }

    public void setUsource(String usource) {
        this.usource = usource;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
