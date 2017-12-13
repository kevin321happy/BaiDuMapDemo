package com.wh.jxd.com.baidumapdemo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;

import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.base.BaseActivity;
import com.wh.jxd.com.baidumapdemo.widget.NavigationBar;

/**
 * Created by kevin321vip on 2017/12/12.
 */

public class ProjectDetailActivity extends BaseActivity {

    private NavigationBar mNb_title;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        mNb_title = (NavigationBar) findViewById(R.id.nb_titls);
        mNb_title.setTitle("智慧发改");
        mNb_title.setLeftImage(R.drawable.fanhui);
        mNb_title.setRightButton(R.drawable.iv_search);
        mNb_title.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button == NavigationBar.LEFT_VIEW) {
                    finish();
                }
            }
        });
    }
}
