package com.qicaixiong.reader.other.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.qicaixiong.reader.R;
import com.yyx.commonsdk.app.GlideApp;
import com.yyx.commonsdk.baseclass.Size;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;

/**
 * 一张图片进行无缝循环滚动
 * Created by admin on 2018/10/23.
 */

public class SeamlessRollingView extends ConstraintLayout {
    private int translationX, translationY;
    private String direction;
    private AnimatorSet animator;
    private ObjectAnimator animator1;
    private ConstraintLayout layout;

    public SeamlessRollingView(Context context) {
        super(context);
    }

    public SeamlessRollingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeamlessRollingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView(Context context, float whbi, Size viewSize, String direction, String path) {
        this.direction = direction;
        int width = viewSize.getWidth();
        int height = viewSize.getHeight();

        layout = new ConstraintLayout(context);
        addView(layout);

        ImageView left = new ImageView(context);
        left.setScaleType(ImageView.ScaleType.FIT_START);
        left.setId(R.id.viewid1);

        ImageView right = new ImageView(context);
        right.setId(R.id.viewid2);
        right.setScaleType(ImageView.ScaleType.FIT_START);

        ConstraintLayout.LayoutParams paramsleft, paramsright;
        switch (direction) {
            case "left":
                translationX = Float.valueOf(height * whbi).intValue();
                paramsleft = new ConstraintLayout.LayoutParams(translationX, height);
                paramsright = new ConstraintLayout.LayoutParams(translationX, height);
                paramsleft.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
                paramsright.leftToRight = R.id.viewid1;
                new LayoutParamsHelp<ConstraintLayout>(layout).creatConstraintLayoutLayoutParams(translationX*2,height)
                        .applyMargin_ConstraintLayout(true,false,false,false)
                        .apply();
                break;
            case "right":
                translationX = Float.valueOf(height * whbi).intValue();
                paramsleft = new ConstraintLayout.LayoutParams(translationX, height);
                paramsright = new ConstraintLayout.LayoutParams(translationX, height);
                paramsright.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
                paramsleft.rightToLeft = R.id.viewid2;
                new LayoutParamsHelp<ConstraintLayout>(layout).creatConstraintLayoutLayoutParams(translationX*2,height)
                        .applyMargin_ConstraintLayout(false,false,true,false)
                        .apply();
                break;
            case "top":
                translationY = Float.valueOf(width / whbi).intValue();
                paramsleft = new ConstraintLayout.LayoutParams(width, translationY);
                paramsright = new ConstraintLayout.LayoutParams(width, translationY);
                paramsleft.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                paramsright.topToBottom = R.id.viewid1;
                new LayoutParamsHelp<ConstraintLayout>(layout).creatConstraintLayoutLayoutParams(width, translationY*2)
                        .applyMargin_ConstraintLayout(false,true,false,false)
                        .apply();
                break;
            case "bottom":
                translationY = Float.valueOf(width / whbi).intValue();
                paramsleft = new ConstraintLayout.LayoutParams(width, translationY);
                paramsright = new ConstraintLayout.LayoutParams(width, translationY);
                paramsright.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
                paramsleft.bottomToTop = R.id.viewid2;
                new LayoutParamsHelp<ConstraintLayout>(layout).creatConstraintLayoutLayoutParams(width, translationY*2)
                        .applyMargin_ConstraintLayout(false,false,false,true)
                        .apply();
                break;
            default:
                translationX = Float.valueOf(height * whbi).intValue();
                paramsleft = new ConstraintLayout.LayoutParams(translationX, height);
                paramsright = new ConstraintLayout.LayoutParams(translationX, height);
                paramsleft.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
                paramsright.leftToRight = R.id.viewid1;
                new LayoutParamsHelp<ConstraintLayout>(layout).creatConstraintLayoutLayoutParams(translationX*2,height)
                        .applyMargin_ConstraintLayout(true,false,false,false)
                        .apply();
                break;
        }
        left.setLayoutParams(paramsleft);
        right.setLayoutParams(paramsright);
        GlideApp.with(left).load(Integer.valueOf(path)).into(left);
        GlideApp.with(right).load(Integer.valueOf(path)).into(right);
        layout.addView(left);
        layout.addView(right);
    }

    public Animator createAnimator(long time, long startDelay) {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        PropertyValuesHolder holder;
        switch (direction) {
            case "left":
                holder = PropertyValuesHolder.ofFloat("translationX", 0, -translationX);
                break;
            case "right":
                holder = PropertyValuesHolder.ofFloat("translationX", 0, translationX);
                break;
            case "top":
                holder = PropertyValuesHolder.ofFloat("translationY", 0, -translationY);
                break;
            case "bottom":
                holder = PropertyValuesHolder.ofFloat("translationY", 0, translationY);
                break;
            default:
                holder = PropertyValuesHolder.ofFloat("translationX", 0, -translationX);
                break;
        }
        animator = new AnimatorSet();
        animator1 = ObjectAnimator.ofPropertyValuesHolder(layout, holder);
        animator1.setDuration(time);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setStartDelay(startDelay);
        animator1.setRepeatMode(ValueAnimator.RESTART);
        animator1.setRepeatCount(-1);
        animator.playTogether(animator1);
        return animator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}