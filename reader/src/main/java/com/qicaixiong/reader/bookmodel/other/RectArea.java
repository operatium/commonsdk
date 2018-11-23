package com.qicaixiong.reader.bookmodel.other;

public class RectArea {

    private String myName;
    private int leftTopX;
    private int leftTopY;
    private int rightBottomX;
    private int rightBottomY;
    private String nodeName;

    public int getLeftTopX() {
        return leftTopX;
    }

    public void setLeftTopX(int leftTopX) {
        this.leftTopX = leftTopX;
    }

    public int getLeftTopY() {
        return leftTopY;
    }

    public void setLeftTopY(int leftTopY) {
        this.leftTopY = leftTopY;
    }

    public int getRightBottomX() {
        return rightBottomX;
    }

    public void setRightBottomX(int rightBottomX) {
        this.rightBottomX = rightBottomX;
    }

    public int getRightBottomY() {
        return rightBottomY;
    }

    public void setRightBottomY(int rightBottomY) {
        this.rightBottomY = rightBottomY;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
