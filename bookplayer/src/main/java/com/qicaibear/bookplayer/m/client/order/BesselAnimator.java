package com.qicaibear.bookplayer.m.client.order;

import android.graphics.PointF;

import com.qicaibear.bookplayer.m.client.AnimatorValue;

/**long time, long startDelay, int repeatCount, int repeatMode
 , Point... points
 * Created by admin on 2018/10/25.
 */

public class BesselAnimator extends AnimatorValue {
    private long time;
    private long startDelay;
    private int repeatCount;
    private int repeatMode;
    private PointF[] points;

    public BesselAnimator(String myName) {
        super(myName,"BesselAnimator");
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

    public PointF[] getPoints() {
        return points;
    }

    public void setPoints(PointF[] points) {
        this.points = points;
    }
}
