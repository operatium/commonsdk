package com.qicaibear.bookplayer.m.client.immediately;


import com.qicaibear.bookplayer.m.client.AnimatorValue;

/**
 * 播音动画
 * Created by admin on 2018/10/26.
 */

public class SoundAnimator extends AnimatorValue {
    private String soundPath;
    private long time;
    private float soundVol = 1;//音量
    private String content;//内容
    private int repeatCount;//重复次数
    private int audioTrack;//音轨

    public SoundAnimator(String myName) {
        super(myName,"SoundAnimator");
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getSoundVol() {
        return soundVol;
    }

    public void setSoundVol(float soundVol) {
        this.soundVol = soundVol;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getAudioTrack() {
        return audioTrack;
    }

    public void setAudioTrack(int audioTrack) {
        this.audioTrack = audioTrack;
    }
}