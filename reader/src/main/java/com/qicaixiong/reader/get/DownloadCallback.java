package com.qicaixiong.reader.get;

import com.qicaixiong.reader.m.inputmodel.BooksDetailBean;

/**
 * Created by admin on 2018/9/3.
 */

public interface DownloadCallback {
    void start(BooksDetailBean bookSummary);
    void progress4workThread(int bookId, int p);
    void end4workThread(int bookId, boolean isSuccess);
}
