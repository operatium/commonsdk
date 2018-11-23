package com.qicaibear.bookplayer.c;

import android.os.Environment;

import com.yyx.commonsdk.file.FileUtils;

import java.io.File;


public class FileController {
    //根目录
    public File getRootDir() {
        return new File(Environment.getExternalStorageDirectory(), "com.qicaibear");
    }

    //某本书的目录
    public File getBookDir(String bookID) {
        return new File(getRootDir(), bookID);
    }

    //整本书的json文件
    public File findBookContent(String bookID) {
        return new File(getBookDir(bookID), relativePathBookContent());
    }
    public void writeBookContent(String bookID, String json) {
        FileUtils.writeCacheAbsolute(getBookDir(bookID).getAbsolutePath(), relativePathBookContent(), json);
    }
    public String relativePathBookContent(){
        return "book";
    }

    //整本书的页码文件
    public File findPageNoList(String bookID) {
        return new File(getBookDir(bookID), relativePathPageNoList());
    }
    public void writePageNoList(String bookID, String json) {
        FileUtils.writeCacheAbsolute(getBookDir(bookID).getAbsolutePath(), relativePathPageNoList(), json);
    }
    public String relativePathPageNoList(){
        return "table";
    }

    //整本书的书籍简介文件
    public File findBookInfo(String bookID) {
        return new File(getBookDir(bookID), relativePathBookInfo());
    }
    public void writeBookInfo(String bookID, String json) {
        FileUtils.writeCacheAbsolute(getBookDir(bookID).getAbsolutePath(), relativePathBookInfo(), json);
    }
    public String relativePathBookInfo(){
        return "bookinfo";
    }

    //具体一页的json文件
    public File findPageContent(String bookID, String pageId) {
        return new File(getBookDir(bookID), relativePathPageContent(pageId));
    }
    public void writePageContent(String bookID, String pageId, String json) {
        FileUtils.writeCacheAbsolute(getBookDir(bookID).getAbsolutePath(), relativePathPageContent(pageId), json);
    }
    public String relativePathPageContent(String pageId){
        return pageId;
    }
}