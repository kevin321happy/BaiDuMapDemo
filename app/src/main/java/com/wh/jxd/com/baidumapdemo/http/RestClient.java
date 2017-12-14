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

/**
 * Created by kevin321vip on 2017/12/14.
 * 联网请求的客户端
 */

public class RestClient {
    private static final WeakHashMap<Object, String> PARAMS = RestCreator.getParams();
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
    public RestClientBuilder builder() {
        return new RestClientBuilder();
    }
}
