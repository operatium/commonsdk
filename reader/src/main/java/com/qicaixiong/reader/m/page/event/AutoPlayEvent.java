package com.qicaixiong.reader.m.page.event;

import com.qicaixiong.reader.other.MessageEvent;

/**
 * 通知当前页自动播放
 * Created by admin on 2018/9/12.
 */

public class AutoPlayEvent extends MessageEvent {
    private int pageNo;

    public AutoPlayEvent(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
