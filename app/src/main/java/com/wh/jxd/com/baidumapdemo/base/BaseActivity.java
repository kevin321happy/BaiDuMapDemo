package com.wh.jxd.com.baidumapdemo.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.utils.StatusBarUtil;

/**
 * Created by kevin321vip on 2017/12/13.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarColor(this, R.color.transparent);
        //设置状态栏黑色字体
        StatusBarUtil.StatusBarLightMode(this);
    }
}
