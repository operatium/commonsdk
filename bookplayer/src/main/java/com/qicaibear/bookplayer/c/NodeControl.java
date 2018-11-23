package com.qicaibear.bookplayer.c;

import android.content.Context;
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
import com.qicaibear.bookplayer.R;
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
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by admin on 2018/10/29.
 */

public class NodeControl {
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
                    MyLog.d("show", v.getTag(R.id.viewName) + " click " + event.getAction());
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setTag(R.id.point, new PointF(event.getX(), event.getY()));
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        PointF downP = (PointF) v.getTag(R.id.point);
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

    //更新 转换类系数  缩放系数不变 中心点需要更改
    public static CoordinatesConversion createNewNodeCoordinatesConversion(CoordinatesConversion conversion, Size parentNodeSize) {
        CoordinatesConversion nodeConversion = new CoordinatesConversion();
        nodeConversion.setSceenFitFactory(conversion.getSceenFitFactory());
        nodeConversion.setCenter(new PointF(parentNodeSize.getWidth() / 2f, parentNodeSize.getHeight() / 2f));
        nodeConversion.setHeight(conversion.getHeight());
        nodeConversion.setWidth(conversion.getWidth());
        return nodeConversion;
    }

    //解析节点的宽高
    public static Size computeNodeSize(WidgetValue node, Size parentNodeSize, CoordinatesConversion conversion) {
        int parentNodeFullWidth = parentNodeSize.getWidth();
        int parentNodeFullHeight = parentNodeSize.getHeight();
        Size viewSize = conversion.getLocationSize(node.getViewSize());
        int w = node.getViewSize().getWidth();
        int h = node.getViewSize().getHeight();
        if (w < 0) {
            w = parentNodeFullWidth;
            viewSize.setWidth(w);
        }
        if (h < 0) {
            h = parentNodeFullHeight;
            viewSize.setHeight(h);
        }
        MyLog.d("show", node.getMyName() + "of viewSize = " + viewSize.toString());
        return viewSize;
    }

    //解析节点的坐标位置
    public static void computeNodePoint(View view, WidgetValue node, CoordinatesConversion conversion, Size viewSize) {
        PointF pointNode = null;
        if (node.getPosition() != null) {
            pointNode = conversion.getLocationPoint(new PointF(node.getPosition().getX(), node.getPosition().getY()));
        }
        if (pointNode == null)
            pointNode = new PointF();
        //位置
        LayoutParamsHelp help = new LayoutParamsHelp<ConstraintLayout>(view)
                .creatConstraintLayoutLayoutParams(viewSize);
        //优先靠边
        if (node.getSide() != null) {
            help.applyMargin_ConstraintLayout(node.getSide().getLeft() == 0, node.getSide().getTop() == 0
                    , node.getSide().getRight() == 0, node.getSide().getBottom() == 0);
            if (!node.getSide().isAllZero()) {
                //不是全部都靠边
                if (node.getSide().haveZero()) {
                    //坐标其次
                    if (!node.getSide().landscapeHaveZero()) {
                        view.setX(pointNode.x);
                    }
                    if (!node.getSide().portraitHaveZero()) {
                        view.setY(pointNode.y);
                    }
                } else {
                    view.setX(pointNode.x);
                    view.setY(pointNode.y);
                }
            }
        } else {
            view.setX(pointNode.x);
            view.setY(pointNode.y);
        }
        help.apply();
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
//                view.setBackgroundColor(Color.YELLOW);
                final ImageView v = (ImageView) view;
                if (!TextUtils.isEmpty(node.getPic())) {
                    String path = eventContext.getPath() + "/" + node.getPic();
                    GlideApp.with(view).load(new File(path)).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            v.setTag(R.id.viewPic,resource);
                            v.setImageDrawable(resource);
                        }
                    });
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

    //添加到父节点
    public static void addView(View view, View parentNode, WidgetValue node) {
        if (parentNode != null && parentNode instanceof ConstraintLayout) {
            int z = Float.valueOf(node.getPosition().getZ()).intValue();
            ((ConstraintLayout) parentNode).addView(view, z);
            MyLog.d("show", parentNode.getTag(R.id.viewName) + " addView (" + node.getMyName() + ", " + z + ")");
        }
    }

    //触发动画时需要的参数
    public static void saveParams(final View view, Size parentNodeSize, Size viewSize, CoordinatesConversion conversion, final String viewName) {
        view.setTag(R.id.viewParentSize, parentNodeSize);
        view.setTag(R.id.viewSize, viewSize);
        view.setTag(R.id.viewCoordinatesConversion, conversion);
        view.setTag(R.id.viewName, viewName);
    }

    //解析节点上 区域的坐标
    public static ArrayList<RectAreaAction> computNodeRect(ArrayList<RectAreaAction> nodeRect, CoordinatesConversion conversion) {
        if (nodeRect == null || conversion == null)
            return nodeRect;
        ArrayList<RectAreaAction> result = new ArrayList<>();
        for (RectAreaAction it : nodeRect) {
            PointF leftTop = conversion.getLocationPoint(new PointF(it.getLeftTopX(), it.getLeftTopY()));
            PointF rightBottom = conversion.getLocationPoint(new PointF(it.getRightBottomX(), it.getRightBottomY()));
            RectAreaAction action = new RectAreaAction(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
            action.setEventList(it.getEventList());
            result.add(action);
        }
        return result;
    }

    //是否在区域内
    public static boolean isContainsPoint(PointF touch, PointF start, PointF end) {
        MyLog.d("show","touch ("+ touch.toString()+")"+"start ("+ start.toString()+")"+"end ("+ end.toString()+")");
        return (touch.x >= start.x && touch.x <= end.x
                && touch.y >= start.y && touch.y <= end.y);
    }

    public static boolean isContainsPoint(PointF touch, float leftTopX, float leftTopY, float rightBottomX, float rightBottomY) {
        MyLog.d("show", "touch (" + touch.toString() + ")" + "start (" + leftTopX + " , " + leftTopY + ")"
                + "end (" + rightBottomX + " , " + rightBottomY + ")");
        return (touch.x >= leftTopX && touch.x <= rightBottomX
                && touch.y >= leftTopY && touch.y <= rightBottomY);
    }

    public static boolean isClick(PointF down, PointF up){
        MyLog.d("show", "isClick (" + down.toString() + ")" + "up (" + up.toString() + ")");
        float x = Math.abs(down.x - up.x);
        float y = Math.abs(down.y - up.y);
        return x < 30 && y < 30;
    }
}