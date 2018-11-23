package com.qicaixiong.reader.page.oldc;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.qicaixiong.reader.get.ReaderHelp;
import com.qicaixiong.reader.other.customview.WordTextView;
import com.qicaixiong.reader.m.page.event.TextClickShowAnimationEvent;
import com.qicaixiong.reader.permission.PermissionManger;
import com.qicaixiong.reader.m.show.AnTextModel;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 阅读器的本文 展示左上角坐标 字体大小 颜色  点击播音
 * Created by admin on 2018/8/27.
 */

public class TextControl {

    //展示整段
    public static void showTexts(PermissionManger permissionManger,ConstraintLayout constraintLayout, ArrayList<AnTextModel> textModels) {
        for (AnTextModel textModel : textModels) {
            try {
                showAnText(permissionManger,constraintLayout, textModel);
            } catch (Exception e) {
                MyLog.e("201809051053", e.toString(), e);
            }
        }
    }

    //展示单词
    private static void showAnText(final PermissionManger permissionManger
            ,final ConstraintLayout constraintLayout
            , final AnTextModel textModel) {
        WordTextView textView = new WordTextView(constraintLayout.getContext());
        textView.setTextSize(COMPLEX_UNIT_PX,textModel.getFontSize());
        new LayoutParamsHelp<ConstraintLayout>(textView)
                .creatConstraintLayoutLayoutParams(-2,-2)
                .applyMargin_ConstraintLayout(true,true,false,false)
                .applyMargin_ConstraintLayout(textModel.getPositionX(), textModel.getPositionY(), 0, 0)
                .apply();
        textView.getPaint().setFakeBoldText(textModel.getFontBold() == 1);
        try {
//            MyLog.d("show","color = " + textModel.getFontColor());
            textView.setInitColor(Color.parseColor(textModel.getFontColor()));
            textView.setTextColor(Color.parseColor(textModel.getFontColor()));
        } catch (Exception e) {
            //todo 恢复
//            MyLog.e("201809051054", e.toString(), e);
            textView.setInitColor(Color.BLACK);
            textView.setTextColor(Color.BLACK);
        }
        textView.setText(textModel.getWord());
        textView.setTime(Math.abs(textModel.getPlayTime()));
        textView.setSoundPath(textModel.getAudio().getAbsolutePath());
        constraintLayout.addView(textView);

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击单词 播放读音
                if (permissionManger.isTextClickable()) {
                    WordTextView view = (WordTextView) v;
                    String tag = view.getSoundTag();
                    String sound = view.getSoundPath();
                    int time = view.getTime();
                    PlaySoundControl.wordPlay(permissionManger, sound, tag);
                    EventBus.getDefault().post(new TextClickShowAnimationEvent(tag, time));
                    ReaderHelp.getInstance().addWord(tag);
                }
            }
        });
    }
}
