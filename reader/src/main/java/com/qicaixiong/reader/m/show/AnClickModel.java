package com.qicaixiong.reader.m.show;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * 矩形区域的点击
 * 点击一个帽子 弹文本  播放读音
 * Created by admin on 2018/8/28.
 */

public class AnClickModel implements Parcelable {
    private String word;
    private PointF startPoint;
    private PointF endPoint;
    private AnLocationUrlModel sound;

    public AnClickModel(String word, PointF startPoint, PointF endPoint, AnLocationUrlModel sound) {
        this.word = word;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.sound = sound;
    }


    protected AnClickModel(Parcel in) {
        word = in.readString();
        startPoint = in.readParcelable(PointF.class.getClassLoader());
        endPoint = in.readParcelable(PointF.class.getClassLoader());
        sound = in.readParcelable(AnLocationUrlModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeParcelable(startPoint, flags);
        dest.writeParcelable(endPoint, flags);
        dest.writeParcelable(sound, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnClickModel> CREATOR = new Creator<AnClickModel>() {
        @Override
        public AnClickModel createFromParcel(Parcel in) {
            return new AnClickModel(in);
        }

        @Override
        public AnClickModel[] newArray(int size) {
            return new AnClickModel[size];
        }
    };

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public PointF getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(PointF startPoint) {
        this.startPoint = startPoint;
    }

    public PointF getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(PointF endPoint) {
        this.endPoint = endPoint;
    }

    public AnLocationUrlModel getSound() {
        return sound;
    }

    public void setSound(AnLocationUrlModel sound) {
        this.sound = sound;
    }
}
