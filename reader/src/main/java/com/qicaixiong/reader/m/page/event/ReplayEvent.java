package com.qicaixiong.reader.m.page.event;

import com.qicaixiong.reader.other.MessageEvent;

/**
 * 通知 重播按钮消失和显示
 * Created by admin on 2018/8/29.
 */

public class ReplayEvent extends MessageEvent {
    private String soundTag;
    private boolean isPlaying;
    private int time;

    public ReplayEvent(String soundTag, boolean isPlaying, int time) {
        this.soundTag = soundTag;
        this.isPlaying = isPlaying;
        this.time = time;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getSoundTag() {
        return soundTag;
    }

    public void setSoundTag(String soundTag) {
        this.soundTag = soundTag;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
