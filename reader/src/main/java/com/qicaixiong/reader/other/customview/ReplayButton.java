package com.qicaixiong.reader.other.customview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.qicaixiong.reader.other.MessageEvent;
import com.qicaixiong.reader.m.page.event.ReplayEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 重读按钮
 * Created by admin on 2018/8/29.
 */

public class ReplayButton extends android.support.v7.widget.AppCompatImageView {
    private String soundTag;

    public ReplayButton(Context context) {
        super(context);
    }

    public ReplayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReplayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public String getSoundTag() {
        return soundTag;
    }

    public void setSoundTag(String soundTag) {
        this.soundTag = soundTag;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if (event instanceof ReplayEvent){
            ReplayEvent msg = (ReplayEvent) event;
            if (!TextUtils.isEmpty(soundTag) && soundTag.equals(msg.getSoundTag())){
                if (msg.isPlaying()) {
                    if (msg.getTime() > 0) {
                        ReplayButton.this.setVisibility(INVISIBLE);
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new ReplayEvent(soundTag, false, 0));
                            }
                        }, msg.getTime());
                    }
                }
                else
                    ReplayButton.this.setVisibility(VISIBLE);
            }
        }
    }
}
