package com.qicaixiong.reader.bookmodel.animator.order;

import android.graphics.PointF;

import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;

/**
 * 旋转动画
 *  private String propertyName; //轴的名称  x,y,z 大小写都行
 private long time;//动画时间
 private long startDelay;// 开始的延迟时间
 private int repeatCount;//重复次数
 private int repeatMode;//重复模式
 private PointF pivot;//锚点
 private float[] rotationValues;//角度 0 - 360
 * Created by admin on 2018/10/24.
 */

public class RotateAnimator extends AnimatorValue {
    private String propertyName;
    private long time;
    private long startDelay;
    private int repeatCount;
    private int repeatMode;
    private PointF pivot;//锚点的坐标
    private float[] rotationValues;

    public RotateAnimator(String myName) {
        super(myName,"RotateAnimator");
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public float[] getRotationValues() {
        return rotationValues;
    }

    public void setRotationValues(float[] rotationValues) {
        this.rotationValues = rotationValues;
    }

}
