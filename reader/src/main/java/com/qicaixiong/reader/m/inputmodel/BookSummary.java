package com.qicaixiong.reader.m.inputmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 某本书拆分后的 概述
 * Created by admin on 2018/9/25.
 */

public class BookSummary implements Parcelable{
    private ArrayList<String> pages = new ArrayList<>();
    private int bookID = 0;
    private String name = "";
    private String Etag = "";
    private int pageCount = 0;
    private long contentlenth = 0;

    public BookSummary() {
    }

    protected BookSummary(Parcel in) {
        pages = in.createStringArrayList();
        bookID = in.readInt();
        name = in.readString();
        Etag = in.readString();
        pageCount = in.readInt();
        contentlenth = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(pages);
        dest.writeInt(bookID);
        dest.writeString(name);
        dest.writeString(Etag);
        dest.writeInt(pageCount);
        dest.writeLong(contentlenth);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookSummary> CREATOR = new Creator<BookSummary>() {
        @Override
        public BookSummary createFromParcel(Parcel in) {
            return new BookSummary(in);
        }

        @Override
        public BookSummary[] newArray(int size) {
            return new BookSummary[size];
        }
    };

    public ArrayList<String> getPages() {
        return pages;
    }

    public void setPages(ArrayList<String> pages) {
        this.pages = pages;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEtag() {
        return Etag;
    }

    public void setEtag(String etag) {
        Etag = etag;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public long getContentlenth() {
        return contentlenth;
    }

    public void setContentlenth(long contentlenth) {
        this.contentlenth = contentlenth;
    }
}
