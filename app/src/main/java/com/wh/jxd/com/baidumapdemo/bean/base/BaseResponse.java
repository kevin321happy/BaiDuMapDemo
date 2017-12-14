package com.wh.jxd.com.baidumapdemo.bean.base;

/**
 * Created by kevin321vip on 2017/12/13.
 * 服务器响应数据的基类
 */

public class BaseResponse<T> {

    private int code;
    private String message;
    private T content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
