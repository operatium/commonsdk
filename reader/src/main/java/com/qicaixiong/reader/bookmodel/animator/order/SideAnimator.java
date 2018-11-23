package com.qicaixiong.reader.bookmodel.animator.order;

import android.graphics.Point;

import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;
import com.yyx.commonsdk.baseclass.Side;
import com.yyx.commonsdk.baseclass.Size;

/**
 * 靠边动画
 *  * 1.long time //动画的持续时间
 2.long startDelay //动画的启动前的延迟时间
 3.int repeatCount //重复次数 -1 = 无限循环； 0 = 执行1次
 4.int repeatMode //重复模式 RESTART(1) = 每次从头循环 REVERSE(2) = 逆向循环
 parentViewSize //父节点宽高
 viewSize //控件自身的宽高
 formSide //靠边的起点
 toSide //靠边的终点
 formPoint //起点的坐标
 * Created by admin on 2018/10/19.
 */
public class SideAnimator extends AnimatorValue {
    private long time;
    private long startDelay;
    private int repeatCount;
    private int repeatMode;
    private Size parentViewSize;
    private Size viewSize;
    private Side formSide;
    private Side toSide;
    private Point formPoint;

    public SideAnimator(String myName) {
        super(myName, "SideAnimator");
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

    public Side getFormSide() {
        return formSide;
    }

    public void setFormSide(Side formSide) {
        this.formSide = formSide;
    }

    public Side getToSide() {
        return toSide;
    }

    public void setToSide(Side toSide) {
        this.toSide = toSide;
    }

    public Point getFormPoint() {
        return formPoint;
    }

    public void setFormPoint(Point formPoint) {
        this.formPoint = formPoint;
    }

    public Size getParentViewSize() {
        return parentViewSize;
    }

    public void setParentViewSize(Size parentViewSize) {
        this.parentViewSize = parentViewSize;
    }

    public Size getViewSize() {
        return viewSize;
    }

    public void setViewSize(Size viewSize) {
        this.viewSize = viewSize;
    }
}