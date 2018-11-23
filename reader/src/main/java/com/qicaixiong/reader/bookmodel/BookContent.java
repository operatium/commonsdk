package com.qicaixiong.reader.bookmodel;

import com.qicaixiong.reader.bookmodel.animator.immediately.*;
import com.qicaixiong.reader.bookmodel.animator.order.*;
import com.qicaixiong.reader.bookmodel.other.ActionGroup;
import com.qicaixiong.reader.bookmodel.other.BookInfo;
import com.qicaixiong.reader.bookmodel.other.Event;
import com.qicaixiong.reader.bookmodel.other.RectAction;
import com.qicaixiong.reader.bookmodel.other.RectArea;
import com.qicaixiong.reader.bookmodel.other.Resouce;
import com.qicaixiong.reader.bookmodel.other.WidgetValue;

import java.util.ArrayList;

/**
 * 这是一本书的json信息
 */

public class BookContent {
    private BookInfo bookInfo = new BookInfo();//书籍简介
    private ArrayList<Resouce> resouce = new ArrayList<>();
    private ArrayList<PageContent> page = new ArrayList<>();//每一页

    public static class PageContent{

        //动作表
        private ArrayList<FrameAnimator> frameAction = new ArrayList<>();
        private ArrayList<PopViewAnimator> popAction = new ArrayList<>();
        private ArrayList<SeamlessRollingAnimator> seamlessRollingAction = new ArrayList<>();
        private ArrayList<SoundAnimator> soundAction = new ArrayList<>();
        private ArrayList<AlphaAnimator> alphaAction = new ArrayList<>();
        private ArrayList<BesselAnimator> besselAction = new ArrayList<>();
        private ArrayList<RotateAnimator> rotateAction = new ArrayList<>();
        private ArrayList<ScaleAnimator> scaleAction = new ArrayList<>();
        private ArrayList<SideAnimator> sideAction = new ArrayList<>();
        private ArrayList<TranslationAnimator> tanslationAction = new ArrayList<>();

        private ArrayList<ActionGroup> actionGrounp = new ArrayList<>();//动作组
        private ArrayList<Event> event = new ArrayList<>();//事件
        private ArrayList<RectArea> rect = new ArrayList<>();//区域
        private ArrayList<RectAction> rectAction = new ArrayList<>();//区域事件
        private ArrayList<WidgetValue> widgetValues = new ArrayList<>();//节点表
    }
}