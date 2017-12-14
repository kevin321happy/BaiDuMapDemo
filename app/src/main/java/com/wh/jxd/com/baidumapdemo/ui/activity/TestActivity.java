package com.wh.jxd.com.baidumapdemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.base.BaseActivity;
import com.wh.jxd.com.baidumapdemo.bean.MeiZi;
import com.wh.jxd.com.baidumapdemo.bean.base.BaseResponse;
import com.wh.jxd.com.baidumapdemo.net.CustomObserver;
import com.wh.jxd.com.baidumapdemo.net.LyonApi;
import com.wh.jxd.com.baidumapdemo.utils.LogUtils;
import com.wh.jxd.com.baidumapdemo.utils.ToastUtils;
import com.wh.jxd.com.baidumapdemo.widget.NavigationBar;

import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by kevin321vip on 2017/12/14.
 */

public class TestActivity extends BaseActivity {

    private NavigationBar mNb_test;
    private TextView mTv_content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        mNb_test = (NavigationBar) findViewById(R.id.na_test);
        mNb_test.setTitle("测试数据");
        mNb_test.setLeftImage(R.drawable.fanhui);
        mNb_test.setListener(new NavigationBar.NavigationListener() {
            @Override
            public void onButtonClick(int button) {
                if (button == NavigationBar.LEFT_VIEW) {
                    finish();
                }
            }
        });
        mTv_content = (TextView) findViewById(R.id.tv_content);
    }

    private void initData() {
//        IdeaApi.getApiService()
//                .getMezi()
//                .compose(this.<BasicResponse<List<MeiZi>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultObserver<BasicResponse<List<MeiZi>>>(this) {
//                    @Override
//                    public void onSuccess(BasicResponse<List<MeiZi>> response) {
//                        List<MeiZi> results = response.getResults();
//                        showToast("请求成功，妹子个数为"+results.size());
//                    }
//                });
        LyonApi.getApiService()
                .getMezi()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomObserver<BaseResponse<List<MeiZi>>>(this) {
                    @Override
                    public void onNext(BaseResponse<List<MeiZi>> response) {
                        super.onNext(response);
                        List<MeiZi> content = response.getContent();
                        if (content != null && content.size() > 0) {
                            for (MeiZi meiZi : content) {
                                LogUtils.d("妹子信息：" + content.toString());
                            }
                        } else {
                            ToastUtils.show("数据为空的....");
                        }
                    }

                    @Override
                    public void onSuccess(BaseResponse<List<MeiZi>> response) {

                        if (mTv_content != null) {
                            mTv_content.setText(response.toString());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });

    }
}
