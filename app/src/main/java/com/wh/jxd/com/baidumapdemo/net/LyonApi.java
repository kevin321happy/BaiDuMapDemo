package com.wh.jxd.com.baidumapdemo.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wh.jxd.com.baidumapdemo.utils.LogUtils;
import com.wh.jxd.com.baidumapdemo.utils.NetworkUtils;
import com.wh.jxd.com.baidumapdemo.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kevin321vip on 2017/12/13.
 * 构建retrofit对象
 */

public class LyonApi {
    Retrofit mRetrofit;
    public final LyonApiService Service;

    public LyonApi() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    LogUtils.d("OkHttp:" + text);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtils.d("OkHttp", message);
                }
            }
        });
        //设置拦截等级
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(Utils.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//100M的缓存
        //设置拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(LyonApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(LyonApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(LyonApiService.API_SERVER_URL)
                .build();
        Service = retrofit.create(LyonApiService.class);
    }

    /**
     * 静态内部类形式的单例
     */
    private static class RetrofitHodler {
        private static final LyonApi INSTANCE = new LyonApi();
    }

    public static LyonApiService getApiService() {
        return RetrofitHodler.INSTANCE.Service;
    }


    class HttpCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtils.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                //有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
