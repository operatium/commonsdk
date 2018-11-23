package com.qicaixiong.reader.bookmodel.other;

/**
 * 书籍简介
 * Created by admin on 2018/10/19.
 */

public class BookInfo {
    private int bookId;
    private String bookName;
    private int coverUrl;//封面
    private int pageCount;//总页数

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(int coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
