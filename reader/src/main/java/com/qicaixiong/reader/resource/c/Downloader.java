package com.qicaixiong.reader.resource.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.qicaixiong.reader.get.DownloadCallback;
import com.qicaixiong.reader.get.ReaderHelp;
import com.qicaixiong.reader.resource.http.QueueDownload;
import com.qicaixiong.reader.resource.http.QueueDownloadManager;
import com.qicaixiong.reader.resource.http.RequestService;
import com.qicaixiong.reader.m.inputmodel.BooksDetailBean;
import com.qicaixiong.reader.m.show.AnLocationUrlModel;
import com.qicaixiong.reader.m.show.AnPageModel;
import com.yyx.commonsdk.file.FileUtils;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.string.SHA1;
import com.yyx.commonsdk.thread.ThreadPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * 管理本地资源的下载类
 * Created by admin on 2018/8/31.
 */

public class Downloader {
    private static Downloader downloader;

    public String IP = "http://dev.api.qicaibear.com";
    public final String picIP = "http://imgs.hellokid.com/";

    private RequestService service;
    private RequestService downloadService;
    private Context context;
    private int serverVersion;
    private String AuthToken;

    public static Downloader getInstance() {
        if (downloader == null)
            downloader = new Downloader();
        return downloader;
    }

    /**
     * 销毁 须知：销毁的东西
     */
    public static void destroyInstance(){
        if (downloader != null) {
            downloader.service = null;
            downloader.downloadService = null;
        }
        downloader = null;
    }

    //单例模式  先初始化RequestService对象
    public void init(Context context,int serverVersion, String AuthToken,String IP) {
        if (!TextUtils.isEmpty(IP))
            this.IP = IP;
        if (context != null)
            this.context = context;
        this.serverVersion =serverVersion;
        this.AuthToken = AuthToken;
        if (service == null && context != null) {
            Retrofit retrofit = createRequest();
            service = retrofit.create(RequestService.class);
        }
        if (downloadService == null && context != null) {
            Retrofit retrofit = createDownloadRequest();
            downloadService = retrofit.create(RequestService.class);
        }
        boolean b1 = FileController.getRootDirectory().mkdirs();
        boolean b2 = FileController.getDownloadDirectory().mkdirs();
    }

    //获取书籍脚本
    public void getBookDetail(boolean needRedownload, int bookId, DownloadCallback callback) {
        String bookid = String.valueOf(bookId);

        if (!needRedownload && ReaderHelp.getInstance().getBookId() == bookId && ReaderHelp.getInstance().isReady()) {
            //数据准备过了 可以复用
            if (callback != null) {
                callback.end4workThread(bookId, true);
            }
            return;
        }

        //走下载路线
        ReaderHelp.getInstance().setReady(false);
        ReaderHelp.getInstance().setBookId(bookId);
        if (service == null) {
            MyLog.d("show", "下载没有初始化");
            if (callback != null) {
                callback.end4workThread(bookId, false);
            }
        } else {
            try {
                Response<ResponseBody> response = service.booksDetail(serverVersion, AuthToken, bookId).execute();
                //获取json
                String json = null;
                if (response.isSuccessful()) {
                    final String Etag = response.headers().get("Etag");
                    if (isBookDetailUpData(Etag, bookId)) {//更新json
                        try {
                            if (response.body() != null)
                                json = response.body().string();
                            //保存本地
                            FileController.writeAnBookSummary(String.valueOf(bookId), Etag);
                            FileController.writeAnBookJSON(String.valueOf(bookId), json);
                        } catch (Exception e) {
                            MyLog.e("201809251828", e.toString(), e);
                        }
                    }
                }

                if (TextUtils.isEmpty(json)) {
                    json = FileController.readAnBookJSON(bookid);
                }

                if (!TextUtils.isEmpty(json)) {
                    //解析pageModel
                    BooksDetailBean bean = JSON.parseObject(json, BooksDetailBean.class);
                    ReaderHelp.getInstance().getPageModels().clear();
                    for (BooksDetailBean.DataBean item : bean.getData()) {
                        if (item.getBookId() == ReaderHelp.getInstance().getBookId()) {
                            AnPageModel value = DataPipeline.netDataTranformPageModel(item);
                            value.setBookId(item.getBookId());
                            ReaderHelp.getInstance().getPageModels().add(value);
                        }
                    }
                    //下载
                    downloadAllURL(bookId, bean, callback);
                } else {
                    if (callback != null) {
                        callback.end4workThread(bookId, false);
                    }
                }
            } catch (Exception e) {
                MyLog.e("201810021543", e.toString(), e);
                if (callback != null) {
                    callback.end4workThread(bookId, false);
                }
            }
        }
    }

    private void downloadAllURL(int bookId, BooksDetailBean book, DownloadCallback callback){
        if (callback != null)
            callback.start(book);
        //创建AnLocationUrlModel
        QueueDownload downloadTask = new QueueDownload(bookId,AnLocationUrlModel.getManager().getAll(bookId)
                , callback);
        downloadTask.startQueue();
        QueueDownloadManager.getInstance().addTask(bookId, downloadTask);
    }

    /**
     * 下载一个资源
     */
    public void downloadOneFile(AnLocationUrlModel model,DownloadListener listener) {
        if (downloadService == null) {
            listener.error(new Exception("下载没有初始化"));
        } else {
            if (TextUtils.isEmpty(model.getUrl())) {
                MyLog.d("show", "下载url is null");
                listener.end("");
            } else {
                String url = model.getUrl();
                HashMap<String, String> mapName = getName(url, model.getSuffix());
                String filePath = mapName.get("filePath");
                String dir = mapName.get("dir");
                String etagName = mapName.get("etagName");

                MyLog.d("show", "(download) file download: " + filePath);
                try {
                    boolean result = false;

                    Response<ResponseBody> response = downloadService.downloadFileWithDynamicUrlSync(model.getUrl()).execute();

                    if (response.isSuccessful()) {

                        //网络数据
                        Headers headers = response.headers();
                        String http_etag = headers.get("Etag");
                        String http_fileSize = headers.get("Content-Length");
                        long filesize = Long.valueOf(http_fileSize);

                        //本地数据
                        String cacheEtag = FileUtils.readCacheAbsolute(dir,etagName);

                        if (!cacheEtag.equals(http_etag)) {
                            ResponseBody body = response.body();
                            if (writeResponseBodyToDisk(body, filePath)) {
                                //本地存储
                                FileUtils.writeCacheAbsolute(dir, etagName, http_etag);
                                result = true;
                            }
                        }
                    }

                    if (!result){
                        File file = new File(filePath);
                        if (file.exists()){
                            result = true;
                        }
                    }

                    if (result) {
                        model.setAbsolutePath(filePath);
                        if (listener != null) {
                            listener.end(filePath);
                        }
                    }else {
                        if (listener != null) {
                            listener.error(new Exception("下载错误"));
                        }
                    }
                } catch (Exception e) {
                    MyLog.e("201810032304",e.toString(),e);
                    if (listener != null) {
                        listener.error(new Exception("下载错误"));
                    }
                }

            }
        }
    }


    /**
     * 下载外部文件
     */
    public void downloadFile(String url, String suffix,final DownloadListener listener) {
        if (downloadService == null) {
            listener.error(new Exception("下载没有初始化"));
        } else {
            if (TextUtils.isEmpty(url)) {
                listener.error(new Exception("url is null"));
            } else {
                final File dir = new File(FileController.getDownloadDirectory().getAbsolutePath());
                final String filePath = dir.getAbsolutePath() + File.separator + SHA1.hexdigest(url) + suffix;
                MyLog.d("show", "(download) file download: " + picIP + url);
                dir.mkdirs();
                downloadService.downloadFileWithDynamicUrlSync(url).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try{
                            if (response.isSuccessful()) {
                                final ResponseBody body = response.body();
                                ThreadPool.newCachedThreadPool().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (writeResponseBodyToDisk(body, filePath)) {
                                            if (listener != null) {
                                                listener.end(filePath);
                                            }
                                        } else {
                                            if (listener != null) {
                                                listener.error(new Exception("下载错误1"));
                                            }
                                        }
                                    }
                                });
                            }
                            else {
                                MyLog.d("show", "下载失败：" + filePath);
                                if (listener != null) {
                                    listener.error(new Exception("下载错误2"));
                                }
                            }
                        }catch (Exception e){
                            if (listener != null) {
                                listener.error(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        MyLog.e("show", t.toString(), new Exception(t));
                        if (listener != null) {
                            listener.error(new Exception(t));
                        }
                    }
                });
            }
        }
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //开辟缓存
        File httpCacheDirectory = FileController.getCacheDirectory();
        Cache cache = new Cache(httpCacheDirectory, 2 << 22);
        builder.cache(cache);
        return builder.build();
    }

    private Retrofit createRequest() {
        return new Retrofit.Builder().baseUrl(IP).client(createOkHttpClient())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private Retrofit createDownloadRequest() {
        return new Retrofit.Builder().baseUrl(picIP).client(createOkHttpClient()).build();
    }
    /**
     * 判断网络是否可用
     */
    public static Boolean isNetworkonnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        NetworkInfo current = cm.getActiveNetworkInfo();
        return current != null && (current.isConnected());
    }

    private boolean writeResponseBodyToDisk(ResponseBody body,String path) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[2<<18];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                MyLog.d("show", "(start) file download: " + path + "of size => " + fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(new File(path));
                int read;
                do{
                    read = inputStream.read(fileReader);
                    if (read > 0) {
                        outputStream.write(fileReader, 0, read);
                        fileSizeDownloaded += read;
//                        MyLog.d("show", "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }
                }while (read != -1);
                outputStream.flush();
                MyLog.d("show", "(end) file download: " + path + " of size => " + fileSize);
                return true;
            } catch (Exception e) {
                MyLog.e("show", e.toString() ,e);
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            MyLog.e("show", e.toString() ,e);
            return false;
        }
    }


    //判断 与缓存文件比较 是否需要更新
    private boolean isUpData(Response<ResponseBody> response,String url,String suffix) {
        try {

        } catch (Exception e) {
            MyLog.e("201809211352", e.toString(), e);
            return true;
        }
        return true;
    }

    //命名规则
    public HashMap<String,String> getName(String url,String suffus){
        String name = SHA1.hexdigest(url)+suffus;
        String etagName = SHA1.hexdigest(name);
        //下载路径
        File dir = FileController.getDownloadDirectory();
        final File path = new File(dir, name);
        final File etagPath = new File(dir,etagName);
        HashMap<String ,String> map = new HashMap<>();
        map.put("dir",dir.getAbsolutePath());
        map.put("filePath",path.getAbsolutePath());
        map.put("etagPath",etagPath.getAbsolutePath());
        map.put("fileName",name);
        map.put("etagName",etagName);
        return map;
    }

    //书籍脚本 判断是否更新
    private boolean isBookDetailUpData(String Etag,int bookId) {
        try {
            String json = FileController.readAnBookSummary(String.valueOf(bookId));
            if (!TextUtils.isEmpty(json) && TextUtils.isEmpty(Etag) && Etag.equals(json)) {
                return false;
            }
        } catch (Exception e) {
            MyLog.e("201810021430", e.toString(), e);
            return true;
        }
        return true;
    }

    public interface DownloadListener{
        void end(String path);
        void error(Exception e);
    }
}