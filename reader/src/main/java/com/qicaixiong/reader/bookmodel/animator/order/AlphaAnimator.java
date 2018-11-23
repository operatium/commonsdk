package com.qicaixiong.reader.bookmodel.animator.order;


import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;

/**
 * Created by admin on 2018/10/25.
 */

public class AlphaAnimator extends AnimatorValue {
    private long time;
    private long startDelay;
    private int repeatCount;
    private int repeatMode;
    private float[] alpha;

    public AlphaAnimator(String myName) {
        super(myName,"AlphaAnimator");
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

    public float[] getAlpha() {
        return alpha;
    }

    public void setAlpha(float[] alpha) {
        this.alpha = alpha;
    }
}
