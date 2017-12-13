package com.wh.jxd.com.baidumapdemo;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by kevin321vip on 2017/12/11.
 */

public class AppcationEx extends Application {
    @Override
    public void onCreate() {
        //初始化百度
        SDKInitializer.initialize(this);
        super.onCreate();
    }
}
