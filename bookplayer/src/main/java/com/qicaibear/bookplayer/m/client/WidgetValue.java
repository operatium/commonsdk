package com.qicaibear.bookplayer.m.client;

import android.os.Parcel;
import android.os.Parcelable;

import com.yyx.commonsdk.baseclass.PointF3D;
import com.yyx.commonsdk.baseclass.Side;
import com.yyx.commonsdk.baseclass.Size;

import java.util.ArrayList;

/**
 * 节点描述
 * Created by admin on 2018/10/19.
 */

public class WidgetValue implements Parcelable {

    private String myName;//控件名称
    private String viewType;//控件类型 Layout, ImageView, SeamlessRollingView
    private Side side;//靠边优先
    private PointF3D position;//位置其次
    private Size viewSize = new Size();//控件的宽高 -1是充满父节点
    private String pic;//资源
    private float picAspectRatio;//SeamlessRollingView图片宽高比
    private String direction;//SeamlessRollingView方向  left right top bottom
    private ArrayList<WidgetValue> children;

    public WidgetValue(String myName, String viewType) {
        this.myName = myName;
        this.viewType = viewType;
    }

    protected WidgetValue(Parcel in) {
        myName = in.readString();
        viewType = in.readString();
        side = in.readParcelable(Side.class.getClassLoader());
        position = in.readParcelable(PointF3D.class.getClassLoader());
        viewSize = in.readParcelable(Size.class.getClassLoader());
        pic = in.readString();
        picAspectRatio = in.readFloat();
        direction = in.readString();
        children = in.createTypedArrayList(WidgetValue.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(myName);
        dest.writeString(viewType);
        dest.writeParcelable(side, flags);
        dest.writeParcelable(position, flags);
        dest.writeParcelable(viewSize, flags);
        dest.writeString(pic);
        dest.writeFloat(picAspectRatio);
        dest.writeString(direction);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WidgetValue> CREATOR = new Creator<WidgetValue>() {
        @Override
        public WidgetValue createFromParcel(Parcel in) {
            return new WidgetValue(in);
        }

        @Override
        public WidgetValue[] newArray(int size) {
            return new WidgetValue[size];
        }
    };

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

    public PointF3D getPosition() {
        return position;
    }

    public void setPosition(PointF3D position) {
        this.position = position;
    }

    public Size getViewSize() {
        return viewSize;
    }

    public void setViewSize(Size viewSize) {
        this.viewSize = viewSize;
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

    public ArrayList<WidgetValue> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<WidgetValue> children) {
        this.children = children;
    }
}
