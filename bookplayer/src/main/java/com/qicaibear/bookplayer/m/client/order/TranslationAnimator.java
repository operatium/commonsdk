package com.qicaibear.bookplayer.m.client.order;


import com.qicaibear.bookplayer.m.client.AnimatorValue;

/**
 * 折线平移
 *  * 1.long time //动画的持续时间
 2.long startDelay //动画的启动前的延迟时间
 3.int repeatCount //重复次数 -1 = 无限循环； 0 = 执行1次
 4.int repeatMode //重复模式 RESTART(1) = 每次从头循环 REVERSE(2) = 逆向循环
 * Created by admin on 2018/10/19.
 */

public class TranslationAnimator extends AnimatorValue {
    private long time;
    private long startDelay;
    private int repeatCount;
    private int repeatMode;
    private Float[] translationX;
    private Float[] translationY;

    public TranslationAnimator(String myName) {
        super(myName, "TranslationAnimator");
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }

    public Float[] getTranslationX() {
        return translationX;
    }

    public void setTranslationX(Float[] translationX) {
        this.translationX = translationX;
    }

    public Float[] getTranslationY() {
        return translationY;
    }

    public void setTranslationY(Float[] translationY) {
        this.translationY = translationY;
    }
}