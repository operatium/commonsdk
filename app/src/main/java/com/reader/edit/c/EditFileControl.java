package com.reader.edit.c;

import com.qicaibear.bookplayer.c.FileController;
import com.yyx.commonsdk.file.FileUtils;

import java.io.File;

/**
 * 编辑json的全部读写文件控制
 */

public class EditFileControl {
    private FileController fileController = new FileController();

    public EditFileControl() {
    }

    public EditFileControl(FileController fileController) {
        this.fileController = fileController;
    }

    //根目录
    public String relativePathRoot() {
        return "edit";
    }

    public File getRoot() {
        return new File(fileController.getRootDir(), relativePathRoot());
    }

    //书籍路径
    public String relativePathBook(String bookId) {
        return bookId;
    }

    public File getBookDir(String bookID) {
        return new File(getRoot(), relativePathBook(bookID));
    }

    //整本书的json文件
    public File findBookContent(String bookID) {
        return new File(getBookDir(bookID), relativePathBookContent());
    }

    public void writeBookContent(String bookID, String json) {
        FileUtils.writeCacheAbsolute(getBookDir(bookID).getAbsolutePath(), relativePathBookContent(), json);
    }

    public String relativePathBookContent() {
        return "book";
    }

    //整本书的页码文件
    public File findPageNoList(String bookID) {
        return new File(getBookDir(bookID), relativePathPageNoList());
    }

    public void writePageNoList(String bookID, String json) {
        FileUtils.writeCacheAbsolute(getBookDir(bookID).getAbsolutePath(), relativePathPageNoList(), json);
    }

    public String relativePathPageNoList() {
        return "table";
    }

    //整本书的书籍简介文件
    public File findBookInfo(String bookID) {
        return new File(getBookDir(bookID), relativePathBookInfo());
    }

    public void writeBookInfo(String bookID, String json) {
        FileUtils.writeCacheAbsolute(getBookDir(bookID).getAbsolutePath(), relativePathBookInfo(), json);
    }

    public String relativePathBookInfo() {
        return "bookinfo";
    }

    //具体一页的json文件
    public File findPageContent(String bookID, String pageId) {
        return new File(getBookDir(bookID), relativePathPageContent(pageId));
    }

    public void writePageContent(String bookID, String pageId, String json) {
        FileUtils.writeCacheAbsolute(getBookDir(bookID).getAbsolutePath(), relativePathPageContent(pageId), json);
    }

    public String relativePathPageContent(String pageId) {
        return pageId;
    }
}