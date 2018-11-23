package com.qicaibear.bookplayer.m.server;

public class PbSeamRollingAnimator   {
    private Integer id;

    private Integer bookId;

    private Integer bookDetailId;

    private String seamRollingName;

    private Integer seamRollingTime;

    private Integer delayTime;

    private Integer play;   //0 运行 1 停止（默认）

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

    public String getSeamRollingName() {
        return seamRollingName;
    }

    public void setSeamRollingName(String seamRollingName) {
        this.seamRollingName = seamRollingName;
    }

    public Integer getSeamRollingTime() {
        return seamRollingTime;
    }

    public void setSeamRollingTime(Integer seamRollingTime) {
        this.seamRollingTime = seamRollingTime;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
    }

    public Integer getPlay() {
        return play;
    }

    public void setPlay(Integer play) {
        this.play = play;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}