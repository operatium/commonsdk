package com.qicaibear.bookplayer.m.server;


public class PbNodeRectRDto  {
    private String nodeName;

    private Integer bookId;

    private Integer bookDetailId;

    private String rectName;

    private String leftTop;

    private String rightBottom;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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

    public String getRectName() {
        return rectName;
    }

    public void setRectName(String rectName) {
        this.rectName = rectName;
    }

    public String getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(String leftTop) {
        this.leftTop = leftTop;
    }

    public String getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(String rightBottom) {
        this.rightBottom = rightBottom;
    }
}