package com.qicaixiong.reader.resource.http;

import com.qicaixiong.reader.get.DownloadCallback;
import com.qicaixiong.reader.get.ReaderHelp;
import com.qicaixiong.reader.resource.c.Downloader;
import com.qicaixiong.reader.m.show.AnLocationUrlModel;

import java.util.ArrayList;

/**
 * 一个串行的下载队列
 * Created by admin on 2018/9/4.
 */

public class QueueDownload {
    private boolean runing = true;
    private ArrayList<AnLocationUrlModel> myData;
    private DownloadCallback callback;
    private int bookId;
    private int idx;//当前资源位置

    public QueueDownload(int bookid, ArrayList<AnLocationUrlModel> myData, DownloadCallback callback) {
        this.myData = myData;
        this.callback = callback;
        this.bookId = bookid;
        idx = 0;
    }

    /**
     * 串行下载资源
     */
    public void startQueue() {
        if (myData == null || myData.isEmpty()) {
            if (callback !=null)
                callback.end4workThread(bookId,true);
            return;
        }
        Downloader.getInstance().downloadOneFile(myData.get(idx), getListener());
    }

    private Downloader.DownloadListener getListener(){
        return new Downloader.DownloadListener() {

            @Override
            public void end(String path) {
                if (callback != null)
                    callback.progress4workThread(bookId,getProgress());
                if (runing && ++idx < myData.size()) {
                    Downloader.getInstance().downloadOneFile(myData.get(idx), getListener());
                }
                else{
                    QueueDownloadManager.getInstance().deleteTask(bookId);
                    if (callback != null){
                        callback.end4workThread(bookId,true);
                    }
                    ReaderHelp.getInstance().setReady(true);
                }
            }

            @Override
            public void error(Exception e) {
                QueueDownloadManager.getInstance().deleteTask(bookId);
                if (callback != null)
                    callback.end4workThread(bookId,false);
            }
        };
    }

    public int getProgress() {
        int total = myData.size();
        if (total == 0)
            return 100;
        int downloaded = idx + 1;
        return downloaded * 100 / total;
    }

    public void stop(){
        runing = false;
    }
}
