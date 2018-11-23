package com.qicaixiong.reader.page.c;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;

import com.qicaixiong.reader.R;
import com.qicaixiong.reader.bookmodel.animator.OrderAction;
import com.qicaixiong.reader.m.page.data.AdditionalObject;
import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;
import com.qicaixiong.reader.bookmodel.animator.immediately.PopViewAnimator;
import com.qicaixiong.reader.bookmodel.animator.immediately.SeamlessRollingAnimator;
import com.qicaixiong.reader.bookmodel.animator.immediately.SoundAnimator;
import com.qicaixiong.reader.bookmodel.animator.order.AlphaAnimator;
import com.qicaixiong.reader.bookmodel.animator.order.BesselAnimator;
import com.qicaixiong.reader.bookmodel.animator.immediately.FrameAnimator;
import com.qicaixiong.reader.bookmodel.animator.order.RotateAnimator;
import com.qicaixiong.reader.bookmodel.animator.order.ScaleAnimator;
import com.qicaixiong.reader.bookmodel.animator.order.SideAnimator;
import com.qicaixiong.reader.bookmodel.animator.order.TranslationAnimator;
import com.qicaixiong.reader.other.customview.SeamlessRollingView;
import com.qicaixiong.reader.resource.c.CoordinatesConversion;
import com.yyx.commonsdk.animation.AlphaAnimationFactory;
import com.yyx.commonsdk.animation.AnimationHelp;
import com.yyx.commonsdk.animation.FramesAnimationFactory;
import com.yyx.commonsdk.animation.RotateAnimationFactory;
import com.yyx.commonsdk.animation.ScaleAnimationFactory;
import com.yyx.commonsdk.animation.TranslationAnimationFactory;
import com.yyx.commonsdk.app.GlideApp;
import com.yyx.commonsdk.baseclass.Size;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;
import com.yyx.commonsdk.sound.SoundPlayManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 页面上的动画解析播放
 * Created by admin on 2018/10/24.
 */

public class AnimatorControl {

    //view执行动作
    public static void viewRunAction(View view, String actionGroupName, AdditionalObject object
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> groupMap
            , LinkedList<ImageView> popViews) {
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
            Animator action = computeAction(view, actionName, object, actionMap, popViews);
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
        AnimationHelp.outList(actions).start();
    }

    //名字为viewName的控件 执行动作
    public static void whoRunActions(String viewName, String actionGroupName, AdditionalObject object
            , HashMap<String, View> viewMap
            , HashMap<String, AnimatorValue> actionMap
            , HashMap<String, ArrayList<OrderAction>> groupMap
            , LinkedList<ImageView> popViews
    ) {
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
            Animator action = computeAction(viewName, actionName, object, viewMap, actionMap, popViews);
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
        AnimationHelp.outList(actions).start();
    }

    //解析一个动作
    private static Animator computeAction(String viewName, String actionName, AdditionalObject object
            , HashMap<String, View> viewHashMap
            , HashMap<String, AnimatorValue> actionMap
            , LinkedList<ImageView> popViews) {
        View view = viewHashMap.get(viewName);
        if (view == null)
            return null;
        return computeAction(view, actionName, object, actionMap, popViews);
    }

    //解析一个动作
    private static Animator computeAction(View view, String actionName, AdditionalObject object
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
                if (view instanceof ImageView)
                    AnimatorControl.createFramesAnimator(view.getContext(), (ImageView) view, viewSize, value3);
                break;
            case "SeamlessRollingAnimator":
                SeamlessRollingAnimator value4 = (SeamlessRollingAnimator) it;
                if (view instanceof SeamlessRollingView)
                    action = ((SeamlessRollingView) view).createAnimator(value4.getTime(), value4.getStartDelay());
                break;
            case "SoundAnimator":
                SoundAnimator value8 = (SoundAnimator) it;
                AnimatorControl.createSound(value8);
                break;
            case "PopViewAnimator":
                PopViewAnimator value9 = (PopViewAnimator) it;
                AnimatorControl.createPopViewAnimator(view.getContext(), view, value9, conversion, object, popViews);
                break;
        }
        return action;
    }

    //解析播音动作
    private static void createSound(SoundAnimator value) {
        SoundPlayManager.getInstance().play(value.getSoundPath(), value.getContent(), value.getSoundVol(), value.getRepeatCount());
    }

    //解析旋转动画
    private static ObjectAnimator createRotateAnimator(View view, RotateAnimator value, Size viewSize) {
        Float pivotX = null, pivotY = null;
        if (value.getPivot() != null) {
            pivotX = viewSize.getWidth() * value.getPivot().x;
            pivotY = viewSize.getHeight() * value.getPivot().y;
        }
        return RotateAnimationFactory.rotate(value.getPropertyName(), view, value.getTime()
                , value.getStartDelay()
                , value.getRepeatCount()
                , value.getRepeatMode()
                , pivotX
                , pivotY
                , value.getRotationValues());
    }

    //解析靠边动画
    private static ObjectAnimator createSideAnimator(View view, SideAnimator value1, Size parentSize, Size viewSize, CoordinatesConversion conversion) {
        PointF formPoint = conversion.getLocationPointF(value1.getFormPoint());
        return TranslationAnimationFactory.translationKeepToSide(view, value1.getTime()
                , value1.getStartDelay()
                , value1.getRepeatCount()
                , value1.getRepeatMode()
                , parentSize
                , viewSize
                , value1.getFormSide()
                , formPoint
                , value1.getToSide());
    }

    //解析折线动画
    private static ObjectAnimator createTranslationAnimator(View view, TranslationAnimator value, CoordinatesConversion conversion) {
        float[] translationX = null, translationY = null;
        if (value.getTranslationX() != null) {
            translationX = conversion.getSceenFitFactory().getSizeFolatArray(value.getTranslationX());
        }
        if (value.getTranslationY() != null) {
            translationY = conversion.getSceenFitFactory().getSizeFolatArray(value.getTranslationY());
        }
        return TranslationAnimationFactory.translationLines(view, value.getTime()
                , value.getStartDelay(), value.getRepeatCount(), value.getRepeatMode()
                , translationX
                , translationY);
    }

    //解析帧动画
    private static void createFramesAnimator(Context context, final ImageView view, Size viewSize, FrameAnimator frameAnimator) {
        FramesAnimationFactory.largeFramesLoadInorder_LocationResouce(context, frameAnimator.getListPic(), viewSize, frameAnimator.getListTime()
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
            points[i] = conversion.getLocationPointF(value.getPoints()[i]);
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
                , value.getScaleX()
                , value.getScaleY());
    }

    //解析透明度
    private static ObjectAnimator createAlphaAnimator(View view, AlphaAnimator value) {
        return AlphaAnimationFactory.alpha(view, value.getTime()
                , value.getStartDelay()
                , value.getRepeatCount()
                , value.getRepeatMode()
                , value.getAlpha());
    }

    //解析 POP动作
    private static void createPopViewAnimator(Context context, View parentView
            , PopViewAnimator value, CoordinatesConversion conversion
            , AdditionalObject object
            , LinkedList<ImageView> popViews) {
        if (parentView != null && value != null) {
            ImageView view;
            if (popViews.size() <= 6) {
                view = new ImageView(context);
                popViews.addLast(view);
            } else {
                view = popViews.pop();
                view.setImageDrawable(null);
                view.clearAnimation();
                Animator animator = (Animator) view.getTag(R.id.animator);
                if (animator != null)
                    animator.cancel();
                if (parentView instanceof ConstraintLayout && view.getParent() == parentView) {
                    ((ConstraintLayout) parentView).removeView(view);
                }
                popViews.addLast(view);
            }

            Size selfSize = conversion.getLocationSize(value.getViewSize());
            new LayoutParamsHelp<ConstraintLayout>(view)
                    .creatConstraintLayoutLayoutParams(selfSize)
                    .apply();
            GlideApp.with(context).load(Integer.valueOf(value.getPic())).into(view);

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

            view.setTag(R.id.animator,set);

            if (parentView instanceof ConstraintLayout && view.getParent() == null) {
                ((ConstraintLayout) parentView).addView(view);
            }
        }
    }
}