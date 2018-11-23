package com.yyx.commonsdk.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yyx.commonsdk.log.MyLog;

/**
 * Created by admin on 2018/10/17.
 */

public class ScaleAnimationFactory {

    public static ObjectAnimator scale(final View view
            , long time, long startDelay, int repeatCount, int repeatMode
            , final Float pivotX, final Float pivotY
            , float[] scaleX, float[] scaleY) {
        if (scaleX == null && scaleY == null)
            return null;
        PropertyValuesHolder holderX, holderY;
        ObjectAnimator animator;
        if (scaleX != null) {
            holderX = PropertyValuesHolder.ofFloat("scaleX", scaleX);
            if (scaleY != null) {
                holderY = PropertyValuesHolder.ofFloat("scaleY", scaleY);
                animator = ObjectAnimator.ofPropertyValuesHolder(view, holderX, holderY);
            } else {
                animator = ObjectAnimator.ofPropertyValuesHolder(view, holderX);
            }
        } else {
            holderY = PropertyValuesHolder.ofFloat("scaleY", scaleY);
            animator = ObjectAnimator.ofPropertyValuesHolder(view, holderY);
        }
        animator.setDuration(time);
        animator.setStartDelay(startDelay);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(repeatCount);
        animator.setRepeatMode(repeatMode);
        if (pivotX != null || pivotY != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (pivotX != null)
                        view.setPivotX(pivotX);
                    if (pivotY != null)
                        view.setPivotY(pivotY);
                }
            });
        }
        return animator;
    }
}