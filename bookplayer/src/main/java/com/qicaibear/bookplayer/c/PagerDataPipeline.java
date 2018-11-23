package com.qicaibear.bookplayer.c;

import android.graphics.Point;
import android.graphics.PointF;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.qicaibear.bookplayer.m.client.*;
import com.qicaibear.bookplayer.m.client.immediately.*;
import com.qicaibear.bookplayer.m.client.order.*;
import com.qicaibear.bookplayer.m.server.*;
import com.yyx.commonsdk.baseclass.Point3D;
import com.yyx.commonsdk.baseclass.PointF3D;
import com.yyx.commonsdk.baseclass.Side;
import com.yyx.commonsdk.baseclass.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 每一页的数据转换类
 */
public class PagerDataPipeline {

    //解析page的json => 视图模型
    public WidgetValue createViewModel(String json
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> actionGrounpMap
            , ArrayList<Event> initEvent
            , HashMap<String, Event> eventMap
            , HashMap<String, ArrayList<RectAreaAction>> nodeRectActionMap) {
        if (TextUtils.isEmpty(json))
            return null;
        AnimatorResponseDto pageDb = JSON.parseObject(json, AnimatorResponseDto.class);
        if (pageDb == null)
            return null;
        if (actionMap == null)
            actionMap = new HashMap<>();
        actionMap.clear();
        if (actionGrounpMap == null)
            actionGrounpMap = new HashMap<>();
        actionGrounpMap.clear();
        if (initEvent == null)
            initEvent = new ArrayList<>();
        initEvent.clear();
        if (eventMap == null)
            eventMap = new HashMap<>();
        eventMap.clear();
        if (nodeRectActionMap == null)
            nodeRectActionMap = new HashMap<>();
        nodeRectActionMap.clear();

        //解析动作 动作组
        action(pageDb, actionMap, actionGrounpMap);
        //解析节点
        WidgetValue root = getRootNode(pageDb);
        //解析事件
        event(pageDb, eventMap, initEvent);
        //解析区域
        rect(pageDb, nodeRectActionMap);

        return root;
    }

    private void event(AnimatorResponseDto pageDb,HashMap<String,Event> eventMap,ArrayList<Event> initEvent){
         List<PbNodeGroupR> eventList = pageDb.getNodeGroupRList();
         for (PbNodeGroupR it : eventList){
             Event event = new Event(it.getNodeGroupRName());
             event.setNodeName(it.getNodeName());
             event.setActionGroupName(it.getGroupName());
             event.setStartDelay(it.getDelay());
             switch (it.getActionType()){//（1-初始化，2-区域点击）
                 case 1:
                     event.setTpye("init");
                     initEvent.add(event);
                     break;
                 case 2:
                     event.setTpye("rect");
                     break;
             }
             eventMap.put(event.getMyName(),event);
         }
    }

    private void rect(AnimatorResponseDto pageDb, HashMap<String, ArrayList<RectAreaAction>> nodeRectActionMap){
        //区域与事件的关系
        HashMap<String,ArrayList<String>> rectEventMap = new HashMap<>();
        List<PbRectNodeGroupRR> list = pageDb.getRectNodeGroupRRList();
        for (PbRectNodeGroupRR it : list) {
            String rectName = it.getRectName();
            String eventName = it.getNodeGroupRName();
            ArrayList<String> eventList = rectEventMap.get(rectName);
            if (eventList == null) {
                eventList = new ArrayList<>();
                rectEventMap.put(rectName, eventList);
            }
            eventList.add(eventName);
        }

        //节点与区域的关系
        List<PbNodeRectRDto> nodeRectRDtoList = pageDb.getNodeRectRDtoList();
        if (nodeRectActionMap != null){
            for (PbNodeRectRDto it : nodeRectRDtoList){
                PointF lefttop = null,rightbottom = null;
                if (!TextUtils.isEmpty(it.getLeftTop()) && !TextUtils.isEmpty(it.getRightBottom())){
                    lefttop = JSON.parseObject(it.getLeftTop(),PointF.class);
                    rightbottom = JSON.parseObject(it.getRightBottom(),PointF.class);
                }
                if (lefttop != null && rightbottom != null){
                    //创建区域对象
                    RectAreaAction areaAction = new RectAreaAction(lefttop.x,lefttop.y,rightbottom.x,rightbottom.y);

                    String nodeName = it.getNodeName();
                    String rectName = it.getRectName();
                    areaAction.setViewName(nodeName);

                    //区域添加到map
                    ArrayList<RectAreaAction> nodeRects = nodeRectActionMap.get(nodeName);
                    if (nodeRects == null){
                        nodeRects = new ArrayList<>();
                        nodeRectActionMap.put(nodeName,nodeRects);
                    }
                    nodeRects.add(areaAction);

                    //给区域提供事件list
                    ArrayList<String> eventList = rectEventMap.get(rectName);
                    if (eventList != null)
                        areaAction.setEventList(eventList);
                }
            }
        }
    }

    private WidgetValue getRootNode(AnimatorResponseDto pageDb){
        //节点
        List<PbNodeDto> nodes = pageDb.getNodes();
        HashMap<String, ArrayList<String>> parentAndChild = new HashMap<>();
        HashMap<String, WidgetValue> widgets = new HashMap<>();
        String rootName = null;
        for (PbNodeDto it : nodes) {
            //1.Layout, 2.ImageView, 3.SeamlessRollingView
            int type = it.getNodeType();
            String typeStr = null;
            switch (type) {
                case 1:
                    typeStr = "Layout";
                    break;
                case 2:
                    typeStr = "ImageView";
                    break;
                case 3:
                    typeStr = "SeamlessRollingView";
                    break;
            }
            if (TextUtils.isEmpty(typeStr))
                continue;
            WidgetValue widgetValue = new WidgetValue(it.getNodeName(), typeStr);
            if (!TextUtils.isEmpty(it.getResourceName()))
                widgetValue.setPic(it.getResourceName());
            if (!TextUtils.isEmpty(it.getNodeSize())) {
                Size size = JSON.parseObject(it.getNodeSize(), Size.class);
                if (size != null)
                    widgetValue.setViewSize(size);
            }
            if (!TextUtils.isEmpty(it.getNodeSide())) {
                Side side = JSON.parseObject(it.getNodeSide(), Side.class);
                if (side != null)
                    widgetValue.setSide(side);
            }
            if (!TextUtils.isEmpty(it.getPoint())) {
                PointF pointF = JSON.parseObject(it.getPoint(), PointF.class);
                if (pointF != null) {
                    widgetValue.setPosition(new PointF3D(pointF.x, pointF.y, it.getSort()));
                }
            }
            widgetValue.setPicAspectRatio(it.getPicAspectRadio());
            widgetValue.setDirection(it.getDirection());
            //插入
            widgets.put(widgetValue.getMyName(),widgetValue);
            String parentName = it.getParentNodeName();
            if (TextUtils.isEmpty(parentName))
                rootName = widgetValue.getMyName();
            ArrayList<String> children = parentAndChild.get(parentName);
            if (children == null){
                children = new ArrayList<>();
                parentAndChild.put(parentName,children);
            }
            children.add(widgetValue.getMyName());
        }
        //组装
        createNode(rootName,parentAndChild,widgets);
        return widgets.get(rootName);
    }

    //组装节点
    private void createNode(String rootNodeName,HashMap<String, ArrayList<String>> parentAndChild,HashMap<String, WidgetValue> widgets){
        ArrayList<String> childrenName = parentAndChild.get(rootNodeName);
        WidgetValue widgetValue = widgets.get(rootNodeName);
        if (widgetValue != null && childrenName != null && !childrenName.isEmpty()) {
            ArrayList<WidgetValue> children = new ArrayList<>();
            for (String it : childrenName) {
                WidgetValue childNode = widgets.get(it);
                children.add(childNode);
                createNode(it,parentAndChild,widgets);
            }
            widgetValue.setChildren(children);
        }
    }

    //解析动作
    private void action(AnimatorResponseDto pageDb, HashMap<String, AnimatorValue> actionMap, HashMap<String, ArrayList<OrderAction>> actionGrounpMap){
        //动作
        if (actionMap == null)
            actionMap = new HashMap<>();
        //帧动画
        addMap_frameAnimtor(pageDb.getPbFrameAnimators(), actionMap);
        //弹出动画
        addMap_popAnimator(pageDb.getPbPopViewAnimators(), actionMap);
        //无缝滚动动画
        addMap_SeamlessRollingAnimator(pageDb.getPbSeamRollingAnimators(), actionMap);
        //声音动画
        addMap_soundAnimator(pageDb.getPbSoundAnimators(), actionMap);
        //透明度动画
        addMap_alphaAnimator(pageDb.getPbAlphaAnimators(), actionMap);
        //贝塞尔曲线动画
        addMap_besselAnimator(pageDb.getPbBesselAnimators(), actionMap);
        //旋转动画
        addMap_rotateAnimator(pageDb.getPbRotateAnimators(), actionMap);
        //缩放动画
        addMap_scaleAnimator(pageDb.getPbScaleAnimators(), actionMap);
        //靠边动画
        addMap_sideAnimator(pageDb.getPbSideAnimators(), actionMap);
        //平移动画
        addMap_tanslationAnimator(pageDb.getPbTranslationAnimators(), actionMap);

        //动作组
        if (actionGrounpMap == null)
            actionGrounpMap = new HashMap<>();

        List<PbActionGroup> groups = pageDb.getGroups();
        for (PbActionGroup it : groups) {
            String groupName = it.getGroupName();
            String actionName = it.getActionName();
            int sort = it.getSort();
            ArrayList<OrderAction> group = actionGrounpMap.get(groupName);
            if (group == null) {
                group = new ArrayList<>();
                actionGrounpMap.put(groupName, group);
            }
            group.add(new OrderAction(actionName, sort));
        }
    }

    //平移动画 列表 处理
    private void addMap_tanslationAnimator(List<PbTranslationAnimator> list, HashMap<String, AnimatorValue> actionMap) {
        if (list == null)
            return;
        for (PbTranslationAnimator it : list) {
            TranslationAnimator animator = createTranslationAction(it);
            actionMap.put(animator.getMyName(), animator);
        }
    }
    //解析单一 平移动画 对象
    private TranslationAnimator createTranslationAction(PbTranslationAnimator it) {
        TranslationAnimator action = new TranslationAnimator(it.getTranslationName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        action.setRepeatCount(it.getRepeatCount());
        action.setRepeatMode(it.getRepeatMode());
        action.setStartDelay(it.getDelayTime());
        action.setTime(it.getTranslationTime());
        if (!TextUtils.isEmpty(it.getTranslationX())){
            List<Float> scaleX = JSON.parseArray(it.getTranslationX(),Float.class);
            Float[] floats = new Float[scaleX.size()];
            scaleX.toArray(floats);
            action.setTranslationX(floats);
        }
        if (!TextUtils.isEmpty(it.getTranslationY())){
            List<Float> scaleY = JSON.parseArray(it.getTranslationY(),Float.class);
            Float[] floats = new Float[scaleY.size()];
            scaleY.toArray(floats);
            action.setTranslationY(floats);
        }
        return action;
    }

    //靠边动画 列表 处理
    private void addMap_sideAnimator(List<PbSideAnimator> list, HashMap<String, AnimatorValue> actionMap) {
        if (list == null)
            return;
        for (PbSideAnimator it : list) {
            SideAnimator animator = createSideAction(it);
            actionMap.put(animator.getMyName(), animator);
        }
    }
    //解析单一 靠边动画 对象
    private SideAnimator createSideAction(PbSideAnimator it) {
        SideAnimator action = new SideAnimator(it.getSideName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        action.setRepeatCount(it.getRepeatCount());
        action.setRepeatMode(it.getRepeatMode());
        action.setStartDelay(it.getDelayTime());
        action.setTime(it.getSideTime());
        if (!TextUtils.isEmpty(it.getPoint())) {
            PointF p = JSON.parseObject(it.getPoint(), PointF.class);
            action.setPoint(p);
        }
        if (!TextUtils.isEmpty(it.getFromSide())) {
            Side p = JSON.parseObject(it.getFromSide(), Side.class);
            action.setFormSide(p);
        }
        if (!TextUtils.isEmpty(it.getToSide())) {
            Side p = JSON.parseObject(it.getToSide(), Side.class);
            action.setToSide(p);
        }
        action.setParentViewSize(new Size(Float.valueOf(it.getParentNodeWidth()).intValue()
                , Float.valueOf(it.getSideHeight()).intValue()));
        return action;
    }

    //缩放动画 列表 处理
    private void addMap_scaleAnimator(List<PbScaleAnimator> list, HashMap<String, AnimatorValue> actionMap) {
        if (list == null)
            return;
        for (PbScaleAnimator it : list) {
            ScaleAnimator animator = createScaleAction(it);
            actionMap.put(animator.getMyName(), animator);
        }
    }
    //解析单一 缩放动画 对象
    private ScaleAnimator createScaleAction(PbScaleAnimator it) {
        ScaleAnimator action = new ScaleAnimator(it.getScaleName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        action.setRepeatCount(it.getRepeatCount());
        action.setRepeatMode(it.getRepeatMode());
        action.setStartDelay(it.getDelayTime());
        action.setTime(it.getScaleTime());
        if (!TextUtils.isEmpty(it.getPoint())){
            action.setPivot(JSON.parseObject(it.getPoint(),PointF.class));
        }
        if (!TextUtils.isEmpty(it.getScaleX())){
            List<Float> scaleX = JSON.parseArray(it.getScaleX(),Float.class);
            Float[] floats = new Float[scaleX.size()];
            scaleX.toArray(floats);
            action.setScaleX(floats);
        }
        if (!TextUtils.isEmpty(it.getScaleY())){
            List<Float> scaleY = JSON.parseArray(it.getScaleY(),Float.class);
            Float[] floats = new Float[scaleY.size()];
            scaleY.toArray(floats);
            action.setScaleY(floats);
        }
        return action;
    }

    //旋转动画 列表 处理
    private void addMap_rotateAnimator(List<PbRotateAnimator> list, HashMap<String, AnimatorValue> actionMap) {
        if (list == null)
            return;
        for (PbRotateAnimator it : list) {
            RotateAnimator animator = createRotateAction(it);
            actionMap.put(animator.getMyName(), animator);
        }
    }
    //解析单一 旋转动画 对象
    private RotateAnimator createRotateAction(PbRotateAnimator it) {
        RotateAnimator action = new RotateAnimator(it.getRotateName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        action.setRepeatCount(it.getRepeatCount());
        action.setRepeatMode(it.getRepeatMode());
        action.setStartDelay(it.getDelayTime());
        action.setTime(it.getRotateTime());
        action.setPropertyName(it.getPropertyName());
        if (!TextUtils.isEmpty(it.getRotation())) {
            List<Float> rotato = JSON.parseArray(it.getRotation(), Float.class);
            if (rotato != null) {
                Float[] floats = new Float[rotato.size()];
                rotato.toArray(floats);
                action.setRotationValues(floats);
            }
        }
        if (!TextUtils.isEmpty(it.getPoint())) {
            PointF p = JSON.parseObject(it.getPoint(), PointF.class);
            action.setPivot(p);
        }
        return action;
    }

    //贝塞尔曲线动画 列表 处理
    private void addMap_besselAnimator(List<PbBesselAnimator> list, HashMap<String, AnimatorValue> actionMap) {
        if (list == null)
            return;
        for (PbBesselAnimator it : list) {
            BesselAnimator animator = createBesselAction(it);
            actionMap.put(animator.getMyName(), animator);
        }
    }

    //解析单一 贝塞尔曲线动画 对象
    private BesselAnimator createBesselAction(PbBesselAnimator it) {
        BesselAnimator action = new BesselAnimator(it.getBesselName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        action.setRepeatCount(it.getRepeatCount());
        action.setRepeatMode(it.getRepeatMode());
        action.setStartDelay(it.getDelayTime());
        action.setTime(it.getBesselTime());
        if (!TextUtils.isEmpty(it.getPoint())) {//[{"x":0,"y":0},{"x":300,"y":-300},{"x":600,"y":0}]
            List<PointF> points = JSON.parseArray(it.getPoint(),PointF.class);
            if (points != null) {
                PointF[] fs = new PointF[points.size()];
                points.toArray(fs);
                action.setPoints(fs);
            }
        }
        return action;
    }

    //透明度动画 列表 处理
    private void addMap_alphaAnimator(List<PbAlphaAnimator> list, HashMap<String, AnimatorValue> actionMap){
        if (list == null)
            return;
        for (PbAlphaAnimator it : list) {
            AlphaAnimator animator = createAlphaAction(it);
            actionMap.put(animator.getMyName(),animator);
        }
    }
    //解析单一透明度动画对象
    private AlphaAnimator createAlphaAction(PbAlphaAnimator it){
        AlphaAnimator action = new AlphaAnimator(it.getAlphaName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        if (!TextUtils.isEmpty(it.getAlpha())) {
            List<Float> alpha = JSON.parseArray(it.getAlpha(),Float.class);
            if (alpha != null) {
                Float[] floats = new Float[alpha.size()];
                alpha.toArray(floats);
                action.setAlpha(floats);
            }
        }
        action.setStartDelay(it.getDelayTime());
        action.setTime(it.getAlphaTime());
        action.setRepeatCount(it.getRepeatCount());
        action.setRepeatMode(it.getRepeatMode());
        return action;
    }

    //声音动画 列表 处理
    private void addMap_soundAnimator(List<PbSoundAnimator> list, HashMap<String, AnimatorValue> actionMap){
        if (list == null)
            return;
        for (PbSoundAnimator it : list) {
            SoundAnimator animator = createSoundAction(it);
            actionMap.put(animator.getMyName(),animator);
        }
    }
    //解析单一声音动画对象
    private SoundAnimator createSoundAction(PbSoundAnimator it){
        SoundAnimator action = new SoundAnimator(it.getSoundName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        action.setTime(it.getSoundTime());
        action.setContent(it.getContent());
        action.setRepeatCount(it.getRepeatCount());
        action.setSoundPath(it.getResourceName());
        action.setSoundVol(it.getSoundVol());
        action.setAudioTrack(it.getSoundTrack());
        return action;
    }


    //无缝滚动动画 列表 处理
    private void addMap_SeamlessRollingAnimator(List<PbSeamRollingAnimator> list, HashMap<String, AnimatorValue> actionMap){
        if (list == null)
            return;
        for (PbSeamRollingAnimator it : list) {
            SeamlessRollingAnimator animator = createSeamRollingAction(it);
            actionMap.put(animator.getMyName(),animator);
        }
    }
    //解析单一无缝滚动动画对象
    private SeamlessRollingAnimator createSeamRollingAction(PbSeamRollingAnimator it){
        SeamlessRollingAnimator action = new SeamlessRollingAnimator(it.getSeamRollingName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        action.setStartDelay(it.getDelayTime());
        action.setTime(it.getSeamRollingTime());
        action.setPlay(it.getPlay() == 0);
        return action;
    }

    //弹出动画 列表 处理
    private void addMap_popAnimator(List<PbPopViewAnimator> list, HashMap<String, AnimatorValue> actionMap){
        if (list == null)
            return;
        for (PbPopViewAnimator it : list) {
            PopViewAnimator animator = createPopAction(it);
            actionMap.put(animator.getMyName(),animator);
        }
    }
    //解析单一弹出动画对象
    private PopViewAnimator createPopAction(PbPopViewAnimator it){
        PopViewAnimator action = new PopViewAnimator(it.getPopName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());
        if (!TextUtils.isEmpty(it.getFromPoint())) {
            PointF p = JSON.parseObject(it.getFromPoint(),PointF.class);
            action.setFromPoint(p);
        }
        if (!TextUtils.isEmpty(it.getToPoint())) {
            PointF p = JSON.parseObject(it.getToPoint(),PointF.class);
            action.setToPoint(p);
        }
        action.setViewSize(new Size(it.getWidth(),it.getHeight()));
        action.setTime(it.getPopTime());
        action.setPic(it.getResourceName());
        return action;
    }


    //帧动画列表处理
    private void addMap_frameAnimtor(List<PbFrameAnimator> list,HashMap<String, AnimatorValue> actionMap){
        if (list == null)
            return;
        for (PbFrameAnimator it : list) {
            FrameAnimator frameAnimator = createFrameAction(it);
            actionMap.put(frameAnimator.getMyName(),frameAnimator);
        }
    }
    //解析单一帧动画对象
    private FrameAnimator createFrameAction(PbFrameAnimator it){
        FrameAnimator action = new FrameAnimator(it.getFrameName());
        action.setBookId(it.getBookId());
        action.setPageId(it.getBookDetailId());

        //帧
        if (!TextUtils.isEmpty(it.getFramePics())) {
            ArrayList<String> picList = new ArrayList<>(JSON.parseArray(it.getFramePics(), String.class));
            action.setListPic(picList);
        }
        //帧时间
        if (!TextUtils.isEmpty(it.getFrameTimes())) {
            ArrayList<Integer> timeList = new ArrayList<>(JSON.parseArray(it.getFrameTimes(), Integer.class));
            action.setListTime(timeList);
        }
        action.setPicSize(new Size(it.getFrameWidth(), it.getFrameHeight()));
        action.setOneShot(it.getIsOneShot() == 1);
        return action;
    }
}
