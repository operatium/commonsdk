package com.qicaixiong.reader.bookmodel.animator.immediately;

import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;

/**
 * 单图无缝滚动 播放 针对SeamlessRollingView 控件
 * Created by admin on 2018/10/25.
 */

public class SeamlessRollingAnimator extends AnimatorValue {
    private int time;
    private int startDelay;

    public SeamlessRollingAnimator(String myName) {
        super(myName,"SeamlessRollingAnimator");
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(int startDelay) {
        this.startDelay = startDelay;
    }
}