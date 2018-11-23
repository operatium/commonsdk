package com.qicaixiong.reader.m.page.data;

import android.graphics.Point;
import android.graphics.PointF;

import com.yyx.commonsdk.baseclass.Side;
import com.yyx.commonsdk.baseclass.Size;

/**
 * 附加信息类
 * Created by Administrator on 2018/10/30.
 */

public class AdditionalObject {
    private String name;
    private Point point;
    private PointF pointF;
    private Size size;
    private Side side;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public PointF getPointF() {
        return pointF;
    }

    public void setPointF(PointF pointF) {
        this.pointF = pointF;
    }
}
