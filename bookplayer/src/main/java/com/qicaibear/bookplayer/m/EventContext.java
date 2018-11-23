package com.qicaibear.bookplayer.m;

import android.content.Context;

import com.qicaibear.bookplayer.c.SoundController;

/**
 * 事件的上下文
 *
 */

public class EventContext {
    private Context context;
    private String path;
    private SoundController soundController;

    public EventContext() {
    }

    public EventContext(Context context, String path, SoundController soundController) {
        this.context = context;
        this.path = path;
        this.soundController = soundController;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SoundController getSoundController() {
        return soundController;
    }

    public void setSoundController(SoundController soundController) {
        this.soundController = soundController;
    }
}
