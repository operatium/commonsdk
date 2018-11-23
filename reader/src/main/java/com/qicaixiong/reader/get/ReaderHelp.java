package com.qicaixiong.reader.get;

import com.qicaixiong.reader.m.show.AnPageModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 提供数据
 * Created by admin on 2018/9/14.
 */

public class ReaderHelp {
    private static ReaderHelp instance;
    private HashMap<String,Integer> map = new HashMap<>();//单词 量
    private long startTime;//阅读开始时间
    private int bookId;//当前书ID
    private ArrayList<AnPageModel> pageModels = new ArrayList<>();//展示页的数据
    private boolean ready = false;//书籍是否准备好


    public static ReaderHelp getInstance(){
        if (instance == null)
            instance = new ReaderHelp();
        return instance;
    }

    public static void create(){
        if (instance != null){
            instance.map.clear();
            instance = null;
        }
        instance = new ReaderHelp();
    }

    public static void clearPageModel(){
        if (instance != null){
            instance.map.clear();
        }
    }

    public void addWord(String word){
        map.put(word,1);
    }

    public void clearWord(){
        map.clear();
    }

    public int getWords(){
        return map.size();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public ArrayList<AnPageModel> getPageModels() {
        return pageModels;
    }

    public void setPageModels(ArrayList<AnPageModel> pageModels) {
        this.pageModels = pageModels;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
