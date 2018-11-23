package com.qicaixiong.reader.m.page.event;

import com.qicaixiong.reader.other.MessageEvent;

/**
 * 文本单击 常亮显示，时间为音频对应时间
 * Created by admin on 2018/8/28.
 */

public class TextInitEvent extends MessageEvent {
    private String text;

    public TextInitEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
