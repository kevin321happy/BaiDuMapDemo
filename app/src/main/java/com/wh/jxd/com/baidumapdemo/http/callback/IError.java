package com.wh.jxd.com.baidumapdemo.http.callback;

/**
 * Created by kevin321vip on 2017/12/14.
 * 失败的回调接口
 */

public interface IError {
    void onError(int code,String msg);
}
