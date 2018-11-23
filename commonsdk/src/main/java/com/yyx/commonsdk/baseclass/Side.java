package com.yyx.commonsdk.baseclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/10/19.
 */

public class Side implements Parcelable{
    private int left;
    private int top;
    private int right;
    private int bottom;

    public Side() {
    }

    public Side(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean haveZero() {
        return left * top * right * bottom == 0;
    }

    public boolean landscapeHaveZero(){
        return left * right  == 0;
    }

    public boolean portraitHaveZero(){
        return top * bottom  == 0;
    }

    public boolean isAllZero() {
        return left == 0 && top == 0 && right == 0 && bottom == 0;
    }

    protected Side(Parcel in) {
        left = in.readInt();
        top = in.readInt();
        right = in.readInt();
        bottom = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(left);
        dest.writeInt(top);
        dest.writeInt(right);
        dest.writeInt(bottom);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Side> CREATOR = new Creator<Side>() {
        @Override
        public Side createFromParcel(Parcel in) {
            return new Side(in);
        }

        @Override
        public Side[] newArray(int size) {
            return new Side[size];
        }
    };

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
