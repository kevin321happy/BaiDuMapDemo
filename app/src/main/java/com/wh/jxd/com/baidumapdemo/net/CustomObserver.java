package com.wh.jxd.com.baidumapdemo.net;

import android.app.Activity;
import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.wh.jxd.com.baidumapdemo.bean.base.BaseResponse;
import com.wh.jxd.com.baidumapdemo.utils.CommonDialogUtils;
import com.wh.jxd.com.baidumapdemo.utils.ToastUtils;


import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.wh.jxd.com.baidumapdemo.net.CustomObserver.ExceptionReason.CONNECT_ERROR;
import static com.wh.jxd.com.baidumapdemo.net.CustomObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.wh.jxd.com.baidumapdemo.net.CustomObserver.ExceptionReason.PARSE_ERROR;
import static com.wh.jxd.com.baidumapdemo.net.CustomObserver.ExceptionReason.UNKNOWN_ERROR;

/**
 * Created by kevin321vip on 2017/12/13.
 * 自定义的观察对象
 * 完成进度加载的控制
 */

public abstract class CustomObserver<T extends BaseResponse> implements Observer<T> {
    private Activity activity;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;
    private CommonDialogUtils mDialogUtils;

    public CustomObserver(Activity activity) {
        this.activity = activity;
        if (mDialogUtils == null) {
            mDialogUtils = new CommonDialogUtils();
            mDialogUtils.showProgress(activity, "Loading....");
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        dismissProgress();
        int code = response.getCode();
        if (code == 200) {
            onSuccess(response);
        } else {
            onFail(response);
        }

    }

    /**
     * 失败
     *
     * @param response
     */
    private void onFail(T response) {
        String message = response.getMessage();
        if (TextUtils.isEmpty(message)) {
            ToastUtils.show("网络连接失败");
        } else {
            ToastUtils.show(message);
        }
    }

    /**
     * 请求成功了
     */
    abstract public void onSuccess(T response);

    @Override
    public void onError(Throwable e) {
        dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    /**
     * 异常提示
     *
     * @param badNetwork
     */
    private void onException(ExceptionReason badNetwork) {
        switch (badNetwork) {
            case UNKNOWN_ERROR:
                ToastUtils.show("未知错误");
                break;
            case BAD_NETWORK:
                ToastUtils.show("网络连接失败");
                break;
            case CONNECT_ERROR:
                ToastUtils.show("连接错误");
                break;
            case PARSE_ERROR:
                ToastUtils.show("解析错误");
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.show("连接超时");
                break;
        }
    }

    @Override
    public void onComplete() {
        dismissProgress();
    }

    /**
     * Loading消失
     */
    private void dismissProgress() {
        if (mDialogUtils != null) {
            if (mDialogUtils.isShowing()) {
                mDialogUtils.dismissProgress();
            }
        }
    }
    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
