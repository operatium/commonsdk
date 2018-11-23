package com.qicaixiong.reader.m.page.event;

import com.qicaixiong.reader.other.MessageEvent;

/**
 * Created by admin on 2018/8/30.
 */

public class TextTurnEvent extends MessageEvent {
    private String showWord;

    public TextTurnEvent(String showWord) {
        this.showWord = showWord;
    }

    public String getShowWord() {
        return showWord;
    }

    public void setShowWord(String showWord) {
        this.showWord = showWord;
    }
}
