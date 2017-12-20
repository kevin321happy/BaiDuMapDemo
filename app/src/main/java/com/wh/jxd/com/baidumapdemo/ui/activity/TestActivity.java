package com.wh.jxd.com.baidumapdemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wh.jxd.com.baidumapdemo.R;
import com.wh.jxd.com.baidumapdemo.base.BaseActivity;
import com.wh.jxd.com.baidumapdemo.bean.MeiZi;
import com.wh.jxd.com.baidumapdemo.bean.base.BaseResponse;
import com.wh.jxd.com.baidumapdemo.http.HttpMethod;
import com.wh.jxd.com.baidumapdemo.http.RestClient;
import com.wh.jxd.com.baidumapdemo.http.callback.IError;
import com.wh.jxd.com.baidumapdemo.http.callback.IRequest;
import com.wh.jxd.com.baidumapdemo.http.callback.ISuccess;
import com.wh.jxd.com.baidumapdemo.net.CustomObserver;
import com.wh.jxd.com.baidumapdemo.net.LyonApi;
import com.wh.jxd.com.baidumapdemo.utils.LogUtils;
import com.wh.jxd.com.baidumapdemo.utils.ToastUtils;
import com.wh.jxd.com.baidumapdemo.widget.NavigationBar;

import java.util.List;
import java.util.WeakHashMap;


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
//        initData();
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
    /**
     * 请求网络
     *
     * @param view
     */
    public void request(View view) {
        RestClient restClient = RestClient.builder()
                .url("data/Android/10/1")
                .load(this)
//                .params(new WeakHashMap<String, Object>())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build();
        restClient.request(HttpMethod.GET);

    }
}
