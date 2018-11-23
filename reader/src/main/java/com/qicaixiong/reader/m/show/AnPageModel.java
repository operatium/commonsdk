package com.qicaixiong.reader.m.show;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 一页的数据
 * 单页的视图数据
 * Created by admin on 2018/8/27.
 */

public class AnPageModel implements Parcelable {
    private int bookId;
    private int pageNo;//当前页码
    private int pageId;
    private ArrayList<AnTextModel> texts;//文本集
    private ArrayList<AnClickModel> clickModels;//点击区域集
    private PointF replay;//重读按钮的位置
    private Point replaySize;//重读按钮的大小
    private int voiceTime;//整段的语音长度
    private AnLocationUrlModel voice;//整段语音
    private AnLocationUrlModel picture;//图片

    public AnPageModel() {
    }

    protected AnPageModel(Parcel in) {
        bookId = in.readInt();
        pageNo = in.readInt();
        pageId = in.readInt();
        texts = in.createTypedArrayList(AnTextModel.CREATOR);
        clickModels = in.createTypedArrayList(AnClickModel.CREATOR);
        replay = in.readParcelable(PointF.class.getClassLoader());
        replaySize = in.readParcelable(Point.class.getClassLoader());
        voiceTime = in.readInt();
        voice = in.readParcelable(AnLocationUrlModel.class.getClassLoader());
        picture = in.readParcelable(AnLocationUrlModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeInt(pageNo);
        dest.writeInt(pageId);
        dest.writeTypedList(texts);
        dest.writeTypedList(clickModels);
        dest.writeParcelable(replay, flags);
        dest.writeParcelable(replaySize, flags);
        dest.writeInt(voiceTime);
        dest.writeParcelable(voice, flags);
        dest.writeParcelable(picture, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnPageModel> CREATOR = new Creator<AnPageModel>() {
        @Override
        public AnPageModel createFromParcel(Parcel in) {
            return new AnPageModel(in);
        }

        @Override
        public AnPageModel[] newArray(int size) {
            return new AnPageModel[size];
        }
    };

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public AnLocationUrlModel getPicture() {
        return picture;
    }

    public void setPicture(AnLocationUrlModel picture) {
        this.picture = picture;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public AnLocationUrlModel getVoice() {
        return voice;
    }

    public void setVoice(AnLocationUrlModel voice) {
        this.voice = voice;
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        this.voiceTime = voiceTime;
    }

    public PointF getReplay() {
        return replay;
    }

    public void setReplay(PointF replay) {
        this.replay = replay;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public ArrayList<AnTextModel> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<AnTextModel> texts) {
        this.texts = texts;
    }

    public ArrayList<AnClickModel> getClickModels() {
        return clickModels;
    }

    public void setClickModels(ArrayList<AnClickModel> clickModels) {
        this.clickModels = clickModels;
    }

    public Point getReplaySize() {
        return replaySize;
    }

    public void setReplaySize(Point replaySize) {
        this.replaySize = replaySize;
    }
}
