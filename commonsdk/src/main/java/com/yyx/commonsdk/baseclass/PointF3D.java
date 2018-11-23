package com.yyx.commonsdk.baseclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/10/19.
 */

public class PointF3D implements Parcelable{
    private float x;
    private float y;
    private float z;

    public PointF3D() {
    }

    public PointF3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected PointF3D(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(x);
        dest.writeFloat(y);
        dest.writeFloat(z);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PointF3D> CREATOR = new Creator<PointF3D>() {
        @Override
        public PointF3D createFromParcel(Parcel in) {
            return new PointF3D(in);
        }

        @Override
        public PointF3D[] newArray(int size) {
            return new PointF3D[size];
        }
    };

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
