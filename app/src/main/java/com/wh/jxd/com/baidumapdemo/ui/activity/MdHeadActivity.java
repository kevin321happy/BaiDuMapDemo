package com.wh.jxd.com.baidumapdemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.base.BaseActivity;
import com.wh.jxd.com.baidumapdemo.bean.ImageItemBean;
import com.wh.jxd.com.baidumapdemo.ui.adapter.OneAdapter;
import com.wh.jxd.com.baidumapdemo.ui.adapter.TwoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin321vip on 2017/12/18.
 * 一个MD风格的界面
 */

public class MdHeadActivity extends BaseActivity {
    private RecyclerView mRcv1;
    private RecyclerView mRcv2;

    private List<ImageItemBean> images = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_head);
        initData();
        initView();
    }
    private void initData() {
        for (int i = 0; i < 10; i++) {
            ImageItemBean itemBean = new ImageItemBean("第：" + i + "张图", R.drawable.mao);
            images.add(itemBean);
        }
    }
    private void initView() {
        mRcv1 = (RecyclerView) findViewById(R.id.rcv1);
        mRcv2 = (RecyclerView) findViewById(R.id.rcv2);
//        mRcv3 = (RecyclerView) findViewById(R.id.rcv3);
//        mRcv4 = (RecyclerView) findViewById(R.id.rcv4);

        OneAdapter oneAdapter = new OneAdapter();
        oneAdapter.setList(images);
        mRcv1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        mRcv1.setAdapter(oneAdapter);

        TwoAdapter twoAdapter = new TwoAdapter();
        twoAdapter.setList(images);
        mRcv2.setLayoutManager(new LinearLayoutManager(this));
        mRcv2.setAdapter(twoAdapter);




//        mRcv1.setAdapter(n);
    }

}
