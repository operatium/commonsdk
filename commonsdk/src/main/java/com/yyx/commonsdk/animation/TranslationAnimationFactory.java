package com.yyx.commonsdk.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.ImageFormat;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yyx.commonsdk.baseclass.Side;
import com.yyx.commonsdk.baseclass.Size;
import com.yyx.commonsdk.log.MyLog;

/**
 * 平移动画的封装类
 * Created by admin on 2018/10/16.
 */

public class TranslationAnimationFactory {

    //直线移动

    /**
     * @param view             控件
     * @param fromTranslationX x的起点
     * @param toTranslationX   x的终点
     * @param fromTranslationY y的起点
     * @param toTranslationY   y的终点
     * @param time             持续时间
     * @param startDelay       延迟启动的时间
     * @param repeatCount      重复次数
     * @param repeatMode       循环模式
     */
    public static ObjectAnimator translationLine(View view
            , long time, long startDelay, int repeatCount, int repeatMode
            , float fromTranslationX, float toTranslationX, float fromTranslationY, float toTranslationY) {
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("x", fromTranslationX, toTranslationX);
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("y", fromTranslationY, toTranslationY);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holderX, holderY);
        animator.setDuration(time);
        animator.setStartDelay(startDelay);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(repeatCount);
        animator.setRepeatMode(repeatMode);
        return animator;
    }


    //折线移动
    public static ObjectAnimator translationLines(View view
            , long time, long startDelay, int repeatCount, int repeatMode
            , float[] translationX, float[] translationY) {
        ObjectAnimator animator;
        PropertyValuesHolder holderX, holderY;
        if (translationX != null) {
            holderX = PropertyValuesHolder.ofFloat("x", translationX);
            if (translationY != null) {
                holderY = PropertyValuesHolder.ofFloat("y", translationY);
                animator = ObjectAnimator.ofPropertyValuesHolder(view, holderX, holderY);
            } else {
                animator = ObjectAnimator.ofPropertyValuesHolder(view, holderX);
            }
        } else {
            if (translationY != null) {
                holderY = PropertyValuesHolder.ofFloat("y", translationY);
                animator = ObjectAnimator.ofPropertyValuesHolder(view, holderY);
            } else {
                return null;
            }
        }
        animator.setDuration(time);
        animator.setStartDelay(startDelay);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(repeatCount);
        animator.setRepeatMode(repeatMode);
        return animator;
    }

    //贝塞尔曲线移动
    public static ObjectAnimator translationBessel(View view, long time, long startDelay, int repeatCount, int repeatMode
            , PointF... points) {
        if (Build.VERSION.SDK_INT >= 21) {
            Path path = new Path();
            path.moveTo(points[0].x, points[0].y);
            path.cubicTo(points[0].x, points[0].y, points[1].x, points[1].y, points[2].x, points[2].y);
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "x", "y", path);
            animator.setDuration(time);
            animator.setStartDelay(startDelay);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(repeatCount);
            animator.setRepeatMode(repeatMode);
            return animator;
        }
        return null;
    }

    //靠边移动
    public static ObjectAnimator translationKeepToSide(View view
            , long time, long startDelay, int repeatCount, int repeatMode
            , Size parentViewSize, Size viewSize, Side fromSide, PointF point, Side toSide) {
        /**
         * point的作用是替换失效的fromSide或者toSide
         */
        if (point == null)
            return null;
        if (fromSide == null || !fromSide.haveZero())
            fromSide = null;
        if (toSide == null || !toSide.haveZero())
            toSide = null;

        float translationX = parentViewSize.getWidth() - viewSize.getWidth();
        float translationY = parentViewSize.getHeight() - viewSize.getHeight();

        if (fromSide != null && toSide != null){
            //from -> to
            return fromSideToToSide(view,fromSide,point,toSide,translationX,translationY,time,startDelay,repeatCount,repeatMode);
        }else if (fromSide == null && toSide != null){
            //point -> to
            return pointToToSide(view,point,toSide,translationX,translationY,time,startDelay,repeatCount,repeatMode);
        }else if (fromSide != null){
            //from -> point
            return fromSideToPoint(view,fromSide,point,translationX,translationY,time,startDelay,repeatCount,repeatMode);
        }
        return null;
    }

    private static ObjectAnimator fromSideToToSide(View view,Side fromSide,PointF point, Side toSide, float translationX, float translationY
            , long time, long startDelay, int repeatCount, int repeatMode){
        float formX = point.x;
        float toX = point.x;
        float formY = point.y;
        float toY = point.y;

        if (fromSide.getLeft() == 0) {
            formX = 0;
        }else if (fromSide.getRight() == 0){
            formX = translationX;
        }
        if (toSide.getLeft() == 0) {
            toX = 0;
        }else if (toSide.getRight() == 0) {
            toX = translationX;
        }

        if (fromSide.getTop() == 0) {
            formY = 0;
        }else if (fromSide.getBottom() == 0){
            formY = translationY;
        }
        if (toSide.getTop() == 0) {
            toY = 0;
        }else if (toSide.getBottom() == 0) {
            toY = translationY;
        }

        return translationLine(view, time, startDelay, repeatCount, repeatMode, formX, toX, formY, toY);
    }

    private static ObjectAnimator pointToToSide(View view,PointF point, Side toSide, float translationX, float translationY
            , long time, long startDelay, int repeatCount, int repeatMode){
        float formX = point.x;
        float toX = point.x;
        float formY = point.y;
        float toY = point.y;

        if (toSide.getLeft() == 0) {
            toX = 0;
        }else if (toSide.getRight() == 0) {
            toX = translationX;
        }

        if (toSide.getTop() == 0) {
            toY = 0;
        }else if (toSide.getBottom() == 0) {
            toY = translationY;
        }

        return translationLine(view, time, startDelay, repeatCount, repeatMode, formX, toX, formY, toY);
    }

    private static ObjectAnimator fromSideToPoint(View view,Side fromSide,PointF point, float translationX, float translationY
            , long time, long startDelay, int repeatCount, int repeatMode){
        float formX = point.x;
        float toX = point.x;
        float formY = point.y;
        float toY = point.y;

        if (fromSide.getLeft() == 0) {
            formX = 0;
        }else if (fromSide.getRight() == 0){
            formX = translationX;
        }

        if (fromSide.getTop() == 0) {
            formY = 0;
        }else if (fromSide.getBottom() == 0){
            formY = translationY;
        }

        return translationLine(view, time, startDelay, repeatCount, repeatMode, formX, toX, formY, toY);
    }
}