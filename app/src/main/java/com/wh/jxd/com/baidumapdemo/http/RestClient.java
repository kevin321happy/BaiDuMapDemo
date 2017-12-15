package com.wh.jxd.com.baidumapdemo.http;

import android.content.Context;

import com.wh.jxd.com.baidumapdemo.http.callback.IError;
import com.wh.jxd.com.baidumapdemo.http.callback.IFailure;
import com.wh.jxd.com.baidumapdemo.http.callback.IRequest;
import com.wh.jxd.com.baidumapdemo.http.callback.ISuccess;
import com.wh.jxd.com.baidumapdemo.ui.loader.LoadStyle;
import com.wh.jxd.com.baidumapdemo.ui.loader.LyonLoader;
import com.wh.jxd.com.baidumapdemo.utils.ToastUtils;

import java.io.File;
import java.util.WeakHashMap;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by kevin321vip on 2017/12/14.
 * 联网请求的客户端
 */

public class RestClient {
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoadStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    public RestClient(String URL, IRequest REQUEST, String DOWNLOAD_DIR, String EXTENSION, String NAME, ISuccess SUCCESS, IFailure FAILURE, IError ERROR, RequestBody BODY, LoadStyle LOADER_STYLE, File FILE, Context CONTEXT) {
        this.URL = URL;
        this.REQUEST = REQUEST;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.BODY = BODY;
        this.LOADER_STYLE = LOADER_STYLE;
        this.FILE = FILE;
        this.CONTEXT = CONTEXT;
    }

    /**
     * 通过建造者模式传参
     */
    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    /**
     * 发起网络请求
     */
    public void request(HttpMethod method) {
        final RestService service = RestCreator.getService();
        Observable<String> observable = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE != null) {
            LyonLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                service.post(URL, PARAMS);
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
//                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.post(URL, PARAMS);
                break;
            case PUT_RAW:
                service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }
        if (observable != null) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(String s) {
                            if (SUCCESS != null) {
                                SUCCESS.onSuccess(s);
                            }
                            LyonLoader.stopLoading();
                            ToastUtils.show("请求成功" + s);
                        }

                        @Override
                        public void onError(Throwable e) {
                            LyonLoader.stopLoading();
                            if (ERROR != null) {
                                ERROR.onError(0, e.getMessage());
                            }
                            ToastUtils.show("请求失败" + e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        }
    }

    /**
     * get方法
     */
    public void get() {
        request(HttpMethod.GET);
    }

    /**
     * post方法
     */
    public void post() {
        request(HttpMethod.POST);
    }
}

