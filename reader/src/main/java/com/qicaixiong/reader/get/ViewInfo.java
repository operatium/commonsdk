package com.qicaixiong.reader.get;

import android.graphics.PointF;

/**
 * 设备信息
 * 横屏
 *
 */
public class ViewInfo {
    public static String screenOrientation = "landscape";//横屏   portrait竖屏
    public static int viewWidth;//屏幕宽
    public static int viewHeight;//屏幕高
    public static int designWidth = 960;
    public static int designHeight = 720;
    public static PointF viewOrigin;
    public static String fitType = "heightFit";//适配方案 heightFit widthFit originalFit
}
