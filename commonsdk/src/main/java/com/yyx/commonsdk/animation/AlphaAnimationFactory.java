package com.yyx.commonsdk.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by admin on 2018/10/17.
 */

public class AlphaAnimationFactory {

    public static ObjectAnimator alpha(View view
            , long time, long startDelay, int repeatCount, int repeatMode
            , float[] alphaValues) {
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("alpha", alphaValues);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holderX);
        animator.setDuration(time);
        animator.setStartDelay(startDelay);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(repeatCount);
        animator.setRepeatMode(repeatMode);
        return animator;
    }
}
