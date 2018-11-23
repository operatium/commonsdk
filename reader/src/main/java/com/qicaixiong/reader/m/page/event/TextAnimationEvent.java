package com.qicaixiong.reader.m.page.event;

import com.qicaixiong.reader.other.MessageEvent;

/**
 * Created by admin on 2018/8/28.
 */

public class TextAnimationEvent extends MessageEvent{
    private Long tag;
    private String animation;
    private float value;

    public TextAnimationEvent(Long tag, String animation, float value) {
        this.tag = tag;
        this.animation = animation;
        this.value = value;
    }

    public Long getTag() {
        return tag;
    }

    public String getAnimation() {
        return animation;
    }

    public float getValue() {
        return value;
    }
}
