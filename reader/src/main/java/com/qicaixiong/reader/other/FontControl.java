package com.qicaixiong.reader.other;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 字体管理
 * Created by admin on 2018/8/28.
 */

public class FontControl {
    private static Typeface typeface;

    public static Typeface getNewton_Regular(Context context){
        if (typeface == null)
            typeface =Typeface.createFromAsset(context.getAssets(),"font/Newton_Regular.ttf");
        return typeface;
    }
}