package com.qicaixiong.reader.page.oldc;

/**
 * 边界检测类
 * Created by admin on 2018/9/27.
 */

public class BoundaryDetection {

    public static boolean overstepRight(float layoutLeft,float layoutWidth,float viewLeft,float viewWidth){
        return !((layoutLeft + layoutWidth) >= (viewLeft + viewWidth));
    }

    public static boolean overstepBottom(float layoutTop,float layoutHeight,float viewTop,float viewHeight){
        return !((layoutTop + layoutHeight) >= (viewTop + viewHeight));
    }

}
