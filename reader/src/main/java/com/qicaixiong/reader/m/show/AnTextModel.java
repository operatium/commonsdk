package com.qicaixiong.reader.m.show;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 单文本的视图数据
 * 单词的展示位置和大小
 * Created by admin on 2018/8/27.
 */

public class AnTextModel implements Parcelable {
    /**
     * word :
     * audioUrl :
     * positionX : 1
     * positionY : 1
     * fontColor :
     * fontSize : 11
     * fontBold : 0
     */

    private String word;
    private AnLocationUrlModel audio;
    private int positionX;
    private int positionY;
    private String fontColor;
    private int fontSize;
    private int fontBold;
    private int playTime;
    private int startTime;
    private int endTime;

    public AnTextModel() {
    }

    protected AnTextModel(Parcel in) {
        word = in.readString();
        audio = in.readParcelable(AnLocationUrlModel.class.getClassLoader());
        positionX = in.readInt();
        positionY = in.readInt();
        fontColor = in.readString();
        fontSize = in.readInt();
        fontBold = in.readInt();
        playTime = in.readInt();
        startTime = in.readInt();
        endTime = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeParcelable(audio, flags);
        dest.writeInt(positionX);
        dest.writeInt(positionY);
        dest.writeString(fontColor);
        dest.writeInt(fontSize);
        dest.writeInt(fontBold);
        dest.writeInt(playTime);
        dest.writeInt(startTime);
        dest.writeInt(endTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnTextModel> CREATOR = new Creator<AnTextModel>() {
        @Override
        public AnTextModel createFromParcel(Parcel in) {
            return new AnTextModel(in);
        }

        @Override
        public AnTextModel[] newArray(int size) {
            return new AnTextModel[size];
        }
    };

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontBold() {
        return fontBold;
    }

    public void setFontBold(int fontBold) {
        this.fontBold = fontBold;
    }

    public AnLocationUrlModel getAudio() {
        return audio;
    }

    public void setAudio(AnLocationUrlModel audio) {
        this.audio = audio;
    }
}
