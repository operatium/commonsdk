package com.reader.edit.c;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.qicaibear.bookplayer.c.CoordinatesConversion;
import com.qicaibear.bookplayer.c.Director;
import com.qicaibear.bookplayer.c.NodeControl;
import com.qicaibear.bookplayer.m.AdditionalObject;
import com.qicaibear.bookplayer.m.EventContext;
import com.qicaibear.bookplayer.m.client.AnimatorValue;
import com.qicaibear.bookplayer.m.client.Event;
import com.qicaibear.bookplayer.m.client.OrderAction;
import com.qicaibear.bookplayer.m.client.RectAreaAction;
import com.qicaibear.bookplayer.m.client.WidgetValue;
import com.qicaibear.bookplayer.v.view.SeamlessRollingView;
import com.yyx.commonsdk.app.GlideApp;
import com.yyx.commonsdk.baseclass.Size;
import com.yyx.commonsdk.log.MyLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class EditNodeControl extends NodeControl{

    /**
     * 节点解析
     */
    //创建节点
    public static View createNode(WidgetValue node
            , final Director director
            , View parentNode
            , Size parentNodeSize
            , CoordinatesConversion nodeConversion
            , final HashMap<String, AnimatorValue> actionMap
            , final HashMap<String, ArrayList<OrderAction>> actionGroupMap
            , final HashMap<String, View> viewMap
            , final HashMap<String, Event> eventMap
            , HashMap<String, ArrayList<RectAreaAction>> rectMap
            , final LinkedList<ImageView> popViews) {
        if (node == null)
            return null;
        //宽高缩放系数一致，坐标体系需要更换
        CoordinatesConversion parentConversion = createNewNodeCoordinatesConversion(nodeConversion, parentNodeSize);
        //计算节点的宽高
        Size viewSize = computeNodeSize(node, parentNodeSize, parentConversion);
        //创建对象
        View view = computeNodeView(director.getEventContext(), node, viewSize);
        //坐标
        computeNodePoint(view, node, parentConversion, viewSize);
        //保留参数
        saveParams(view, parentNodeSize, viewSize, parentConversion, node.getMyName());
        //添加父节点上
        addView(view, parentNode, node);
        //注册node
        viewMap.put(node.getMyName(), view);
//        //描述子节点
        ArrayList<WidgetValue> children = node.getChildren();
        if (children != null && !children.isEmpty()) {
            Collections.sort(children, new Comparator<WidgetValue>() {
                @Override
                public int compare(WidgetValue a, WidgetValue b) {
                    int az = (int) a.getPosition().getZ();
                    int bz = (int) b.getPosition().getZ();
                    return az - bz;
                }
            });
            for (WidgetValue child : children) {
                if (child != null)
                    createNode(child, director, view, viewSize, parentConversion, actionMap, actionGroupMap, viewMap, eventMap, rectMap, popViews);
            }
        }

        //区域触发动作
        CoordinatesConversion selfConversion = createNewNodeCoordinatesConversion(parentConversion, viewSize);
        ArrayList<RectAreaAction> nodeRect = rectMap.get(node.getMyName());
        final ArrayList<RectAreaAction> rectAreaActions = computNodeRect(nodeRect, selfConversion);
        if (rectAreaActions != null && !rectAreaActions.isEmpty()) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    MyLog.d("show", v.getTag(com.qicaibear.bookplayer.R.id.viewName) + " click " + event.getAction());
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setTag(com.qicaibear.bookplayer.R.id.point, new PointF(event.getX(), event.getY()));
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        PointF downP = (PointF) v.getTag(com.qicaibear.bookplayer.R.id.point);
                        PointF upP = new PointF(event.getX(), event.getY());
                        for (RectAreaAction it : rectAreaActions) {
                            boolean result1 = isContainsPoint(downP, it.getLeftTopX(), it.getLeftTopY(), it.getRightBottomX(), it.getRightBottomY());
                            boolean result2 = isContainsPoint(upP, it.getLeftTopX(), it.getLeftTopY(), it.getRightBottomX(), it.getRightBottomY());
                            boolean click = isClick(downP, upP);
                            if (click && result1 && result2) {
                                AdditionalObject object = new AdditionalObject();
                                object.setPointF(new PointF(event.getRawX(), event.getRawY()));
                                ArrayList<String> actions = it.getEventList();
                                for (String item : actions) {
                                    director.runEvent(item, object, viewMap, actionMap, actionGroupMap, eventMap, popViews);
                                }
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
        }
        return view;
    }

    //解析节点的视图
    public static View computeNodeView(EventContext eventContext, WidgetValue node, Size viewSize) {
        View view = null;
        Context context = eventContext.getContext();
        switch (node.getViewType()) {
            case "Layout":
                view = new ConstraintLayout(context);
//                view.setBackgroundColor(Color.BLUE);
                break;
            case "ImageView":
                view = new ImageView(context);
                final ImageView v = (ImageView) view;
                if (!TextUtils.isEmpty(node.getPic())) {
                    String path = eventContext.getPath() + "/" + node.getPic();
                    GlideApp.with(view).load(new File(path)).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            v.setTag(com.qicaibear.bookplayer.R.id.viewPic,resource);
                            v.setImageDrawable(resource);
                        }
                    });
                }else {
                    view.setBackgroundColor(Color.YELLOW);
                }
                ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case "SeamlessRollingView":
                view = new SeamlessRollingView(context);
//                view.setBackgroundColor(Color.WHITE);
                SeamlessRollingView viewitem = (SeamlessRollingView) view;
                String path = eventContext.getPath() + "/" + node.getPic();
                viewitem.initView(context, node.getPicAspectRatio(), viewSize, node.getDirection(), path);
                break;
        }
        return view;
    }
}
