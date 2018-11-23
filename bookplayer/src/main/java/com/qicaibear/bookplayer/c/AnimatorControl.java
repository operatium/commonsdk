package com.qicaibear.bookplayer.c;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qicaibear.bookplayer.R;
import com.qicaibear.bookplayer.m.*;
import com.qicaibear.bookplayer.m.client.*;
import com.qicaibear.bookplayer.m.client.immediately.*;
import com.qicaibear.bookplayer.m.client.order.*;
import com.qicaibear.bookplayer.v.view.SeamlessRollingView;
import com.yyx.commonsdk.animation.*;
import com.yyx.commonsdk.app.GlideApp;
import com.yyx.commonsdk.baseclass.Size;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 页面上的动画解析播放
 * Created by admin on 2018/10/24.
 */

public class AnimatorControl {

    //名字为viewName的控件 执行动作
    public static void whoRunActions(Director director, String viewName
            , String actionGroupName
            , AdditionalObject object
            , HashMap<String, View> viewMap
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> groupMap
            , LinkedList<ImageView> popViews
    ) {
        //恢复属性
        View view = viewMap.get(viewName);
        director.viewResume(view);

        EventContext context = director.getEventContext();
        ArrayList<OrderAction> gourpAction = null;
        if (groupMap != null && !groupMap.isEmpty()) {
            gourpAction = groupMap.get(actionGroupName);
        }
        if (gourpAction == null || gourpAction.isEmpty())
            return;
        ArrayList<AnimationHelp> actions = new ArrayList<>();
        int lastIdx = -1;
        for (int i = 0; i < gourpAction.size(); i++) {
            OrderAction key = gourpAction.get(i);
            String actionName = key.getActionName();
            int order = key.getOrder();
            Animator action = computeAction(context, viewName, actionName, object, viewMap, actionMap, popViews);
            if (action != null) {
                if (lastIdx != order) {
                    AnimationHelp help = new AnimationHelp();
                    help.createGroup(action);
                    actions.add(help);
                } else {
                    actions.get(actions.size() - 1).addTogotherGroup(action);
                }
                lastIdx = order;
            }
        }
        AnimatorSet set = AnimationHelp.outList(actions);

        //执行动画之前 恢复属性
        if (view != null && set != null) {
            //标记坐标
            PointF pointF = (PointF) view.getTag(R.id.viewPosition);
            if (pointF == null){
                pointF = new PointF(view.getX(),view.getY());
                view.setTag(R.id.viewPosition,pointF);
                MyLog.d("show",viewName + " point = "+pointF.toString());
            }
            view.setTag(R.id.animator,set);
            //执行动画
            set.start();
        }
    }

    //解析一个动作
    private static Animator computeAction(EventContext context, String viewName, String actionName, AdditionalObject object
            , HashMap<String, View> viewHashMap
            , HashMap<String, AnimatorValue> actionMap
            , LinkedList<ImageView> popViews) {
        View view = viewHashMap.get(viewName);
        if (view == null)
            return null;
        return computeAction(context, view, actionName, object, actionMap, popViews);
    }

    //解析一个动作
    private static Animator computeAction(EventContext eventContext, View view, String actionName, AdditionalObject object
            , HashMap<String, AnimatorValue> actionMap
            , LinkedList<ImageView> popViews) {
        Animator action = null;
        Size viewSize = (Size) view.getTag(R.id.viewSize);
        Size parentNodeSize = (Size) view.getTag(R.id.viewParentSize);
        CoordinatesConversion conversion = (CoordinatesConversion) view.getTag(R.id.viewCoordinatesConversion);
        AnimatorValue it = actionMap.get(actionName);
        if (viewSize == null || parentNodeSize == null || conversion == null || it == null)
            return null;
        switch (it.getAnimatorType()) {
            case "TranslationAnimator":
                TranslationAnimator value = (TranslationAnimator) it;
                action = AnimatorControl.createTranslationAnimator(view, value, conversion);
                break;
            case "BesselAnimator":
                BesselAnimator value5 = (BesselAnimator) it;
                action = AnimatorControl.createBesselAnimator(view, value5, conversion);
                break;
            case "SideAnimator":
                SideAnimator value1 = (SideAnimator) it;
                action = AnimatorControl.createSideAnimator(view, value1, parentNodeSize, viewSize, conversion);
                break;
            case "ScaleAnimator":
                ScaleAnimator value6 = (ScaleAnimator) it;
                action = AnimatorControl.createScaleAnimator(view, value6, viewSize);
                break;
            case "RotateAnimator":
                RotateAnimator value2 = (RotateAnimator) it;
                action = AnimatorControl.createRotateAnimator(view, value2, viewSize);
                break;
            case "AlphaAnimator":
                AlphaAnimator value7 = (AlphaAnimator) it;
                action = AnimatorControl.createAlphaAnimator(view, value7);
                break;
            case "FrameAnimator":
                FrameAnimator value3 = (FrameAnimator) it;
                if (view instanceof ImageView) {
                        AnimatorControl.createFramesAnimator(eventContext, (ImageView) view, viewSize, value3);
                }
                break;
            case "SeamlessRollingAnimator":
                SeamlessRollingAnimator value4 = (SeamlessRollingAnimator) it;
                if (view instanceof SeamlessRollingView)
                    action = ((SeamlessRollingView) view).createAnimator(value4.getTime(), value4.getStartDelay());
                break;
            case "SoundAnimator":
                SoundAnimator value8 = (SoundAnimator) it;
                AnimatorControl.createSound(eventContext, value8);
                break;
            case "PopViewAnimator":
                PopViewAnimator value9 = (PopViewAnimator) it;
                AnimatorControl.createPopViewAnimator(eventContext, view, value9, conversion, object, popViews);
                break;
        }
        return action;
    }

    //解析播音动作
    private static void createSound(EventContext context, SoundAnimator value) {
        String path = context.getPath() + "/" + value.getSoundPath();
        if (new File(path).exists()) {
            context.getSoundController().play(value.getAudioTrack(),path, value.getContent(), value.getSoundVol(), value.getRepeatCount());
        } else {
            Exception e = new Exception(context.getPath() + " createSound error (" + path + " is not exists)");
            MyLog.e("201811171151", e.toString(), e);
        }
    }

    //解析旋转动画
    private static ObjectAnimator createRotateAnimator(View view, RotateAnimator value, Size viewSize) {
        Float pivotX = null, pivotY = null;
        if (value.getPivot() != null) {
            pivotX = viewSize.getWidth() * value.getPivot().x;
            pivotY = viewSize.getHeight() * value.getPivot().y;
        }
        Float[] floats = value.getRotationValues();
        return RotateAnimationFactory.rotate(value.getPropertyName(), view, value.getTime()
                , value.getStartDelay()
                , value.getRepeatCount()
                , value.getRepeatMode()
                , pivotX
                , pivotY
                , getfloats(floats));
    }

    //解析靠边动画
    private static ObjectAnimator createSideAnimator(View view, SideAnimator value1, Size parentSize, Size viewSize, CoordinatesConversion conversion) {
        PointF point = null;
        if (value1.getPoint() != null)
            point = conversion.getLocationPoint(value1.getPoint());
        return TranslationAnimationFactory.translationKeepToSide(view, value1.getTime()
                , value1.getStartDelay()
                , value1.getRepeatCount()
                , value1.getRepeatMode()
                , parentSize
                , viewSize
                , value1.getFormSide()
                , point
                , value1.getToSide());
    }

    //解析折线动画
    private static ObjectAnimator createTranslationAnimator(View view, TranslationAnimator value, CoordinatesConversion conversion) {
        float[] translationX = null, translationY = null;
        if (value.getTranslationX() != null) {
            translationX = new float[value.getTranslationX().length];
            for (int i = 0; i < value.getTranslationX().length; i++) {
                translationX[i] = conversion.getLoactionPointX(value.getTranslationX()[i]);
            }
        }
        if (value.getTranslationY() != null) {
            translationY = new float[value.getTranslationY().length];
            for (int i = 0; i < value.getTranslationY().length; i++) {
                translationY[i] = conversion.getLoactionPointY(value.getTranslationY()[i]);
            }
        }
        return TranslationAnimationFactory.translationLines(view, value.getTime()
                , value.getStartDelay(), value.getRepeatCount(), value.getRepeatMode()
                , translationX
                , translationY);
    }

    //解析帧动画
    private static void createFramesAnimator(EventContext eventContext, final ImageView view, Size viewSize, FrameAnimator frameAnimator) {
        if (frameAnimator.getListPic() == null || frameAnimator.getListPic().isEmpty())
            return;
        ArrayList<String> images  = new ArrayList<>();
        for (String it : frameAnimator.getListPic()){
            images.add(eventContext.getPath() + "/" + it);
        }
        FramesAnimationFactory.largeFramesLoadInorder_PathFile(eventContext.getContext(), images, viewSize, frameAnimator.getListTime()
                , frameAnimator.isOneShot(), new FramesAnimationFactory.FramesAnimationReady() {
                    @Override
                    public Drawable onFramesReady(Drawable resource, int idx) {
                        return resource;
                    }

                    @Override
                    public void onResourceReady(AnimationDrawable animationDrawable) {
                        view.setImageDrawable(animationDrawable);
                        animationDrawable.start();
                    }
                });
    }

    //解析贝塞尔曲线动画
    private static ObjectAnimator createBesselAnimator(View view, BesselAnimator value, CoordinatesConversion conversion) {
        if (value.getPoints() == null)
            return null;
        PointF[] points = new PointF[value.getPoints().length];
        for (int i = 0; i < value.getPoints().length; i++) {
            points[i] = conversion.getLocationPoint(value.getPoints()[i]);
        }
        return TranslationAnimationFactory.translationBessel(view, value.getTime(), value.getStartDelay()
                , value.getRepeatCount(), value.getRepeatMode(), points);
    }

    //解析缩放动画
    private static ObjectAnimator createScaleAnimator(View view, ScaleAnimator value, Size viewSize) {
        Float pivotX = null, pivotY = null;
        if (value.getPivot() != null) {
            pivotX = viewSize.getWidth() * value.getPivot().x;
            pivotY = viewSize.getHeight() * value.getPivot().y;
        }
        return ScaleAnimationFactory.scale(view, value.getTime()
                , value.getStartDelay()
                , value.getRepeatCount()
                , value.getRepeatMode()
                , pivotX
                , pivotY
                , getfloats(value.getScaleX())
                , getfloats(value.getScaleY()));
    }

    //解析透明度
    private static ObjectAnimator createAlphaAnimator(View view, AlphaAnimator value) {
        return AlphaAnimationFactory.alpha(view, value.getTime()
                , value.getStartDelay()
                , value.getRepeatCount()
                , value.getRepeatMode()
                , getfloats(value.getAlpha()));
    }

    //解析 POP动作
    private static void createPopViewAnimator(EventContext eventContext, View parentView
            , PopViewAnimator value, CoordinatesConversion conversion
            , AdditionalObject object
            , LinkedList<ImageView> popViews) {
        if (parentView != null && value != null) {
            ImageView view;
            if (popViews.size() <= 6) {
                view = new ImageView(eventContext.getContext());
                popViews.addLast(view);
            } else {
                view = popViews.pop();
                view.setImageDrawable(null);
                view.clearAnimation();
                Animator animator = (Animator) view.getTag(R.id.animator);
                if (animator != null)
                    animator.cancel();
                if (parentView instanceof ViewGroup && view.getParent() == parentView) {
                    ((ViewGroup) parentView).removeView(view);
                }
                popViews.addLast(view);
            }
            MyLog.d("show", "createPopViewAnimator parentView = " + parentView.getTag(R.id.viewName));
            if (parentView instanceof ViewGroup && view.getParent() == null)
                ((ViewGroup) parentView).addView(view);

            Size selfSize = conversion.getLocationSize(value.getViewSize());
            new LayoutParamsHelp<ConstraintLayout>(view)
                    .creatConstraintLayoutLayoutParams(selfSize)
                    .apply();
            String path = eventContext.getPath() + "/" + value.getPic();
            if (new File(path).exists())
                GlideApp.with(view).load(path).into(view);
            else
                return;

            PointF from = value.getFromPoint();
            PointF to = value.getToPoint();
            if (from == null) {
                if (object != null) {
                    PointF center = object.getPointF();
                    from = new PointF(center.x - selfSize.getWidth() / 2f, center.y - selfSize.getHeight() / 2f);
                }
            }
            if (to == null) {
                if (object != null) {
                    PointF center = object.getPointF();
                    to = new PointF(center.x - selfSize.getWidth() / 2f, center.y - selfSize.getHeight() / 2f);
                }
            }
            if (from == null)
                from = new PointF(0, 0);
            if (to == null)
                to = new PointF(0, 0);
            ObjectAnimator action1 = TranslationAnimationFactory.translationLine(view, value.getTime(), 0, 0, 0
                    , from.x, to.x, from.y, to.y);
            ObjectAnimator action2 = ScaleAnimationFactory.scale(view, value.getTime(), 0, 0, 0
                    , null, null, new float[]{0, 1}, new float[]{0, 1});
            ObjectAnimator action3 = AlphaAnimationFactory.alpha(view, 500, 0, 0, 0
                    , new float[]{0, 1});
            ObjectAnimator action4 = AlphaAnimationFactory.alpha(view, 500, 1000, 0, 0
                    , new float[]{1, 0});
            AnimatorSet set = new AnimatorSet();
            set.play(action1).with(action2).with(action3).before(action4);
            set.start();

            view.setTag(R.id.animator, set);
        }
    }

    private static float[] getfloats(Float[] floats){
        float[] result = new float[floats.length];
        for (int i= 0; i < floats.length ;i++){
            result[i]=floats[i];
        }
        return result;
    }
}