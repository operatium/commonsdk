package com.qicaixiong.reader.resource.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 网络接口
 * Created by admin on 2018/8/31.
 */

public interface RequestService {

    @GET("/api/v1/booksDetail")
    @Headers({
            "clientType:android",
            "Cache-Control:public, max-age=86400, max-stale=8640000"
    })
    Call<ResponseBody> booksDetail(@Header("serverVersion") int serverVersion,
                                   @Header("Auth-Token") String authToken,
                                   @Query("bookId") int bookId);

    @Streaming
    @GET
    @Headers("Cache-Control:public, max-age=86400, max-stale=8640000")
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}