package com.wh.jxd.com.baidumapdemo.http;

import com.wh.jxd.com.baidumapdemo.constance.UrlConstance;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by kevin321vip on 2017/12/14.
 */

public class RestCreator {

    /**
     * 构建全局的Retrofit客户端
     * .baseUrl(UrlConstance.BaseUrl)
     * .addConverterFactory(GsonConverterFactory.create())
     * http://gank.io/api/data/Android/10/1
     */
    private static final class RetrofitHodler {
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .client(OkHttpHodler.OK_HTTP_CLIENT)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * OkhttpHodler
     */
    private static final class OkHttpHodler {
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(UrlConstance.TimeOut, TimeUnit.SECONDS)
                .build();
    }

    /**
     * RestService
     */
    private static final class RestServiceHodler {
        private static final RestService REST_SERVICE = RetrofitHodler.RETROFIT_CLIENT.create(RestService.class);
    }

    /**
     * Params的Hodler
     */
    private static final class ParamsHodler {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    /**
     * 获取参数
     *
     * @return
     */
    public static WeakHashMap<String, Object> getParams() {
        return ParamsHodler.PARAMS;
    }

    /**
     * 获取Service
     */
    public static RestService getService() {
        return RestServiceHodler.REST_SERVICE;
    }



}
