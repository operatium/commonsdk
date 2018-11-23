package com.reader.net;

import android.content.Context;
import android.util.Log;
import android.view.Display;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.qicaibear.bookplayer.m.server.AnimatorBooksDto;
import com.reader.App;
import com.reader.BuildConfig;
import com.yyx.commonsdk.log.MyLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class NetControl {
    private NetApi getservice, postservice;

    private static NetControl instance;

    public static NetControl getInstance() {
        if (instance == null)
            instance = new NetControl();
        return instance;
    }

    public void createRequest(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //开辟缓存
        File httpCacheDirectory = context.getApplicationContext().getCacheDir();
        Cache cache = new Cache(httpCacheDirectory, 2 << 22);
        builder.cache(cache);
//        if (BuildConfig.DEBUG)
            builder.addInterceptor(newLogInterceptor());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NetApi.IP).client(builder.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        getservice = retrofit.create(NetApi.class);

        OkHttpClient.Builder builder2 = new OkHttpClient.Builder();
//        if (BuildConfig.DEBUG)
            builder2.addInterceptor(newLogInterceptor());
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(NetApi.IP)
                .client(builder2.build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        postservice = retrofit2.create(NetApi.class);
    }

    public Interceptor newLogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                MyLog.d("netShow", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    //获取书籍列表
    public Disposable getBooksList(Consumer<List<BooListBean.DataBean>> success, final Consumer<Throwable> error) {
        if (getservice == null)
            createRequest(App.getInstance());
        return getservice.listBooks("1").map(new Function<BooListBean, List<BooListBean.DataBean>>() {
            @Override
            public List<BooListBean.DataBean> apply(BooListBean s) throws Exception {
                if (s != null && s.getStatus() == 0){
                    return s.getData();
                }else {
                    if (s != null)
                        error.accept(new Throwable(s.getMessage()));
                    else
                        error.accept(new Throwable("网络异常"));
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, error);
    }

    //获取书籍内容
    public Disposable getBook(int bookId,Consumer<AnimatorBooksDto> success, final Consumer<Throwable> error){
        if (getservice == null)
            createRequest(App.getInstance());
        return getservice.getBook(1,bookId).map(new Function<BookBean, AnimatorBooksDto>() {
            @Override
            public AnimatorBooksDto apply(BookBean s) throws Exception {
                if (s != null && s.getStatus() == 0){
                    return s.getData();
                }else {
                    if (s != null)
                        error.accept(new Throwable(s.getMessage()));
                    else
                        error.accept(new Throwable("网络异常"));
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, error);
    }
}