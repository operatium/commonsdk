package com.qicaixiong.reader.m.show;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 书籍的数据
 * Created by admin on 2018/8/27.
 */

public class AnBookModel implements Parcelable{
    private ArrayList<AnPageModel> pages;
    private int bookId;

    public AnBookModel() {
    }

    protected AnBookModel(Parcel in) {
        pages = in.createTypedArrayList(AnPageModel.CREATOR);
        bookId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(pages);
        dest.writeInt(bookId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnBookModel> CREATOR = new Creator<AnBookModel>() {
        @Override
        public AnBookModel createFromParcel(Parcel in) {
            return new AnBookModel(in);
        }

        @Override
        public AnBookModel[] newArray(int size) {
            return new AnBookModel[size];
        }
    };

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public ArrayList<AnPageModel> getPages() {
        return pages;
    }

    public void setPages(ArrayList<AnPageModel> pages) {
        this.pages = pages;
    }
}
