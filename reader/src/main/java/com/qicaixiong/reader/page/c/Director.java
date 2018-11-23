package com.qicaixiong.reader.page.c;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.qicaixiong.reader.bookmodel.animator.OrderAction;
import com.qicaixiong.reader.m.page.data.AdditionalObject;
import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 阅读器的导演类
 *
 * 指挥统筹全局调度
 * 1.控制自动翻页
 * 2.控制暂停 恢复
 * 3.控制触发式动画
 * Created by Administrator on 2018/10/27.
 */

public class Director {
    private int currentPage;//当前页
    private int allPageCount;//总页数


    //指挥控件做动作
    public void viewRunAction(View view, String actionGroupName, AdditionalObject object
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> actionGroupMap
            , LinkedList<ImageView> popViews) {
        if (view != null && actionMap != null && !actionMap.isEmpty()
                && actionGroupMap != null && !actionGroupMap.isEmpty()) {
            AnimatorControl.viewRunAction(view, actionGroupName, object, actionMap, actionGroupMap, popViews);
        }
    }

    //指挥控件做动作
    public void whoRunAction(String who, String actionGroupName, AdditionalObject object
            , HashMap<String, View> viewMap
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> actionGroupMap
            , LinkedList<ImageView> popViews) {
        if (!TextUtils.isEmpty(who) && !TextUtils.isEmpty(actionGroupName)
                && viewMap != null && !viewMap.isEmpty()
                && actionMap != null && !actionMap.isEmpty()
                && actionGroupMap != null && !actionGroupMap.isEmpty()) {
            AnimatorControl.whoRunActions(who, actionGroupName, object, viewMap, actionMap, actionGroupMap, popViews);
        }
    }
}