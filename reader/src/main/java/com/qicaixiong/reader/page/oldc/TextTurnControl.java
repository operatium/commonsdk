package com.qicaixiong.reader.page.oldc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

import com.qicaixiong.reader.m.book.event.NextPageEvent;
import com.qicaixiong.reader.m.page.event.TextTurnEvent;
import com.qicaixiong.reader.permission.PermissionManger;
import com.qicaixiong.reader.m.show.AnTextModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 字幕滚动
 * 播放一段语音 对应的文字常量
 * Created by admin on 2018/8/30.
 */

public class TextTurnControl {

    /**
     *
     * @param words 每段文字播完的时间点
     * @param alltime
     */
    public static ValueAnimator start(final PermissionManger permissionManger,final ArrayList<AnTextModel> words, long alltime){
        int c = words.size();
        if (c == 0)
            return null;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,Long.valueOf(alltime).intValue());
        valueAnimator.setDuration(alltime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int t = (int) animation.getAnimatedValue();
                for (int i = 0 ; i < words.size(); i++){
                    int startT = words.get(i).getStartTime();
                    int endt = words.get(i).getEndTime();
                    if (t <= endt && t >= startT){
                        EventBus.getDefault().post(new TextTurnEvent(words.get(i).getWord()));
                        return;
                    }
                }
                EventBus.getDefault().post(new TextTurnEvent(""));
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                EventBus.getDefault().post(new TextTurnEvent(""));
                if (permissionManger.isAutoPlay())
                    EventBus.getDefault().post(new NextPageEvent());//自动阅读 播音结束 通知翻页
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                EventBus.getDefault().post(new TextTurnEvent(""));
            }
        });
        valueAnimator.start();
        return valueAnimator;
    }
}
