package com.yyx.commonsdk.screenadaptation;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.HashMap;

/**
 * 屏幕适配工厂
 * Created by admin on 2018/9/14.
 */

public class SceenFitFactory {
    private static HashMap<String, Typeface> map = new HashMap<>();

    private float scale = 1;//缩放比例

    public SceenFitFactory() {
    }

    public SceenFitFactory(float scale) {
        this.scale = scale;
    }

    public static SceenFitFactory createFitFactory(int width, int desWidth) {
        return new SceenFitFactory(width * 1f / desWidth);
    }

    //设置文本控件的左图标
    public void textViewSetLeftIcon(TextView view, int pic, int width, int height, int drawablePadding) {
        if (pic > 0) {
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), pic);
            if (drawable != null) {
                drawable.setBounds(0, 0, getSizeInt(width), getSizeInt(height));
                view.setCompoundDrawables(drawable, null, null, null);
            }
            if (drawablePadding > 0) {
                view.setCompoundDrawablePadding(getSizeInt(drawablePadding));
            }
        }
    }
    //上图标
    public void textViewSetTopIcon(TextView view, int pic, int width, int height, int drawablePadding) {
        if (pic > 0) {
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), pic);
            if (drawable != null) {
                drawable.setBounds(0, 0, getSizeInt(width), getSizeInt(height));
                view.setCompoundDrawables(null, drawable, null, null);
            }
            if (drawablePadding > 0) {
                view.setCompoundDrawablePadding(getSizeInt(drawablePadding));
            }
        }
    }

    //下图标
    public void textViewSetBottomIcon(TextView view, int pic, int width, int height, int drawablePadding) {
        if (pic > 0) {
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), pic);
            if (drawable != null) {
                drawable.setBounds(0, 0, getSizeInt(width), getSizeInt(height));
                view.setCompoundDrawables(null, null, null, drawable);
            }
            if (drawablePadding > 0) {
                view.setCompoundDrawablePadding(getSizeInt(drawablePadding));
            }
        }
    }

    //设置文本控件的字体大小
    public void textViewSets(TextView view, int size, String fontTTF, int color) {
        if (size > 0)
            setTextSize(view, size);
        if (!TextUtils.isEmpty(fontTTF))
            setTextFont(view, fontTTF);
        if (color != 0) {
            view.setTextColor(color);
        }
    }

    //文本控制字体
    public void setTextFont(TextView view, String fontTTF) {
        Typeface typeface = map.get(fontTTF);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/" + fontTTF);
            map.put(fontTTF, typeface);
        }
        view.setTypeface(typeface);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    //文本控件字号
    public void setTextSize(TextView view, int fontsize) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getSizeInt(fontsize));
    }

    public float getSizeFloat(float designSize){
        return designSize * scale;
    }

    public int getSizeInt(float designSize){
        return Float.valueOf(designSize * scale).intValue();
    }

    public float[] getSizeFolatArray(float[] designSize){
        float[] result = new float[designSize.length];
        for (int i = 0 ; i < designSize.length ; i++){
            result[i] = getSizeFloat(designSize[i]);
        }
        return result;
    }

    public float getScale() {
        return scale;
    }
}
