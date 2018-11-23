package com.reader.read.m;

public class BookViewHolderData {
    private int bookId;
    private String bookUrl;
    private int type;
    private String bookName;

    public BookViewHolderData(int bookId, String bookUrl, int type, String bookName) {
        this.bookId = bookId;
        this.bookUrl = bookUrl;
        this.type = type;
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
