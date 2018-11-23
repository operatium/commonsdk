package com.yyx.commonsdk.sound;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/9/6.
 */

public class AnSound implements Parcelable{
    private int soundID;
    private String path;
    private String word;

    public AnSound(int soundID, String path, String word) {
        this.soundID = soundID;
        this.path = path;
        this.word = word;
    }

    protected AnSound(Parcel in) {
        soundID = in.readInt();
        path = in.readString();
        word = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(soundID);
        dest.writeString(path);
        dest.writeString(word);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnSound> CREATOR = new Creator<AnSound>() {
        @Override
        public AnSound createFromParcel(Parcel in) {
            return new AnSound(in);
        }

        @Override
        public AnSound[] newArray(int size) {
            return new AnSound[size];
        }
    };

    public int getSoundID() {
        return soundID;
    }

    public String getPath() {
        return path;
    }

    public String getWord() {
        return word;
    }
}
