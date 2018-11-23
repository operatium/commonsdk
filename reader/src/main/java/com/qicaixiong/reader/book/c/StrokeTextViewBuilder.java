package com.qicaixiong.reader.book.c;

import android.content.Context;

import com.qicaixiong.reader.other.customview.StrokeTextView;
import com.yyx.commonsdk.log.MyLog;

import java.util.LinkedList;

/**
 * 采用享元模式 复用StrokeTextView
 * 同一个布局器复用view
 * 不同布局器 需要删除干净
 * Created by admin on 2018/9/6.
 */

public class StrokeTextViewBuilder {
    private static StrokeTextViewBuilder instance;
    private LinkedList<StrokeTextView> views = new LinkedList<>();
    private int max = 6;

    public static StrokeTextViewBuilder getInstance(){
        if (instance == null){
            instance = new StrokeTextViewBuilder();
        }
        return instance;
    }

    public static void destroyInstance(){
        if (instance != null){
            instance.views.clear();
            instance = null;
        }
    }

    public StrokeTextView getView(Context context){
        StrokeTextView popTextView;
        if (views.size() < max ){
            popTextView = new StrokeTextView(context);
            views.addLast(popTextView);
            MyLog.d("show","new StrokeTextView ");
        }else {
            popTextView = views.pop();
            popTextView.clearAnimation();
            views.addLast(popTextView);
            MyLog.d("show","pop StrokeTextView "+popTextView.getText());
        }
        return popTextView;
    }

    public void clear(){
        views.clear();
    }

}
