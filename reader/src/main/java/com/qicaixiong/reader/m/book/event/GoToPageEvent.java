package com.qicaixiong.reader.m.book.event;

import com.qicaixiong.reader.other.MessageEvent;

/**
 * 跳页阅读
 * Created by Administrator on 2018/9/13.
 */

public class GoToPageEvent extends MessageEvent {
    private int page;

    public GoToPageEvent(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
