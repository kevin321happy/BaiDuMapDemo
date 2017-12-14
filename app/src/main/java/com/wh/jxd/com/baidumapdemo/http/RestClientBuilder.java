package com.wh.jxd.com.baidumapdemo.http;

import android.content.Context;

import com.wh.jxd.com.baidumapdemo.http.callback.IError;
import com.wh.jxd.com.baidumapdemo.http.callback.IFailure;
import com.wh.jxd.com.baidumapdemo.http.callback.IRequest;
import com.wh.jxd.com.baidumapdemo.http.callback.ISuccess;
import com.wh.jxd.com.baidumapdemo.ui.loader.LoadStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by kevin321vip on 2017/12/14.
 * RestClient的构建者
 */

public class RestClientBuilder {
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private String URL = null;
    private IRequest REQUEST = null;
    private String DOWNLOAD_DIR = null;
    private String EXTENSION = null;
    private String NAME = null;
    private ISuccess SUCCESS = null;
    private IFailure FAILURE = null;
    private IError ERROR = null;
    private RequestBody BODY = null;
    private LoadStyle LOADER_STYLE = null;
    private File FILE = null;
    private Context CONTEXT = null;

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder url(String url) {
        this.URL = url;
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest) {
        this.REQUEST = iRequest;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.FAILURE = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.ERROR = iError;
        return this;
    }

    public final RestClientBuilder dir(String downloaddir) {
        this.DOWNLOAD_DIR = downloaddir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.EXTENSION = extension;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.NAME = name;
        return this;
    }

    public final RestClientBuilder body(RequestBody body) {
        this.BODY = body;
        return this;
    }

    public final RestClientBuilder LoadStyle(LoadStyle loadStyle) {
        this.LOADER_STYLE = loadStyle;
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.FILE = file;
        return this;
    }

    public final RestClientBuilder load(Context context, LoadStyle loadStyle) {
        this.CONTEXT = context;
        this.LOADER_STYLE = loadStyle;
        return this;
    }

    public final RestClientBuilder load(Context context) {
        this.CONTEXT = context;
        this.LOADER_STYLE = LoadStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(URL, REQUEST, DOWNLOAD_DIR
                , EXTENSION, NAME, SUCCESS, FAILURE, ERROR, BODY, LOADER_STYLE
                , FILE, CONTEXT);
    }
}
