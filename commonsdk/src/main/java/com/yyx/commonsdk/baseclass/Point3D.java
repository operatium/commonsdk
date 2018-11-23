package com.yyx.commonsdk.baseclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/10/19.
 */

public class Point3D implements Parcelable{
    private int x;
    private int y;
    private int z;

    public Point3D() {
    }

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected Point3D(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(z);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Point3D> CREATOR = new Creator<Point3D>() {
        @Override
        public Point3D createFromParcel(Parcel in) {
            return new Point3D(in);
        }

        @Override
        public Point3D[] newArray(int size) {
            return new Point3D[size];
        }
    };

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
