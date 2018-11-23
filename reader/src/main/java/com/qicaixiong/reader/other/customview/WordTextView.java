package com.qicaixiong.reader.other.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.qicaixiong.reader.other.MessageEvent;
import com.qicaixiong.reader.m.page.event.TextClickShowAnimationEvent;
import com.qicaixiong.reader.m.page.event.TextInitEvent;
import com.qicaixiong.reader.m.page.event.TextTurnEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 单词控件
 * 1. 单击能够读单词 变色
 * 2. 读整段 按照时间段变色
 * Created by admin on 2018/8/28.
 */

public class WordTextView extends android.support.v7.widget.AppCompatTextView {
    private String soundTag="";//当前的文本对于音频的tag
    private String soundPath = "";//文本的音频
    private int initColor = 0;
    private int time;

    public WordTextView(Context context) {
        super(context);
    }

    public WordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WordTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (text != null)
            soundTag = text.toString();
        else
            soundTag = "";
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

    public void setInitColor(int initColor) {
        this.initColor = initColor;
    }

    public String getSoundTag() {
        return soundTag;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if (event instanceof TextClickShowAnimationEvent){
            TextClickShowAnimationEvent msg = (TextClickShowAnimationEvent) event;
            if (msg.getText().equals(soundTag) && msg.getTime() > 0){
                this.setTextColor(Color.parseColor("#ff0000"));
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new TextInitEvent(soundTag));
                    }
                },msg.getTime());
            }
        }
        else if(event instanceof TextInitEvent){
            TextInitEvent msg = (TextInitEvent) event;
            if (msg.getText().equals(soundTag)){
                this.setTextColor(initColor);
            }
        }
        else if (event instanceof TextTurnEvent){
            TextTurnEvent msg = (TextTurnEvent) event;
            if (msg.getShowWord().equals(soundTag)){
                this.setTextColor(Color.parseColor("#ff0000"));
            }else {
                this.setTextColor(initColor);
            }
        }
    }
}
