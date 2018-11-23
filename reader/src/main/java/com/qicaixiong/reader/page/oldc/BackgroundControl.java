package com.qicaixiong.reader.page.oldc;

import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.qicaixiong.reader.R;
import com.qicaixiong.reader.book.c.StrokeTextViewBuilder;
import com.qicaixiong.reader.other.customview.StrokeTextView;
import com.qicaixiong.reader.m.page.event.ClickRectEvent;
import com.qicaixiong.reader.permission.PermissionManger;
import com.qicaixiong.reader.m.show.AnClickModel;
import com.yyx.commonsdk.app.GlideApp;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static android.util.TypedValue.COMPLEX_UNIT_PX;


/**
 * 当前页的背景图片
 * <p>
 * Created by admin on 2018/8/27.
 */

public class BackgroundControl {

    //展示背景图片
    public static void showBackground(ImageView imageView, String path, int w, int h) {
        if (!TextUtils.isEmpty(path)) {
            GlideApp.with(imageView)
                    .load(Uri.fromFile(new File(path)))
                    .centerCrop()
                    .override(w,h)
                    .into(imageView);
        }
    }


    //在背景上添加点击区域
    public static void addClicks(final PermissionManger permissionManger, final View view, final ArrayList<AnClickModel> clickModels, final int pageNo) {
//        if (MyLog.debug) {
//            for (AnClickModel anClickModel : clickModels) {
//                showClickRect4Debug(view, anClickModel);
//            }
//        }
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (permissionManger.isRectClickable()) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setTag(R.id.point, new PointF(event.getX(), event.getY()));
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        PointF downP = (PointF) v.getTag(R.id.point);
                        PointF upP = new PointF(event.getX(), event.getY());
                        for (AnClickModel it : clickModels) {
                            boolean result1 = isContainsPoint(downP, it.getStartPoint(), it.getEndPoint());
                            boolean result2 = isContainsPoint(upP, it.getStartPoint(), it.getEndPoint());
                            if (result1 && result2) {
                                EventBus.getDefault().post(new ClickRectEvent(it.getWord()
                                        , upP, it.getSound().getAbsolutePath(), pageNo));
                                return true;
                            }
                        }
                    }

                }
                return false;
            }
        });
    }

    private static void showClickRect4Debug(View view, AnClickModel clickModel) {
        ImageView imageView = new ImageView(view.getContext());
        imageView.setBackgroundColor(Color.parseColor("#90000000"));
        ConstraintLayout constraintLayout = (ConstraintLayout) view.getParent();
        int w, h;
        if (clickModel.getStartPoint().x >= 0) {
            w = (int) (clickModel.getEndPoint().x - clickModel.getStartPoint().x);
            h = (int) (clickModel.getEndPoint().y - clickModel.getStartPoint().y);
            new LayoutParamsHelp<ConstraintLayout>(imageView)
                    .creatConstraintLayoutLayoutParams(w, h)
                    .applyMargin_ConstraintLayout(true,true,false,false)
                    .applyMargin_ConstraintLayout(Float.valueOf(clickModel.getStartPoint().x).intValue()
                            , Float.valueOf(clickModel.getStartPoint().y).intValue(), 0, 0)
                    .apply();
            constraintLayout.addView(imageView);
        }
    }

    //点击区域的效果
    public static void showClickRect(ConstraintLayout constraintLayout, ClickRectEvent clickRectEvent
            , HashMap<String, Integer> hashMap) {
        final String word = clickRectEvent.getWord();
        //弹出一个文本
        /**  复用控件 */
        final StrokeTextView textView = StrokeTextViewBuilder.getInstance().getView(constraintLayout.getContext());
        //添加到布局器 同一页复用
        if (textView.getParent() == null)
            constraintLayout.addView(textView);

        textView.setGravity(Gravity.CENTER);
        final Long time = System.currentTimeMillis();
        textView.setTag(time);
        //描边
        textView.setStrokeWidth(8);
        textView.setStrokeColor(Color.WHITE);
        //字体颜色
        textView.setTextColor(Color.RED);
        //旋转
        Integer count = hashMap.get(word);
        if (count != null) {
            ++count;
            hashMap.put(word, count);
            textView.setRotation((count % 3 - 1) * 10);
        } else {
            hashMap.put(word, 1);
            textView.setRotation(15);
        }
        //字体，大小
        textView.setTextSize(COMPLEX_UNIT_PX, 100);
        //文本内容
        textView.setText(word);

        //坐标位置
        textView.measure(0, 0);
        int w = textView.getMeasuredWidth();
        w += w * 0.1f;
        int h = textView.getMeasuredHeight();
        h += h * 0.1f;
        new LayoutParamsHelp<ConstraintLayout>(textView)
                .getLayoutParams(w,h)
                .applyMargin_ConstraintLayout(true,true,false,false)
                .applyMargin_ConstraintLayout(Float.valueOf(clickRectEvent.getShowPoint().x).intValue() - w / 2
                        , Float.valueOf(clickRectEvent.getShowPoint().y).intValue() - h / 2
                        , 0, 0)
                .apply();
        textView.startPop();
    }

    //是否在区域内
    private static boolean isContainsPoint(PointF touch, PointF start, PointF end) {
        return (touch.x >= start.x && touch.x <= end.x
                && touch.y >= start.y && touch.y <= end.y);
    }
}