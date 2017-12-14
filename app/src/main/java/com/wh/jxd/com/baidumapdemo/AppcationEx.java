package com.wh.jxd.com.baidumapdemo;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.wh.jxd.com.baidumapdemo.utils.Utils;

/**
 * Created by kevin321vip on 2017/12/11.
 */

public class AppcationEx extends Application {
    private static AppcationEx mAppcationEx;
    @Override
    public void onCreate() {
        mAppcationEx=this;
        //初始化百度
        SDKInitializer.initialize(this);
        Utils.init(this);
        super.onCreate();
    }
    public static Context getAppContext(){
        return mAppcationEx;
    }
}
