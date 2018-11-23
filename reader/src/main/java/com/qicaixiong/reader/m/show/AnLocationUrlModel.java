package com.qicaixiong.reader.m.show;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;

import com.qicaixiong.reader.resource.c.Downloader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 本地URL
 * 管理网络资源 和 本地资源的对应
 *
 * ModelManager 是用于管理所有的AnLocationUrlModel
 * 1.可以提供一份副本给您 但是您不能直接操作manager
 * 2.不使用的时候 请注意销毁
 * Created by admin on 2018/9/1.
 */

public class AnLocationUrlModel implements Parcelable {
    private static ModelManager modelManager;

    private String url;
    private String absolutePath;
    private String suffix;

    public AnLocationUrlModel(int bookid) {
        this(null, bookid, null);
    }

    public AnLocationUrlModel(String url, int bookId, String suffix) {
        this.url = url;
        this.suffix = suffix;
        if (modelManager == null)
            modelManager = new ModelManager();
        modelManager.addData(this, bookId);
    }

    //该函数用于集中管理下载使用
    public static AnLocationUrlModel getObjectToDownload(String url, int bookid, String suffix) {
        if (modelManager == null)
            modelManager = new ModelManager();
        HashMap<String, AnLocationUrlModel> hashMap = modelManager.getBookLocation(bookid);
        if (hashMap != null){
            AnLocationUrlModel model = hashMap.get(url);
            if (model != null) {
                if (TextUtils.isEmpty(model.getSuffix()))
                    model.setSuffix(suffix);
                return model;
            }
        }
        return new AnLocationUrlModel(url,bookid,suffix);
    }

    //确保已经下载完成 获取下载完成的对象
    public static AnLocationUrlModel getDownloadedObject(String url, int bookid, String suffix) {
        AnLocationUrlModel model;
        if (modelManager == null)
            modelManager = new ModelManager();
        HashMap<String, AnLocationUrlModel> hashMap = modelManager.getBookLocation(bookid);
        if (hashMap != null) {
            model = hashMap.get(url);
            if (model != null && !TextUtils.isEmpty(model.getAbsolutePath())) {
                if (TextUtils.isEmpty(model.getSuffix()))
                    model.setSuffix(suffix);
                return model;
            }
        }
        model = new AnLocationUrlModel(url, bookid, suffix);
        HashMap<String, String> map = Downloader.getInstance().getName(url, suffix);
        String filePath = map.get("filePath");
        model.setAbsolutePath(filePath);
        return model;
    }

    protected AnLocationUrlModel(Parcel in) {
        url = in.readString();
        absolutePath = in.readString();
        suffix = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(absolutePath);
        dest.writeString(suffix);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnLocationUrlModel> CREATOR = new Creator<AnLocationUrlModel>() {
        @Override
        public AnLocationUrlModel createFromParcel(Parcel in) {
            return new AnLocationUrlModel(in);
        }

        @Override
        public AnLocationUrlModel[] newArray(int size) {
            return new AnLocationUrlModel[size];
        }
    };

    public static ModelManager getManager(){
        if (modelManager == null)
            modelManager = new ModelManager();
        return modelManager;
    }

    /**
     * 销毁 须知：new AnLocationUrlModel 的时候自动加入管理类中
     * ，如果销毁如果还有AnLocationUrlModel正在工作 就会发生管理不全的问题
     */
    public static void destroyManager(){
        if (modelManager != null && modelManager.hashMapBook != null)
            modelManager.hashMapBook.clear();
        modelManager = null;
    }

    public static void clearAnBook(int bookid){
        if (modelManager != null && modelManager.hashMapBook != null){
            if (modelManager.hashMapBook.get(bookid) != null)
                modelManager.hashMapBook.get(bookid).clear();
            modelManager.hashMapBook.remove(bookid);
        }
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }


    public static class ModelManager{
        private SparseArray<HashMap<String,AnLocationUrlModel>> hashMapBook = new SparseArray<>();

        private void addData(AnLocationUrlModel model, int bookId) {
            if (hashMapBook == null)
                hashMapBook = new SparseArray<>();
            HashMap<String, AnLocationUrlModel> anLocationUrlModels = hashMapBook.get(bookId);
            if (anLocationUrlModels == null) {
                anLocationUrlModels = new HashMap<>();
                hashMapBook.put(bookId, anLocationUrlModels);
            }
            //有URL的才需要统一管理
            String url = model.getUrl();
            if (!TextUtils.isEmpty(url))
                anLocationUrlModels.put(url, model);
        }

        private HashMap<String,AnLocationUrlModel> getBookLocation(int bookId){
            return hashMapBook.get(bookId);
        }

        public ArrayList<AnLocationUrlModel> getAll(int bookId){
            HashMap<String,AnLocationUrlModel> models = hashMapBook.get(bookId);
            if (models != null){
                return new ArrayList<>(models.values());
            }else
                return null;
        }
    }
}
