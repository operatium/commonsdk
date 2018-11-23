package com.qicaibear.bookplayer.m.client;

import java.util.ArrayList;

/**
 * 矩形区域
 * Created by admin on 2018/10/29.
 */

public class RectAreaAction {
    private String viewName;
    private float leftTopX;
    private float leftTopY;
    private float rightBottomX;
    private float rightBottomY;
    private ArrayList<String> eventList;

    public RectAreaAction(float leftTopX, float leftTopY, float rightBottomX, float rightBottomY) {
        this.leftTopX = leftTopX;
        this.leftTopY = leftTopY;
        this.rightBottomX = rightBottomX;
        this.rightBottomY = rightBottomY;
    }

    public float getLeftTopX() {
        return leftTopX;
    }

    public void setLeftTopX(float leftTopX) {
        this.leftTopX = leftTopX;
    }

    public float getLeftTopY() {
        return leftTopY;
    }

    public void setLeftTopY(float leftTopY) {
        this.leftTopY = leftTopY;
    }

    public float getRightBottomX() {
        return rightBottomX;
    }

    public void setRightBottomX(float rightBottomX) {
        this.rightBottomX = rightBottomX;
    }

    public float getRightBottomY() {
        return rightBottomY;
    }

    public void setRightBottomY(float rightBottomY) {
        this.rightBottomY = rightBottomY;
    }

    public ArrayList<String> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<String> eventList) {
        this.eventList = eventList;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
