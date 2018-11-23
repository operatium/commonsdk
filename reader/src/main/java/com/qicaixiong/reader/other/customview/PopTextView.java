package com.qicaixiong.reader.other.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

import com.qicaixiong.reader.other.MessageEvent;
import com.qicaixiong.reader.m.page.event.TextAnimationEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * TextView
 * 接收消失命令
 * Created by admin on 2018/8/28.
 */

public class PopTextView extends android.support.v7.widget.AppCompatTextView {

    public PopTextView(Context context) {
        super(context);
    }

    public PopTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public void startPop(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1
        ,Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        scaleAnimation.setDuration(1000);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setStartOffset(3000);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setDuration(1000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.startNow();
        this.setAnimation(set);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if (event instanceof TextAnimationEvent){
            TextAnimationEvent msg = (TextAnimationEvent) event;
            Long t = (Long) getTag();
            if (msg.getTag().equals(t)) {
                if (msg.getAnimation().equals("scale")) {
                    PopTextView.this.setScaleX(msg.getValue());
                    PopTextView.this.setScaleY(msg.getValue());
                } else if (msg.getAnimation().equals("alpha")) {
                    PopTextView.this.setAlpha(msg.getValue());
                } else if (msg.getAnimation().equals("hide")) {
                    PopTextView.this.setVisibility(GONE);
                    ViewParent view = PopTextView.this.getParent();
                    if (view != null && view instanceof ViewGroup){
                        ((ViewGroup)view).removeView(PopTextView.this);
                    }
                }
            }
        }
    }
}
