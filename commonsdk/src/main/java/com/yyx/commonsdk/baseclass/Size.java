package com.yyx.commonsdk.baseclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/10/18.
 */

public class Size implements Parcelable{
    private int width;
    private int height;

    public Size() {
    }

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected Size(Parcel in) {
        width = in.readInt();
        height = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Size> CREATOR = new Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel in) {
            return new Size(in);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
