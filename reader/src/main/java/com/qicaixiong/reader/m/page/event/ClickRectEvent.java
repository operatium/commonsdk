package com.qicaixiong.reader.m.page.event;

import android.graphics.PointF;

import com.qicaixiong.reader.other.MessageEvent;

/**
 * 点击区域内 被点击  弹文本 播放读音
 * Created by admin on 2018/8/28.
 */

public class ClickRectEvent extends MessageEvent {
    private String word;
    private PointF showPoint;
    private String soundPath;
    private int pageNo;

    public ClickRectEvent(String word, PointF showPoint, String soundPath, int pageNo) {
        this.word = word;
        this.showPoint = showPoint;
        this.soundPath = soundPath;
        this.pageNo = pageNo;
    }

    public PointF getShowPoint() {
        return showPoint;
    }

    public void setShowPoint(PointF showPoint) {
        this.showPoint = showPoint;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
