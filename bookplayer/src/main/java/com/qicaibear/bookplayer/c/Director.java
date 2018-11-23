package com.qicaibear.bookplayer.c;

import android.animation.Animator;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.qicaibear.bookplayer.R;
import com.qicaibear.bookplayer.m.AdditionalObject;
import com.qicaibear.bookplayer.m.EventContext;
import com.qicaibear.bookplayer.m.Message;
import com.qicaibear.bookplayer.m.client.AnimatorValue;
import com.qicaibear.bookplayer.m.client.Event;
import com.qicaibear.bookplayer.m.client.OrderAction;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.thread.ThreadPool;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
    private EventContext eventContext;//上下文 环境变量

    public Director() {
    }

    public Director(EventContext context) {
        this.eventContext = context;
    }

    //执行事件
    public void runEvent(String eventName, AdditionalObject object
            , HashMap<String, View> viewMap
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> actionGroupMap
            , HashMap<String, Event> eventMap
            , LinkedList<ImageView> popViews) {
        final Event event = eventMap.get(eventName);
        if (event != null) {
            String who = event.getNodeName();
            String grounpAction = event.getActionGroupName();
            final int startDelay = event.getStartDelay();
            if (startDelay <= 0)
                whoRunAction(who, grounpAction, object, viewMap, actionMap, actionGroupMap, popViews);
            else {
                Long time = System.currentTimeMillis();
                final Message message = new Message(who, grounpAction, time);
                View view = viewMap.get(who);
                if (view != null)
                    view.setTag(R.id.viewEvent, time);
                ThreadPool.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(startDelay);
                            EventBus.getDefault().post(message);
                        } catch (Exception e) {
                            MyLog.e("201811201339", e.toString(), e);
                        }
                    }
                });
            }
        }
    }

    //指挥控件做动作
    public void whoRunAction(String who, String actionGroupName
            , AdditionalObject object
            , HashMap<String, View> viewMap
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> actionGroupMap
            , LinkedList<ImageView> popViews) {
        if (!TextUtils.isEmpty(who) && !TextUtils.isEmpty(actionGroupName)
                && viewMap != null && !viewMap.isEmpty()
                && actionMap != null && !actionMap.isEmpty()
                && actionGroupMap != null && !actionGroupMap.isEmpty()) {
            MyLog.d("show", who + " => " + actionGroupName);
            AnimatorControl.whoRunActions(this, who, actionGroupName, object, viewMap, actionMap, actionGroupMap, popViews);
        }
    }

    //取消所有动作 恢复初始状态
    public void actionsCancel(HashMap<String, View> viewMap) {
        for (Map.Entry<String, View> entry : viewMap.entrySet()) {
            View view = entry.getValue();
            viewResume(view);
        }
    }

    public void viewResume(View view) {
        if (view == null)
            return;
        Animator animator = (Animator) view.getTag(R.id.animator);
        if (animator != null) {
            animator.cancel();
        }
        view.setScaleX(1);
        view.setScaleY(1);
        view.setAlpha(1);
        view.setRotation(0);
        view.setRotationX(0);
        view.setRotationY(0);
        PointF p = (PointF) view.getTag(R.id.viewPosition);
        if (p != null) {
            view.setX(p.x);
            view.setY(p.y);
        }
        view.setTag(R.id.viewEvent, -1);
        Drawable drawable = (Drawable) view.getTag(R.id.viewPic);
        if (drawable != null && view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(drawable);
        }
    }

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

    public EventContext getEventContext() {
        return eventContext;
    }

    public void setEventContext(EventContext eventContext) {
        this.eventContext = eventContext;
    }
}