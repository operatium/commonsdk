package com.yyx.commonsdk.screenadaptation;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yyx.commonsdk.baseclass.Size;


/**
 * Created by admin on 2018/9/14.
 */

public class LayoutParamsHelp<T extends ViewGroup> {
    private T.LayoutParams params;
    private View view;
    private SceenFitFactory fitFactory;

    public LayoutParamsHelp(View view) {
        this.view = view;
    }

    public LayoutParamsHelp(T.LayoutParams params, View view) {
        this.params = params;
        this.view = view;
    }

    //应用
    public void apply() {
        if (view != null && params != null)
            view.setLayoutParams(params);
    }

    public LayoutParamsHelp with(SceenFitFactory fitFactory) {
        this.fitFactory = fitFactory;
        return this;
    }

    public LayoutParamsHelp applyPadding(int start, int top, int end, int bottom) {
        int s = start;
        int t = top;
        int e = end;
        int b = bottom;
        if (fitFactory != null) {
            s = fitFactory.getSizeInt(start);
            t = fitFactory.getSizeInt(top);
            e = fitFactory.getSizeInt(end);
            b = fitFactory.getSizeInt(bottom);
        }
        view.setPadding(s, t, e, b);
        return this;
    }
    //提供ViewGroup.LayoutParams
    public LayoutParamsHelp creatLayoutParams(int width, int height) {
        int w = width;
        int h = height;
        if (fitFactory != null) {
            w = fitFactory.getSizeInt(w);
            h = fitFactory.getSizeInt(h);
        }
        params = new T.LayoutParams(w, h);
        return this;
    }

    //从view中提取ConstraintLayout.LayoutParams
    public LayoutParamsHelp getLayoutParams(int width, int height) {
        params = view.getLayoutParams();
        if (params != null) {
            if (width != 0) {
                int w = width;
                if (width > 0) {
                    if (fitFactory != null)
                        w = fitFactory.getSizeInt(width);
                }
                params.width = w;
            }
            if (height != 0) {
                int h = height;
                if (height > 0) {
                    if (fitFactory != null)
                        h = fitFactory.getSizeInt(height);
                }
                params.height = h;
            }
        }
        return this;
    }


    /**----------------------ConstraintLayout
     */
    public LayoutParamsHelp applyMargin_ConstraintLayout(boolean start, boolean top, boolean end, boolean bottom) {
        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) params;
        if (start)
            p.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        else
            p.startToStart = -1;
        if (top)
            p.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        else
            p.topToTop = -1;
        if (end)
            p.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        else
            p.endToEnd = -1;
        if (bottom)
            p.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        else
            p.bottomToBottom = -1;
        return this;
    }
    public LayoutParamsHelp applyMargin_ConstraintLayout(int start, int top, int end, int bottom) {
        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) params;
        int s = start;
        int t = top;
        int e = end;
        int b = bottom;
        if (fitFactory != null) {
            s = fitFactory.getSizeInt(start);
            t = fitFactory.getSizeInt(top);
            e = fitFactory.getSizeInt(end);
            b = fitFactory.getSizeInt(bottom);
        }
        p.setMargins(s, t, e, b);
        return this;
    }

    //设置横向布局约束
    public LayoutParamsHelp applyChainStyle_ConstraintLayout(int horizontalChainStyle,int verticalChainStyle){
        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) params;
        if (horizontalChainStyle >= 0)
            p.horizontalChainStyle = horizontalChainStyle;
        if (verticalChainStyle >= 0)
            p.verticalChainStyle = verticalChainStyle;
        return this;
    }

    //提供ConstraintLayout.LayoutParams
    public LayoutParamsHelp creatConstraintLayoutLayoutParams(int width, int height) {
        int w = width;
        int h = height;
        if (fitFactory != null) {
            if (w > 0)
                w = fitFactory.getSizeInt(w);
            if (h > 0)
                h = fitFactory.getSizeInt(h);
        }
        params = new ConstraintLayout.LayoutParams(w, h);
        return this;
    }
    public LayoutParamsHelp creatConstraintLayoutLayoutParams(Size size) {
        int w = size.getWidth();
        int h = size.getHeight();
        if (fitFactory != null) {
            if (w > 0)
                w = fitFactory.getSizeInt(w);
            if (h > 0)
                h = fitFactory.getSizeInt(h);
        }
        params = new ConstraintLayout.LayoutParams(w, h);
        return this;
    }


    public LayoutParamsHelp applyMargin_LinearLayout(int start, int top, int end, int bottom) {
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) params;
        int s = start;
        int t = top;
        int e = end;
        int b = bottom;
        if (fitFactory != null) {
            s = fitFactory.getSizeInt(start);
            t = fitFactory.getSizeInt(top);
            e = fitFactory.getSizeInt(end);
            b = fitFactory.getSizeInt(bottom);
        }
        p.setMargins(s, t, e, b);
        return this;
    }

    public T.LayoutParams getParams() {
        return params;
    }
}