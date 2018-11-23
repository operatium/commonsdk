package com.qicaibear.bookplayer.m.server;

public class PbFrameAnimator{
    private Integer id;

    private Integer bookId;

    private Integer bookDetailId;

    private String frameName;

    private String framePics;

    private String frameTimes;

    private Integer isOneShot;//是否播放一次 1 播放一次 0 循环

    private Integer frameWidth;

    private Integer frameHeight;

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

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public String getFramePics() {
        return framePics;
    }

    public void setFramePics(String framePics) {
        this.framePics = framePics;
    }

    public String getFrameTimes() {
        return frameTimes;
    }

    public void setFrameTimes(String frameTimes) {
        this.frameTimes = frameTimes;
    }

    public Integer getIsOneShot() {
        return isOneShot;
    }

    public void setIsOneShot(Integer isOneShot) {
        this.isOneShot = isOneShot;
    }

    public Integer getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(Integer frameWidth) {
        this.frameWidth = frameWidth;
    }

    public Integer getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(Integer frameHeight) {
        this.frameHeight = frameHeight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}