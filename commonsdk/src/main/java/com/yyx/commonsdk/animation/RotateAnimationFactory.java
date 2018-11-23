package com.yyx.commonsdk.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.List;

/**
 * Created by admin on 2018/10/17.
 */

public class RotateAnimationFactory {

    public static ObjectAnimator rotate(String propertyName, final View view
            , long time, long startDelay, int repeatCount, int repeatMode
            , final Float pivotX, final Float pivotY
            , float[] rotationValues) {

        String prName = "rotation";
        if (propertyName != null) {
            if (propertyName.equals("Z") || propertyName.equals("z")) {
                prName = "rotation";
            } else if (propertyName.equals("Y") || propertyName.equals("y")) {
                prName = "rotationY";
            } else if (propertyName.equals("X") || propertyName.equals("x")) {
                prName = "rotationX";
            }
        }
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat(prName, rotationValues);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holderX);
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