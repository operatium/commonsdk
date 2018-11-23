package com.qicaixiong.reader.bookmodel.animator.order;

import android.graphics.PointF;

import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;

/**
 * Created by admin on 2018/10/25.
 */

public class ScaleAnimator extends AnimatorValue {
    private long time;
    private long startDelay;
    private int repeatCount;
    private int repeatMode;
    private PointF pivot;//锚点的坐标 0-1取值 代表控件内部位置
    private float[] scaleX;
    private float[] scaleY;

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

    public float[] getScaleX() {
        return scaleX;
    }

    public void setScaleX(float[] scaleX) {
        this.scaleX = scaleX;
    }

    public float[] getScaleY() {
        return scaleY;
    }

    public void setScaleY(float[] scaleY) {
        this.scaleY = scaleY;
    }
}
