package com.qicaixiong.reader.page.c;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.qicaixiong.reader.R;
import com.qicaixiong.reader.bookmodel.other.WidgetValue;
import com.qicaixiong.reader.bookmodel.animator.OrderAction;
import com.qicaixiong.reader.m.page.data.AdditionalObject;
import com.qicaixiong.reader.bookmodel.other.RectAreaAction;
import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;
import com.qicaixiong.reader.other.customview.SeamlessRollingView;
import com.qicaixiong.reader.resource.c.CoordinatesConversion;
import com.yyx.commonsdk.baseclass.Size;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;

import java.util.ArrayList;
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
    public static View createNode(final Director director, Context context
            , View parentNode, Size parentNodeSize
            , WidgetValue WidgetValue
            , CoordinatesConversion nodeConversion
            , final HashMap<String, AnimatorValue> actionMap
            , final HashMap<String, ArrayList<OrderAction>> actionGroupMap
            , final HashMap<String, View> viewMap
            , final HashMap<String,String> initActions
            , final LinkedList<ImageView> popViews) {
        //宽高缩放系数一致，坐标体系需要更换
        CoordinatesConversion parentConversion = createNewNodeCoordinatesConversion(nodeConversion, parentNodeSize);
        //计算节点的宽高
        Size viewSize = computeNodeSize(WidgetValue, parentNodeSize, parentConversion);
        //创建对象
        View view = computeNodeView(context, WidgetValue, viewSize);
        //坐标
        computeNodePoint(view, WidgetValue, parentConversion, viewSize);
        //添加父节点上
        addView(view, parentNode, WidgetValue);
        //注册node
        viewMap.put(WidgetValue.getMyName(), view);
        //保留参数
        saveParams(view, parentNodeSize, viewSize, parentConversion, WidgetValue.getMyName());
//        //描述子节点
//        ArrayList<WidgetValue> children = WidgetValue.getChildrenNode();
//        if (children != null && !children.isEmpty()) {
//            for (WidgetValue child : children) {
//                createNode(director, context, view, viewSize, child, parentConversion, actionMap, actionGroupMap, viewMap,initActions, popViews);
//            }
//        }

        //初始化动作
//        String initAction = WidgetValue.getInitAction();
//        if (!TextUtils.isEmpty(initAction))
//            initActions.put(WidgetValue.getMyName(),initAction);
/*
        //区域触发动作
        CoordinatesConversion selfConversion = createNewNodeCoordinatesConversion(parentConversion, viewSize);
        final ArrayList<RectAreaAction> rectAreaActions = computNodeRect(WidgetValue.getRectAction(), selfConversion);
        if (rectAreaActions != null && !rectAreaActions.isEmpty()) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setTag(R.id.point, new PointF(event.getX(), event.getY()));
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        PointF downP = (PointF) v.getTag(R.id.point);
                        PointF upP = new PointF(event.getX(), event.getY());
                        for (RectAreaAction it : rectAreaActions) {
                            boolean result1 = isContainsPoint(downP, it.getLeftTopX(), it.getLeftTopY(), it.getRightBottomX(), it.getRightBottomY());
                            boolean result2 = isContainsPoint(upP, it.getLeftTopX(), it.getLeftTopY(), it.getRightBottomX(), it.getRightBottomY());
                            if (result1 && result2) {
                                AdditionalObject object = new AdditionalObject();
                                object.setPointF(new PointF(event.getRawX(), event.getRawY()));
//                                ArrayList<OrderAction> actions = it.getActionValues();
//                                for (ActionValue item : actions) {
//                                    director.whoRunAction(item.getWho(), item.getActionGroup(), object, viewMap, actionMap, actionGroupMap,popViews);
//                                }
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
        }
        */
        return view;
    }

    //更新 转换类系数  缩放系数不变 中心点需要更改
    private static CoordinatesConversion createNewNodeCoordinatesConversion(CoordinatesConversion conversion, Size parentNodeSize) {
        CoordinatesConversion nodeConversion = new CoordinatesConversion();
        nodeConversion.setSceenFitFactory(conversion.getSceenFitFactory());
        nodeConversion.setCenter(new PointF(parentNodeSize.getWidth() / 2f, parentNodeSize.getHeight() / 2f));
        nodeConversion.setHeight(conversion.getHeight());
        nodeConversion.setWidth(conversion.getWidth());
        return nodeConversion;
    }

    //解析节点的宽高
    private static Size computeNodeSize(WidgetValue WidgetValue, Size parentNodeSize, CoordinatesConversion conversion) {
        int parentNodeFullWidth = parentNodeSize.getWidth();
        int parentNodeFullHeight = parentNodeSize.getHeight();
        Size viewSize = conversion.getLocationSize(WidgetValue.getViewSize());
        int w = WidgetValue.getViewSize().getWidth();
        int h = WidgetValue.getViewSize().getHeight();
        if (w < 0) {
            w = parentNodeFullWidth;
            viewSize.setWidth(w);
        }
        if (h < 0) {
            h = parentNodeFullHeight;
            viewSize.setHeight(h);
        }
        return viewSize;
    }

    //解析节点的坐标位置
    private static void computeNodePoint(View view, WidgetValue WidgetValue, CoordinatesConversion conversion, Size viewSize) {
        Point pointNode = null;
        if (WidgetValue.getPosition() != null) {
            pointNode = conversion.getLocationPoint(WidgetValue.getPosition());
        }
        if (pointNode == null)
            pointNode = new Point();
        //位置
        LayoutParamsHelp help = new LayoutParamsHelp<ConstraintLayout>(view)
                .creatConstraintLayoutLayoutParams(viewSize);
        //优先靠边
        if (WidgetValue.getSide() != null) {
            help.applyMargin_ConstraintLayout(WidgetValue.getSide().getLeft() == 0, WidgetValue.getSide().getTop() == 0
                    , WidgetValue.getSide().getRight() == 0, WidgetValue.getSide().getBottom() == 0);
            if (!WidgetValue.getSide().isAllZero()) {
                //不是全部都靠边
                if (WidgetValue.getSide().haveZero()) {
                    //坐标其次
                    if (!WidgetValue.getSide().landscapeHaveZero()) {
                        view.setX(pointNode.x);
                    }
                    if (!WidgetValue.getSide().portraitHaveZero()) {
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
    private static View computeNodeView(Context context, WidgetValue WidgetValue, Size viewSize) {
        View view = null;
        switch (WidgetValue.getViewType()) {
            case "Layout":
                view = new ConstraintLayout(context);
//                view.setBackgroundColor(Color.BLUE);
                break;
            case "ImageView":
                view = new ImageView(context);
//                view.setBackgroundColor(Color.YELLOW);
                if (WidgetValue.getPic() != null)
                    ((ImageView) view).setImageDrawable(ContextCompat.getDrawable(context, Integer.valueOf(WidgetValue.getPic())));
                ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case "SeamlessRollingView":
                view = new SeamlessRollingView(context);
//                view.setBackgroundColor(Color.WHITE);
                SeamlessRollingView viewitem = (SeamlessRollingView) view;
                viewitem.initView(context, WidgetValue.getPicAspectRatio(), viewSize, WidgetValue.getDirection(), WidgetValue.getPic());
                break;
        }
        return view;
    }

    //添加到父节点
    private static void addView(View view, View parentNode, WidgetValue WidgetValue) {
        if (parentNode != null && parentNode instanceof ConstraintLayout) {
            ((ConstraintLayout) parentNode).addView(view);
            MyLog.d("show", parentNode.getTag(R.id.viewName) + " addView (" + WidgetValue.getMyName() + ")");
        }
    }

    //触发动画时需要的参数
    private static void saveParams(View view, Size parentNodeSize, Size viewSize, CoordinatesConversion conversion, String viewName) {
        view.setTag(R.id.viewParentSize, parentNodeSize);
        view.setTag(R.id.viewSize, viewSize);
        view.setTag(R.id.viewCoordinatesConversion, conversion);
        view.setTag(R.id.viewName, viewName);
    }

    //解析节点上 区域的坐标
    private static ArrayList<RectAreaAction> computNodeRect(ArrayList<RectAreaAction> nodeRect, CoordinatesConversion conversion) {
        if (nodeRect == null || conversion == null)
            return nodeRect;
        ArrayList<RectAreaAction> result = new ArrayList<>();
        for (RectAreaAction it : nodeRect) {
            PointF leftTop = conversion.getLocationPoint(new PointF(it.getLeftTopX(), it.getLeftTopY()));
            PointF rightBottom = conversion.getLocationPoint(new PointF(it.getRightBottomX(), it.getRightBottomY()));
            RectAreaAction action = new RectAreaAction(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
            action.setActionValues(it.getActionValues());
            result.add(action);
        }
        return result;
    }

    //是否在区域内
    private static boolean isContainsPoint(PointF touch, PointF start, PointF end) {
        return (touch.x >= start.x && touch.x <= end.x
                && touch.y >= start.y && touch.y <= end.y);
    }

    private static boolean isContainsPoint(PointF touch, float leftTopX, float leftTopY, float rightBottomX, float rightBottomY) {
        return (touch.x >= leftTopX && touch.x <= rightBottomX
                && touch.y >= leftTopY && touch.y <= rightBottomY);
    }
}