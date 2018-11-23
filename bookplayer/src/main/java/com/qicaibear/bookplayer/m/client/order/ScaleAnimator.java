package com.qicaibear.bookplayer.m.client.order;

import android.graphics.PointF;

import com.qicaibear.bookplayer.m.client.AnimatorValue;


/**
 * Created by admin on 2018/10/25.
 */

public class ScaleAnimator extends AnimatorValue {
    private long time;
    private long startDelay;
    private int repeatCount;
    private int repeatMode;
    private PointF pivot;//锚点的坐标 0-1取值 代表控件内部位置
    private Float[] scaleX;
    private Float[] scaleY;

    public ScaleAnimator(String myName) {
        super(myName,"ScaleAnimator");
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }

    public PointF getPivot() {
        return pivot;
    }

    public void setPivot(PointF pivot) {
        this.pivot = pivot;
    }

    public Float[] getScaleX() {
        return scaleX;
    }

    public void setScaleX(Float[] scaleX) {
        this.scaleX = scaleX;
    }

    public Float[] getScaleY() {
        return scaleY;
    }

    public void setScaleY(Float[] scaleY) {
        this.scaleY = scaleY;
    }
}
