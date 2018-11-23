//package com.qicaixiong.reader.page.c;
//
//import android.graphics.Point;
//import android.graphics.PointF;
//
//import com.qicaixiong.reader.R;
//import com.qicaixiong.reader.bookmodel.other.WidgetValue;
//import com.qicaixiong.reader.bookmodel.other.RectAreaAction;
//import com.qicaixiong.reader.bookmodel.animator.immediately.PopViewAnimator;
//import com.qicaixiong.reader.bookmodel.animator.immediately.SoundAnimator;
//import com.qicaixiong.reader.bookmodel.animator.order.AlphaAnimator;
//import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;
//import com.qicaixiong.reader.bookmodel.animator.order.BesselAnimator;
//import com.qicaixiong.reader.bookmodel.animator.immediately.FrameAnimator;
//import com.qicaixiong.reader.bookmodel.animator.order.RotateAnimator;
//import com.qicaixiong.reader.bookmodel.animator.order.ScaleAnimator;
//import com.qicaixiong.reader.bookmodel.animator.immediately.SeamlessRollingAnimator;
//import com.qicaixiong.reader.bookmodel.animator.order.TranslationAnimator;
//import com.qicaixiong.reader.resource.c.FileController;
//
//import com.yyx.commonsdk.baseclass.Side;
//import com.yyx.commonsdk.baseclass.Size;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static android.view.animation.Animation.RESTART;
//import static android.view.animation.Animation.REVERSE;
//
///**
// * Created by admin on 2018/10/19.
// */
//
//public class NewPageFragmentControl {
//
//    public WidgetValue page1() {
//        //背景
//        WidgetValue root = new WidgetValue("rootNode", "Layout");
//        root.setSide(new Side(0, 0, 0, 0));
//        root.setViewSize(new Size(-1, -1));
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        children.add(createImageView("tiegui",R.drawable.home_background_guidao));
//        root.setChildrenNode(children);
//        return root;
//    }
//
//    public WidgetValue page2() {
//        //背景
//        WidgetValue root = new WidgetValue("rootNode", "Layout");
//        root.setSide(new Side(0, 0, 0, 0));
//        root.setViewSize(new Size(-1, -1));
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        children.add(createImageView("light",R.drawable.home_tree_left_light));
//        root.setChildrenNode(children);
//        return root;
//    }
//
//    public WidgetValue page3() {
//        //背景
//        WidgetValue root = new WidgetValue("rootNode", "Layout");
//        root.setSide(new Side(0, 0, 0, 0));
//        root.setViewSize(new Size(-1, -1));
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        children.add(createImageView("bird",R.drawable.home_background_yellowbird_00));
//        root.setChildrenNode(children);
//        return root;
//    }
//
//    public WidgetValue page4() {
//        //背景
//        WidgetValue root = new WidgetValue("rootNode", "Layout");
//        root.setSide(new Side(0, 0, 0, 0));
//        root.setViewSize(new Size(-1, -1));
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        children.add(createImageView("door",R.drawable.home_tree_left_opendoor00));
//        root.setChildrenNode(children);
//        return root;
//    }
//
//
//    private WidgetValue createImageView(String name, int res){
//        WidgetValue WidgetValue = new WidgetValue(name, "ImageView");
//        WidgetValue.setPosition(new Point(0, 0));
//        WidgetValue.setViewSize(new Size(100, 100));
//        WidgetValue.setPic("" + res);
//        return WidgetValue;
//    }
//
//
//
//    public WidgetValue demo(HashMap<String, AnimatorValue> actionMap, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        //背景
//        WidgetValue root = new WidgetValue("rootNode", "Layout");
//        root.setSide(new Side(0, 0, 0, 0));
//        root.setViewSize(new Size(-1, -1));
//
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        //鸟
//        children.add(bird(actionMap, grounpMap));
//
//        root.setChildrenNode(children);
//        return root;
//    }
//
//    public WidgetValue demo2(HashMap<String, AnimatorValue> actionMap, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        //背景
//        WidgetValue root = new WidgetValue("rootNode", "Layout");
//        root.setSide(new Side(0, 0, 0, 0));
//        root.setViewSize(new Size(-1, -1));
//
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        //鸟
//        children.add(bird(actionMap, grounpMap));
//        //树节点
//        children.add(createleftNode(actionMap, grounpMap));
//
//        root.setChildrenNode(children);
//        return root;
//    }
//
//    public WidgetValue demo3(HashMap<String, AnimatorValue> actionMap, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        //背景
//        WidgetValue root = new WidgetValue("rootNode", "Layout");
//        root.setSide(new Side(0, 0, 0, 0));
//        root.setViewSize(new Size(-1, -1));
//
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        //鸟
//        children.add(bird(actionMap, grounpMap));
//        //树节点
//        children.add(createleftNode(actionMap, grounpMap));
//        //草
//        children.add(cao(actionMap, grounpMap));
//
//        root.setChildrenNode(children);
//        return root;
//    }
//    /**
//     * 例子
//     */
//    public WidgetValue demo4(HashMap<String, AnimatorValue> actionMap, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        //背景
//        WidgetValue root = new WidgetValue("rootNode", "Layout");
//        root.setSide(new Side(0, 0, 0, 0));
//        root.setViewSize(new Size(-1, -1));
//
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        //鸟
//        children.add(bird(actionMap, grounpMap));
//        //铁轨
//        children.add(tiegui(actionMap, grounpMap));
//        //树节点
//        children.add(createleftNode(actionMap, grounpMap));
//        //草
//        children.add(cao(actionMap, grounpMap));
//
//        root.setChildrenNode(children);
//
//        SoundAnimator soundAnimator = new SoundAnimator("backgroundsound");
//        soundAnimator.setContent("org");
//        soundAnimator.setRepeatCount(0);
//        soundAnimator.setSoundPath(FileController.getDownloadDirectory() + "/04ef80a981ca5892b129e0a72bdf41a90753f6af.mp3");
//        soundAnimator.setTime(1000);
//        soundAnimator.setSoundVol(1);
//        actionMap.put(soundAnimator.getMyName(), soundAnimator);
//
//        ArrayList<AnimatorKey> grounp = new ArrayList<>();
//        grounp.add(new AnimatorKey(soundAnimator.getMyName(), 1));
//
//        grounpMap.put("beijinginit",grounp);
//
//        root.setInitAction("beijinginit");
//        return root;
//    }
//
//    //树布局器
//    private WidgetValue createleftNode(HashMap<String, AnimatorValue> actionMap, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        WidgetValue WidgetValue = new WidgetValue("leftnode", "Layout");
//        WidgetValue.setSide(new Side(0, 1, 1, 0));
//        WidgetValue.setViewSize(new Size(375, 720));
//
//        ArrayList<WidgetValue> children = new ArrayList<>();
//        children.add(tree(actionMap,grounpMap));
//        children.add(door(actionMap,grounpMap));
//        children.add(light(actionMap,grounpMap));
//        WidgetValue.setChildrenNode(children);
//
//        //旋转动画设置
//        RotateAnimator animator = new RotateAnimator("tree1");
//        animator.setTime(2000);
//        animator.setRepeatCount(0);
//        animator.setRepeatMode(REVERSE);
//        animator.setStartDelay(0);
//        animator.setPivot(new PointF(0, 1));
//        animator.setRotationValues(new float[]{-45, 0});
//        animator.setPropertyName("Z");
//        actionMap.put(animator.getMyName(), animator);
//
//        ArrayList<AnimatorKey> grounp = new ArrayList<>();
//        grounp.add(new AnimatorKey(animator.getMyName(), 1));
//
//        grounpMap.put("treeinit",grounp);
//
//        WidgetValue.setInitAction("treeinit");
//        return WidgetValue;
//    }
//
//    private WidgetValue light(HashMap<String, AnimatorValue> map, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        // home_tree_left_light
//        WidgetValue WidgetValue = new WidgetValue("light", "ImageView");
//        WidgetValue.setPosition(new Point(-126, -140));
//        WidgetValue.setViewSize(new Size(34, 54));
//        WidgetValue.setPic("" + R.drawable.home_tree_left_light);
//
//        RotateAnimator rotateAnimator = new RotateAnimator("lightrotate");
//        rotateAnimator.setTime(5000);
//        rotateAnimator.setPivot(new PointF(0.5f, 0));
//        rotateAnimator.setRepeatCount(-1);
//        rotateAnimator.setRepeatMode(REVERSE);
//        rotateAnimator.setRotationValues(new float[]{-15, 15});
//        map.put(rotateAnimator.getMyName(), rotateAnimator);
//
//        ArrayList<AnimatorKey> grounp = new ArrayList<>();
//        grounp.add(new AnimatorKey(rotateAnimator.getMyName(), 1));
//
//        grounpMap.put("lightinit",grounp);
//
//        WidgetValue.setInitAction("lightinit");
//        return WidgetValue;
//    }
//
//    private WidgetValue bird(HashMap<String, AnimatorValue> map, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        WidgetValue WidgetValue = new WidgetValue("bird", "ImageView");
//        WidgetValue.setSide(new Side(1, 0, 0, 1));
//        WidgetValue.setViewSize(new Size(42, 36));
//        WidgetValue.setPic("" + R.drawable.home_background_yellowbird_00);
//        //帧动画设置
//        FrameAnimator animator = new FrameAnimator("birdinit");
//        ArrayList<Integer> res = new ArrayList<>();
//        res.add(R.drawable.home_background_yellowbird_00);
//        res.add(R.drawable.home_background_yellowbird_01);
//        res.add(R.drawable.home_background_yellowbird_02);
//        res.add(R.drawable.home_background_yellowbird_03);
//        animator.setListPic(res);
//        ArrayList<Integer> time = new ArrayList<>();
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        animator.setListTime(time);
//        animator.setOneShot(false);
//        animator.setPicSize(WidgetValue.getViewSize());
//        map.put(animator.getMyName(), animator);
//
//        BesselAnimator besselAnimator = new BesselAnimator("birdpath");
//        besselAnimator.setTime(10000);
//        besselAnimator.setStartDelay(0);
//        besselAnimator.setRepeatCount(-1);
//        besselAnimator.setRepeatMode(RESTART);
//        besselAnimator.setPoints(new Point[]{new Point(500, 360), new Point(0, 0), new Point(-500, 360)});
//        map.put(besselAnimator.getMyName(), besselAnimator);
//
//        ScaleAnimator scaleAnimator = new ScaleAnimator("birdscale");
//        scaleAnimator.setTime(10000);
//        scaleAnimator.setStartDelay(0);
//        scaleAnimator.setRepeatCount(-1);
//        scaleAnimator.setRepeatMode(RESTART);
//        scaleAnimator.setScaleX(new float[]{1f, 2f, 1f});
//        scaleAnimator.setScaleY(new float[]{1f, 2f, 1f});
//        map.put(scaleAnimator.getMyName(), scaleAnimator);
//
//        ArrayList<AnimatorKey> grounp = new ArrayList<>();
//        grounp.add(new AnimatorKey(animator.getMyName(), 1));
//        grounp.add(new AnimatorKey(besselAnimator.getMyName(), 1));
//        grounp.add(new AnimatorKey(scaleAnimator.getMyName(), 1));
//
//        grounpMap.put("birdinit",grounp);
//
//        WidgetValue.setInitAction("birdinit");
//        return WidgetValue;
//    }
//
//    private WidgetValue tree(HashMap<String, AnimatorValue> map, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        WidgetValue WidgetValue = new WidgetValue("tree", "ImageView");
//        WidgetValue.setSide(new Side(0, 1, 1, 0));
//        WidgetValue.setPic("" + R.drawable.home_tree_left_tree);
//        WidgetValue.setViewSize(new Size(375, 720));
//
//        return WidgetValue;
//    }
//
//    private WidgetValue door(HashMap<String, AnimatorValue> map, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        WidgetValue WidgetValue = new WidgetValue("door", "ImageView");
//        WidgetValue.setPosition(new Point(-180, -150));
//        WidgetValue.setPic("" + R.drawable.home_tree_left_opendoor00);
//        WidgetValue.setViewSize(new Size(109, 137));
//        //帧动画设置
//        FrameAnimator animator = new FrameAnimator("dooropen");
//        ArrayList<Integer> res = new ArrayList<>();
//        res.add(R.drawable.home_tree_left_opendoor00);
//        res.add(R.drawable.home_tree_left_opendoor01);
//        res.add(R.drawable.home_tree_left_opendoor02);
//        res.add(R.drawable.home_tree_left_opendoor03);
//        res.add(R.drawable.home_tree_left_opendoor04);
//        res.add(R.drawable.home_tree_left_opendoor05);
//        res.add(R.drawable.home_tree_left_opendoor06);
//        res.add(R.drawable.home_tree_left_opendoor05);
//        res.add(R.drawable.home_tree_left_opendoor04);
//        res.add(R.drawable.home_tree_left_opendoor03);
//        res.add(R.drawable.home_tree_left_opendoor02);
//        res.add(R.drawable.home_tree_left_opendoor01);
//        animator.setListPic(res);
//        ArrayList<Integer> time = new ArrayList<>();
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        time.add(100);
//        animator.setListTime(time);
//        animator.setOneShot(false);
//        animator.setPicSize(WidgetValue.getViewSize());
//        map.put(animator.getMyName(), animator);
//
//        ArrayList<AnimatorKey> group = new ArrayList<>();
//        group.add(new AnimatorKey(animator.getMyName(), 1));
//
//        grounpMap.put("doorinit",group);
//
//        WidgetValue.setInitAction("doorinit");
//        return WidgetValue;
//    }
//
//    private WidgetValue tiegui(HashMap<String, AnimatorValue> map, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        WidgetValue WidgetValue = new WidgetValue("tiegui", "SeamlessRollingView");
//        WidgetValue.setSide(new Side(1, 1, 0, 0));
//        WidgetValue.setViewSize(new Size(-1, 238));
//        WidgetValue.setPic("" + R.drawable.home_background_guidao);
//        WidgetValue.setPicAspectRatio(2187f / 238f);
//        WidgetValue.setDirection("right");
//
//        TranslationAnimator translationAnimator = new TranslationAnimator("tieguijinlai");
//        translationAnimator.setTime(3000);
//        translationAnimator.setRepeatCount(0);
//        translationAnimator.setRepeatMode(REVERSE);
//        translationAnimator.setStartDelay(0);
//        translationAnimator.setTranslationY(new float[]{720, 482});
//        map.put(translationAnimator.getMyName(), translationAnimator);
//
//        SeamlessRollingAnimator animator = new SeamlessRollingAnimator("tieguimove");
//        animator.setStartDelay(0);
//        animator.setTime(5000);
//        map.put(animator.getMyName(), animator);
//
//        ArrayList<AnimatorKey> gourp = new ArrayList<>();
//        gourp.add(new AnimatorKey(translationAnimator.getMyName(), 1));
//        gourp.add(new AnimatorKey(animator.getMyName(), 2));
//
//        grounpMap.put("tieguinit",gourp);
//
//        WidgetValue.setInitAction("tieguinit");
//
//        //整体view点击
//        PopViewAnimator popViewAnimator = new PopViewAnimator("popTree");
//        popViewAnimator.setPic(""+R.drawable.view_hongbaoshuoming_wodehongbao);
//        popViewAnimator.setViewSize(new Size(238,66));
//        popViewAnimator.setTime(3000);
////        popViewAnimator.setFromPoint(new PointF(0,0));
//        map.put(popViewAnimator.getMyName(), popViewAnimator);
//
//        ScaleAnimator scaleAnimator = new ScaleAnimator("bianda");
//        scaleAnimator.setScaleX(new float[]{0,1});
//        scaleAnimator.setScaleY(new float[]{0,1});
//        scaleAnimator.setTime(3000);
//        map.put(scaleAnimator.getMyName(),scaleAnimator);
//
//        ArrayList<AnimatorKey> actiongroup = new ArrayList<>();
//        actiongroup.add(new AnimatorKey(popViewAnimator.getMyName(),0));
//        actiongroup.add(new AnimatorKey("backgroundsound",0));
//        grounpMap.put("groupPop",actiongroup);
//
////        HashMap<String, String> clickActions = new HashMap<>();
////        clickActions.put("tiegui","tieguichange");
////        clickActions.put("cao","tieguichange");
////        WidgetValue.setClickAction(clickActions);
//
//        //区域触发
//        ArrayList<RectAreaAction> rectActions = new ArrayList<>();
//
//        RectAreaAction rectAreaAction = new RectAreaAction(0,0,100,-100);
//
//        ArrayList<ActionValue> actionValues = new ArrayList<>();
//        ActionValue action1 = new ActionValue("rootNode","groupPop",0);
//        actionValues.add(action1);
//
//        rectAreaAction.setActionValues(actionValues);
//
//        rectActions.add(rectAreaAction);
//
//        WidgetValue.setRectAction(rectActions);
//
//        return WidgetValue;
//    }
//
//    private WidgetValue cao(HashMap<String, AnimatorValue> map, HashMap<String, ArrayList<AnimatorKey>> grounpMap) {
//        WidgetValue cao = new WidgetValue("cao", "ImageView");
//        cao.setPic("" + R.drawable.home_tree_left_grass);
//        cao.setSide(new Side(0, 1, 1, 0));
//        cao.setViewSize(new Size(469, 163));
//
//        ScaleAnimator animator = new ScaleAnimator("caoscale");
//        animator.setTime(3000);
//        animator.setPivot(new PointF(0, 1));
//        animator.setScaleX(new float[]{0f, 1});
//        animator.setScaleY(new float[]{0f, 1});
//        map.put(animator.getMyName(), animator);
//
//        AlphaAnimator alphaAnimator = new AlphaAnimator("caoalpha");
//        alphaAnimator.setTime(2000);
//        alphaAnimator.setAlpha(new float[]{1, 0.3f, 1, 0.7f});
//        map.put(alphaAnimator.getMyName(), alphaAnimator);
//
//        ArrayList<AnimatorKey> group = new ArrayList<>();
//        group.add(new AnimatorKey(animator.getMyName(), 1));
//        group.add(new AnimatorKey(alphaAnimator.getMyName(), 30));
//
//        grounpMap.put("caoactions",group);
//
//        cao.setInitAction("caoactions");
//        return cao;
//    }
//
//}