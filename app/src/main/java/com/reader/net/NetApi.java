package com.reader.net;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetApi {
    String IP = "http://animator.api.qicaibear.com";
    String picIP = "http://files.qicaibear.com/";

    @GET("/api/v1/listBooks")
    Observable<BooListBean> listBooks(@Query("userId") String userId);

    @GET("/api/v2/getAnimatorBookInfo")
    Observable<BookBean> getBook(@Query("userId") int userId, @Query("bookId") int bookId);
}