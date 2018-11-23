package com.qicaixiong.reader.page.oldc;

import android.graphics.PointF;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;

import com.qicaixiong.reader.R;
import com.qicaixiong.reader.other.customview.ReplayButton;
import com.qicaixiong.reader.permission.PermissionManger;
import com.qicaixiong.reader.resource.c.CoordinatesConversion;
import com.qicaixiong.reader.m.show.AnClickModel;
import com.qicaixiong.reader.m.show.AnPageModel;
import com.qicaixiong.reader.m.show.AnTextModel;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;

import java.util.ArrayList;

public class PagerFragmentControl {

    //初始化界面背景
    public static void initPagerBackgroundImage(ImageView background, AnPageModel anPageModel, CoordinatesConversion conversion) {
        try{
            BackgroundControl.showBackground(background, anPageModel.getPicture().getAbsolutePath(),conversion.getWidth(),conversion.getHeight());
        }catch (Exception e){
            MyLog.e("201809272130",e.toString(),e);
        }
    }

    //初始化文本
    public static void initText(PermissionManger permissionManger, ConstraintLayout constraintLayout, ArrayList<AnTextModel> textModels) {
        if (textModels != null && textModels.size() > 0)
            TextControl.showTexts(permissionManger,constraintLayout, textModels);
    }

    //添加背景上的点击区域 和事件
    public static void addRectClicks(PermissionManger permissionManger,View view, ArrayList<AnClickModel> clickModels, int pageNo) {
        if (clickModels != null && clickModels.size() > 0)
            BackgroundControl.addClicks(permissionManger,view, clickModels, pageNo);
    }

    //放置重读按钮
    public static ReplayButton initRePlayButton(final String tag, final AnPageModel pageModel, ConstraintLayout constraintLayout) {
        if (pageModel.getVoice() == null)
            return null;

        PointF pointF = pageModel.getReplay();

        ReplayButton button = new ReplayButton(constraintLayout.getContext());
        button.setSoundTag(tag);
        LayoutParamsHelp help = new LayoutParamsHelp<ConstraintLayout>(button)
                .creatConstraintLayoutLayoutParams(pageModel.getReplaySize().x, pageModel.getReplaySize().y);
        constraintLayout.measure(0,0);
        boolean overX = BoundaryDetection.overstepRight(constraintLayout.getX(), constraintLayout.getMeasuredWidth(), pointF.x, pageModel.getReplaySize().x);
        boolean overY = BoundaryDetection.overstepBottom(constraintLayout.getY(),constraintLayout.getMeasuredHeight(),pointF.y,pageModel.getReplaySize().y);
        boolean start = false;
        boolean top = false;
        boolean end = false;
        boolean bottom = false;
        if (overX) {
            end = true;
        }else {
            start = true;
        }
        if (overY)
            bottom = true;
        else
            top = true;
        help.applyMargin_ConstraintLayout(start, top, end, bottom)
                .applyMargin_ConstraintLayout(Float.valueOf(pointF.x).intValue(), Float.valueOf(pointF.y).intValue(), 0, 0)
                .apply();
        button.setImageResource(R.drawable.read_repeat);
        constraintLayout.addView(button);
        return button;
    }
}