package com.qicaixiong.reader.resource.c;

import android.os.Environment;

import com.yyx.commonsdk.file.FileUtils;

import java.io.File;

/**
 * 负责管理本地书籍资源的目录
 * Created by admin on 2018/8/31.
 */

public class FileController {

    //用户信息
    public static File getUserDirectory(){
        return getAllBooksDirectory();
    }

    //文件存储的根目录
    public static File getRootDirectory() {
        return new File(Environment.getExternalStorageDirectory(), "qicaixiong");
    }

    //文件下载目录
    public static File getDownloadDirectory() {
        return new File(getRootDirectory(), "download");
    }

    //网络请求的缓存文件目录
    public static File getCacheDirectory() {
        return new File(getRootDirectory(), "cache");
    }

    //书籍的总目录
    public static File getAllBooksDirectory() {
        return new File(getRootDirectory(), "book");
    }

    //某本书的目录
    public static File getAnBookDirectory(String bookID) {
        return new File(getAllBooksDirectory(), bookID);
    }

    //某本书的网络脚本路径
    public static File getAnBookJSON(String bookID) {
        return new File(getAnBookDirectory(bookID), getAnBookJSON_fileName());
    }
    public static String getAnBookJSON_fileName(){
        return "json";
    }
    public static void writeAnBookJSON(String bookID, String json){
        FileUtils.writeCacheAbsolute(getAnBookDirectory(bookID).getAbsolutePath(),getAnBookJSON_fileName(),json);
    }
    public static String readAnBookJSON(String bookID){
        return FileUtils.readCacheAbsolute(getAnBookDirectory(bookID).getAbsolutePath(),getAnBookJSON_fileName());
    }

    //某本书的Etag存取
    public static File getAnBookSummary(String bookID) {
        return new File(getAnBookDirectory(bookID), getAnBookSummary_fileName());
    }
    public static String getAnBookSummary_fileName(){
        return "scrpt";
    }
    public static void writeAnBookSummary(String bookID, String json){
        FileUtils.writeCacheAbsolute(getAnBookDirectory(bookID).getAbsolutePath(),FileController.getAnBookSummary_fileName(),json);
    }
    public static String readAnBookSummary(String bookID){
        return FileUtils.readCacheAbsolute(getAnBookDirectory(bookID).getAbsolutePath(),FileController.getAnBookSummary_fileName());
    }

    //某本书的网络脚本拆分出来的 单页路径
    public static File getAnPageJSON(String bookID, String pageID) {
        return new File(getAnBookDirectory(bookID), pageID);
    }
    public static void writeAnPageJSON(String bookID, String pageID, String json){
        FileUtils.writeCacheAbsolute(getAnBookDirectory(bookID).getAbsolutePath(),pageID,json);
    }
    public static String readAnPageJSON(String bookID, String pageID){
        return FileUtils.readCacheAbsolute(getAnBookDirectory(bookID).getAbsolutePath(),pageID);
    }
}
