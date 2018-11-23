package com.qicaibear.bookplayer.m.server;

public class PbSideAnimator   {
    private Integer id;

    private Integer bookId;

    private Integer bookDetailId;

    private String sideName;

    private Integer sideTime;   //运行时间

    private Integer delayTime;

    private Integer repeatCount;

    private Integer repeatMode;

    private float parentNodeWidth;

    private float parentNodeHeight;

    private float sideWidth;

    private float sideHeight;

    private String fromSide;

    private String toSide;

    private String point;   //起点坐标

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

    public String getSideName() {
        return sideName;
    }

    public void setSideName(String sideName) {
        this.sideName = sideName;
    }

    public Integer getSideTime() {
        return sideTime;
    }

    public void setSideTime(Integer sideTime) {
        this.sideTime = sideTime;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Integer getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(Integer repeatMode) {
        this.repeatMode = repeatMode;
    }

    public float getParentNodeWidth() {
        return parentNodeWidth;
    }

    public void setParentNodeWidth(float parentNodeWidth) {
        this.parentNodeWidth = parentNodeWidth;
    }

    public float getParentNodeHeight() {
        return parentNodeHeight;
    }

    public void setParentNodeHeight(float parentNodeHeight) {
        this.parentNodeHeight = parentNodeHeight;
    }

    public float getSideWidth() {
        return sideWidth;
    }

    public void setSideWidth(float sideWidth) {
        this.sideWidth = sideWidth;
    }

    public float getSideHeight() {
        return sideHeight;
    }

    public void setSideHeight(float sideHeight) {
        this.sideHeight = sideHeight;
    }

    public String getFromSide() {
        return fromSide;
    }

    public void setFromSide(String fromSide) {
        this.fromSide = fromSide;
    }

    public String getToSide() {
        return toSide;
    }

    public void setToSide(String toSide) {
        this.toSide = toSide;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}