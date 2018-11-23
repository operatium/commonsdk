package com.qicaibear.bookplayer.m.server;

public class PbSoundAnimator   {
    private Integer id;

    private Integer bookId;

    private Integer bookDetailId;

    private String soundName;

    private String resourceName;

    private Integer soundTrack;

    private Integer soundTime;

    private float soundVol;

    private String content;

    private Integer repeatCount;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBookDetailId() {
        return bookDetailId;
    }

    public void setBookDetailId(Integer bookDetailId) {
        this.bookDetailId = bookDetailId;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getSoundTime() {
        return soundTime;
    }

    public void setSoundTime(Integer soundTime) {
        this.soundTime = soundTime;
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

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSoundTrack() {
        return soundTrack;
    }

    public void setSoundTrack(Integer soundTrack) {
        this.soundTrack = soundTrack;
    }
}