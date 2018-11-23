package com.qicaixiong.reader.bookmodel.other;

import android.graphics.Point;

import com.yyx.commonsdk.baseclass.Side;
import com.yyx.commonsdk.baseclass.Size;

/**
 * 节点描述
 * Created by admin on 2018/10/19.
 */

public class WidgetValue {

    private String myName;//控件名称
    private String viewType;//控件类型 Layout, ImageView, SeamlessRollingView
    private Side side;//靠边优先
    private Point position;//位置其次
    private Size viewSize;//控件的宽高 -1是充满父节点
    private String pic;//资源
    private float picAspectRatio;//SeamlessRollingView图片宽高比
    private String direction;//SeamlessRollingView方向  left right top bottom
    private String parentNodeName;//父节点名称

    public WidgetValue(String myName, String viewType) {
        this.myName = myName;
        this.viewType = viewType;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Size getViewSize() {
        return viewSize;
    }

    public void setViewSize(Size viewSize) {
        this.viewSize = viewSize;
    }

    public String getParentNodeName() {
        return parentNodeName;
    }

    public void setParentNodeName(String parentNodeName) {
        this.parentNodeName = parentNodeName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public float getPicAspectRatio() {
        return picAspectRatio;
    }

    public void setPicAspectRatio(float picAspectRatio) {
        this.picAspectRatio = picAspectRatio;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
