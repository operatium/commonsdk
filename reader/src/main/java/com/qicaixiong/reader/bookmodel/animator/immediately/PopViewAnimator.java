package com.qicaixiong.reader.bookmodel.animator.immediately;

import android.graphics.PointF;

import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;
import com.yyx.commonsdk.baseclass.Size;

/**
 * 复用弹窗动作
 * Created by admin on 2018/10/30.
 */

public class PopViewAnimator extends AnimatorValue {
    private PointF fromPoint;
    private PointF toPoint;
    private Size viewSize;
    private String pic;
    private long time;

    public PopViewAnimator(String myName) {
        super(myName, "PopViewAnimator");
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Size getViewSize() {
        return viewSize;
    }

    public void setViewSize(Size viewSize) {
        this.viewSize = viewSize;
    }

    public PointF getFromPoint() {
        return fromPoint;
    }

    public void setFromPoint(PointF fromPoint) {
        this.fromPoint = fromPoint;
    }

    public PointF getToPoint() {
        return toPoint;
    }

    public void setToPoint(PointF toPoint) {
        this.toPoint = toPoint;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
