package com.wh.jxd.com.baidumapdemo.net;

import com.wh.jxd.com.baidumapdemo.bean.MeiZi;
import com.wh.jxd.com.baidumapdemo.bean.base.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by kevin321vip on 2017/12/13.
 * api接口
 */

public interface LyonApiService {


    public long DEFAULT_TIMEOUT = 20000;
    String HOST = "http://gank.io/";
    String API_SERVER_URL = HOST + "api/data/";


    @GET("福利/10/1")
    Observable<BaseResponse<List<MeiZi>>> getMezi();

    /**
     * @param page
     * @param number
     * @return
     */
    @Headers("Cache-Control: public, max-age=100")//设置缓存 缓存时间为100s
    @GET("everySay/selectAll.do")
    Observable<BaseResponse<List<MeiZi>>> lookBack(@Query("page") int page, @Query("rows") int number);


    @POST("upload/uploadFile.do")
    Observable<BaseResponse> uploadFiles(@Part("filename") String description,
                                          @Part("pic\"; filename=\"image1.png") RequestBody imgs1,
                                          @Part("pic\"; filename=\"image2.png") RequestBody imgs2);

    @POST("upload/uploadFile.do")
    Observable<BaseResponse> uploadFiles(@Part("filename") String description, @PartMap() Map<String, RequestBody> maps);
}
