package com.qicaibear.bookplayer.m.client;

/**
 * 动画对象

 * Created by admin on 2018/10/19.
 */

public class AnimatorValue {

    private String myName;//动作名称
    private String animatorType;//动画类型 AlphaAnimator,BesselAnimator,RotateAnimator,ScaleAnimator,SideAnimator,TranslationAnimator,SoundAnimator,SeamlessRollingAnimator,FrameAnimator
    private int bookId;
    private int pageId;

    public AnimatorValue(String myName, String animatorType) {
        this.myName = myName;
        this.animatorType = animatorType;
    }

    public AnimatorValue(String myName, int order) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getAnimatorType() {
        return animatorType;
    }

    public void setAnimatorType(String animatorType) {
        this.animatorType = animatorType;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
}
