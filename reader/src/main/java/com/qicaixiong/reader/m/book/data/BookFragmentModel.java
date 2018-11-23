package com.qicaixiong.reader.m.book.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/8/29.
 */

public class BookFragmentModel implements Parcelable{
    private int currentPage;//当前页
    private int allPageCount = 0;//总页数

    public BookFragmentModel() {
    }


    protected BookFragmentModel(Parcel in) {
        currentPage = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentPage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookFragmentModel> CREATOR = new Creator<BookFragmentModel>() {
        @Override
        public BookFragmentModel createFromParcel(Parcel in) {
            return new BookFragmentModel(in);
        }

        @Override
        public BookFragmentModel[] newArray(int size) {
            return new BookFragmentModel[size];
        }
    };

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllPageCount() {
        return allPageCount;
    }

    public void setAllPageCount(int allPageCount) {
        this.allPageCount = allPageCount;
    }

}
