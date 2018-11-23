package com.qicaibear.bookplayer.c;

import android.graphics.PointF;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.qicaibear.bookplayer.R;
import com.qicaibear.bookplayer.m.client.AnimatorValue;
import com.qicaibear.bookplayer.m.client.Event;
import com.qicaibear.bookplayer.m.client.OrderAction;
import com.qicaibear.bookplayer.m.client.RectAreaAction;
import com.qicaibear.bookplayer.m.client.WidgetValue;
import com.qicaibear.bookplayer.m.client.immediately.FrameAnimator;
import com.qicaibear.bookplayer.m.client.immediately.PopViewAnimator;
import com.qicaibear.bookplayer.m.client.immediately.SeamlessRollingAnimator;
import com.qicaibear.bookplayer.m.client.immediately.SoundAnimator;
import com.qicaibear.bookplayer.m.client.order.AlphaAnimator;
import com.qicaibear.bookplayer.m.client.order.BesselAnimator;
import com.qicaibear.bookplayer.m.client.order.RotateAnimator;
import com.qicaibear.bookplayer.m.client.order.ScaleAnimator;
import com.qicaibear.bookplayer.m.client.order.SideAnimator;
import com.qicaibear.bookplayer.m.client.order.TranslationAnimator;
import com.qicaibear.bookplayer.m.server.AnimatorResponseDto;
import com.qicaibear.bookplayer.m.server.PbActionGroup;
import com.qicaibear.bookplayer.m.server.PbAlphaAnimator;
import com.qicaibear.bookplayer.m.server.PbBesselAnimator;
import com.qicaibear.bookplayer.m.server.PbBooks;
import com.qicaibear.bookplayer.m.server.PbBooksDetail;
import com.qicaibear.bookplayer.m.server.PbFrameAnimator;
import com.qicaibear.bookplayer.m.server.PbNodeDto;
import com.qicaibear.bookplayer.m.server.PbNodeGroupR;
import com.qicaibear.bookplayer.m.server.PbNodeRectRDto;
import com.qicaibear.bookplayer.m.server.PbPopViewAnimator;
import com.qicaibear.bookplayer.m.server.PbRectNodeGroupRR;
import com.qicaibear.bookplayer.m.server.PbRotateAnimator;
import com.qicaibear.bookplayer.m.server.PbScaleAnimator;
import com.qicaibear.bookplayer.m.server.PbSeamRollingAnimator;
import com.qicaibear.bookplayer.m.server.PbSideAnimator;
import com.qicaibear.bookplayer.m.server.PbSoundAnimator;
import com.qicaibear.bookplayer.m.server.PbTranslationAnimator;
import com.yyx.commonsdk.baseclass.PointF3D;
import com.yyx.commonsdk.baseclass.Side;
import com.yyx.commonsdk.baseclass.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 每一页的数据转换类
 */
public class PagerDataPipelineToJson {

    //视图模型  ==> page的json
    public AnimatorResponseDto parseJson(int bookId, int pageId, WidgetValue node
            , HashMap<String, View> viewMap
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> actionGrounpMap
            , ArrayList<Event> initEvent
            , HashMap<String, Event> eventMap
            , HashMap<String, ArrayList<RectAreaAction>> nodeRectActionMap) {

        AnimatorResponseDto data = new AnimatorResponseDto();

        //节点
        ArrayList<PbNodeDto> pbNodeDtos = new ArrayList<>();
        getPbNodeDto(bookId,pageId,node,viewMap,pbNodeDtos);
        data.setNodes(pbNodeDtos);

        return data;
    }

    //装节点
    private void getPbNodeDto(int bookId, int pageId, WidgetValue node,HashMap<String, View> viewMap, ArrayList<PbNodeDto> pbNodes){
        String viewName = node.getMyName();
        //宽高缩放系数一致，坐标体系需要更换
        View view = viewMap.get(viewName);
        CoordinatesConversion parentConversion = (CoordinatesConversion) view.getTag(R.id.viewCoordinatesConversion);
        //计算节点的宽高
        Size viewSize = node.getViewSize();
        //坐标
        PointF position = new PointF(node.getPosition().getX(),node.getPosition().getY());
        float z = node.getPosition().getZ();
//        //描述子节点
        ArrayList<WidgetValue> children = node.getChildren();
        if (children != null && !children.isEmpty()) {
            for (WidgetValue child : children) {
                if (child != null)
                    getPbNodeDto(bookId, pageId, child, viewMap, pbNodes);
            }
        }
        PbNodeDto p = new PbNodeDto();
        p.setBookId(bookId);
        p.setBookDetailId(pageId);
        p.setNodeName(viewName);
        p.setPoint(JSON.toJSONString(position));
        p.setNodeSize(JSON.toJSONString(viewSize));
        pbNodes.add(p);
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
