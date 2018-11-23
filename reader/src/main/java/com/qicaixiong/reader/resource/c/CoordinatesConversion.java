package com.qicaixiong.reader.resource.c;

import android.graphics.Point;
import android.graphics.PointF;

import com.yyx.commonsdk.baseclass.Size;
import com.yyx.commonsdk.screenadaptation.SceenFitFactory;

/**
 * 转换类
 *
 * 坐标转换
 * 1.数学坐标系
 * 2.设备中心为原点 => 设备左上角为原点
 * 3.非安全 1600*720  安全 960*720
 * 4.横屏
 *
 * 字体大小转换
 *
 * Created by admin on 2018/8/30.
 */

public class CoordinatesConversion {
    private SceenFitFactory sceenFitFactory = new SceenFitFactory();
    private PointF center = new PointF(0,0);
    private int width;//宽高用于 横竖屏切换的时候 宽高错误时候的校验
    private int height;

    public CoordinatesConversion() {
    }

    public CoordinatesConversion(float scale, PointF center, int width, int height) {
        setParamter(scale, center, width, height);
    }

    public void setParamter(float scale, PointF center, int width,int height) {
        if (sceenFitFactory == null)
            sceenFitFactory = new SceenFitFactory();
        sceenFitFactory.setScale(scale);
        this.center = center;
        this.width = width;
        this.height = height;
    }

    public PointF getLocationPoint(PointF designPoint) {
        /**
         * 坐标转换分2步
         * 1.原点转换
         * 2.距离转换
         * 3.方向转换
         */
        //原点
        float Ox = center.x;
        float Oy = center.y;
        //距离
        float x = sceenFitFactory.getSizeFloat(designPoint.x);
        float y = sceenFitFactory.getSizeFloat(designPoint.y);
        //方向
//        MyLog.d("show", designPoint.toString() + " ==>" + "(" + (Ox + x) + "," + (Oy - y) + ")");
        return new PointF(Ox + x, Oy - y);
    }

    public Point getLocationPoint(Point designPoint) {
        /**
         * 坐标转换分2步
         * 1.原点转换
         * 2.距离转换
         * 3.方向转换
         */
        //原点
        float Ox = center.x;
        float Oy = center.y;
        //距离
        float x = sceenFitFactory.getSizeFloat(designPoint.x);
        float y = sceenFitFactory.getSizeFloat(designPoint.y);
        //方向
//        MyLog.d("show", designPoint.toString() + " ==>" + "(" + (Ox + x) + "," + (Oy - y) + ")");
        return new Point((int) (Ox + x), (int) (Oy - y));
    }

    public PointF getLocationPointF(Point designPoint) {
        /**
         * 坐标转换分2步
         * 1.原点转换
         * 2.距离转换
         * 3.方向转换
         */
        //原点
        float Ox = center.x;
        float Oy = center.y;
        //距离
        float x = sceenFitFactory.getSizeFloat(designPoint.x);
        float y = sceenFitFactory.getSizeFloat(designPoint.y);
        //方向
//        MyLog.d("show", designPoint.toString() + " ==>" + "(" + (Ox + x) + "," + (Oy - y) + ")");
        return new PointF((Ox + x), (Oy - y));
    }

    //长度转换
    public float getLocationWidth(float fontSize){
        return sceenFitFactory.getSizeFloat(fontSize);
    }

    public int getLocationWidth(int fontSize){
        return sceenFitFactory.getSizeInt(fontSize);
    }

    //宽高尺寸转换
    public Size getLocationSize(Size size){
        return new Size(getLocationWidth(size.getWidth()),getLocationWidth(size.getHeight()));
    }

    public SceenFitFactory getSceenFitFactory() {
        return sceenFitFactory;
    }

    public void setSceenFitFactory(SceenFitFactory sceenFitFactory) {
        this.sceenFitFactory = sceenFitFactory;
    }

    public PointF getCenter() {
        return center;
    }

    public void setCenter(PointF center) {
        this.center = center;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
