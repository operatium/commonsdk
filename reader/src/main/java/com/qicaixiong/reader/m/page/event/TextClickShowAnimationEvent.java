package com.qicaixiong.reader.m.page.event;

import com.qicaixiong.reader.other.MessageEvent;

/**
 * 文本单击 常亮显示，时间为音频对应时间
 * Created by admin on 2018/8/28.
 */

public class TextClickShowAnimationEvent extends MessageEvent {
    private String text;
    private int time;

    public TextClickShowAnimationEvent(String text, int time) {
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
