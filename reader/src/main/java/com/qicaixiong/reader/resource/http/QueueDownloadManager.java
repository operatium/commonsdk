package com.qicaixiong.reader.resource.http;

import java.util.HashMap;

/**
 * 管理 顺序下载的任务
 * 可以取消
 * Created by admin on 2018/9/21.
 */

public class QueueDownloadManager {
    private static QueueDownloadManager instance;

    private HashMap<Integer, QueueDownload> map = new HashMap<>(); // key = bookid

    public static QueueDownloadManager getInstance(){
        if (instance == null)
            instance = new QueueDownloadManager();
        return instance;
    }

    public void addTask(int bookid, QueueDownload download){
        map.put(bookid,download);
    }

    public void deleteTask(int bookid){
        map.remove(bookid);
    }

    public QueueDownload getTask(int bookid){
        return map.get(bookid);
    }

    public boolean bookIsRuning(int bookid){
        QueueDownload download = map.get(bookid);
        return download != null;
    }
}
