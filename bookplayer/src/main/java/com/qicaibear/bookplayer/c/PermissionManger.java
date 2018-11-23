package com.qicaibear.bookplayer.c;

/**
 * Created by Administrator on 2018/9/26.
 */

public class PermissionManger {
    //阅读模式
    public static final int DEFAULT = 0;//默认模式  点读
    public static final int AUTO = 1;//自动播放
    public static final int AUTOLOOPMUTE = 2;//自动循环静音播放
    public static final int RECORD = 3;//录制
    public static final int PLAYRECORD = 4;//自动播放录制


    //细分权限
    private int myMode = DEFAULT;
    private boolean rectClickable;//点击区域
    private boolean loopAutoPlay;//自动播放模式下 视图进行自动循环翻页 最后一页跳回第一页
    private boolean rePlayButtonable;//支持播放整段的按钮
    private boolean currentPageAutoPlay;//翻到当前页自动播放当前页整段语音
    private boolean autoPlay;//支持自动翻页并且下一页自动播放
    private boolean mutePlayable;//静音
    private boolean textClickable;//点击文本
    private boolean allTextPlayContinuity;//整段文本播放声音时候的连续性 true = 正在播放整段的时候 单词读音不可以打断整段播放

    //权限对应需要的状态
    private boolean allTextPlaying;//整段文本播放声音 true = 正在播放

    public PermissionManger() {
        this(DEFAULT);
    }

    public PermissionManger(int mode) {
        myMode = mode;
        switch (myMode) {
            case DEFAULT:
                rectClickable = true;
                loopAutoPlay = false;
                rePlayButtonable = true;
                mutePlayable = false;
                textClickable = true;
                autoPlay = false;
                currentPageAutoPlay = true;
                allTextPlayContinuity = true;
                break;
            case AUTO:
                rectClickable = false;
                loopAutoPlay = false;
                rePlayButtonable = false;
                mutePlayable = false;
                textClickable = false;
                autoPlay = true;
                currentPageAutoPlay = true;
                allTextPlayContinuity = true;
                break;
            case AUTOLOOPMUTE:
                rectClickable = false;
                loopAutoPlay = true;
                rePlayButtonable = false;
                mutePlayable = true;
                textClickable = false;
                autoPlay = true;
                currentPageAutoPlay = true;
                allTextPlayContinuity = true;
                break;
            case RECORD:
                rectClickable = true;
                loopAutoPlay = false;
                rePlayButtonable = false;
                mutePlayable = false;
                textClickable = true;
                autoPlay = false;
                currentPageAutoPlay = true;
                allTextPlayContinuity = true;
                break;
            case PLAYRECORD:
                rectClickable = true;
                loopAutoPlay = false;
                rePlayButtonable = false;
                mutePlayable = false;
                textClickable = true;
                autoPlay = false;
                currentPageAutoPlay = false;
                allTextPlayContinuity = true;
                break;
        }
    }

    public boolean isRectClickable() {
        return rectClickable;
    }

    public boolean isLoopAutoPlay() {
        return loopAutoPlay;
    }

    public boolean isRePlayButtonable() {
        return rePlayButtonable;
    }

    public boolean isMutePlayable() {
        return mutePlayable;
    }

    public boolean isTextClickable() {
        return textClickable;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public int getMyMode() {
        return myMode;
    }

    public boolean isCurrentPageAutoPlay() {
        return currentPageAutoPlay;
    }

    public void setCurrentPageAutoPlay(boolean currentPageAutoPlay) {
        this.currentPageAutoPlay = currentPageAutoPlay;
    }

    public boolean isAllTextPlayContinuity() {
        return allTextPlayContinuity;
    }

    public boolean isAllTextPlaying() {
        return allTextPlaying;
    }

    public void setAllTextPlaying(boolean allTextPlaying) {
        this.allTextPlaying = allTextPlaying;
    }
}
