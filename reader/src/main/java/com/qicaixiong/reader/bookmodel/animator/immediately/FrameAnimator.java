package com.qicaixiong.reader.bookmodel.animator.immediately;

import com.qicaixiong.reader.bookmodel.animator.AnimatorValue;
import com.yyx.commonsdk.baseclass.Size;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 旋转动画
 *
 * Created by admin on 2018/10/24.
 */

public class FrameAnimator extends AnimatorValue {
    private ArrayList<Integer> listPic;
    private ArrayList<Integer> listTime;
    private boolean isOneShot;
    private Size picSize;


    public FrameAnimator(String myName) {
        super(myName,"FrameAnimator");
    }

    public void addListPic(Integer... pic){
        if (listPic == null)
            listPic = new ArrayList<>();
        listPic.addAll(Arrays.asList(pic));
    }

    public void addListTime(Integer... time){
        if (listTime == null)
            listTime = new ArrayList<>();
        listTime.addAll(Arrays.asList(time));
    }

    public ArrayList<Integer> getListPic() {
        return listPic;
    }

    public void setListPic(ArrayList<Integer> listPic) {
        this.listPic = listPic;
    }

    public ArrayList<Integer> getListTime() {
        return listTime;
    }

    public void setListTime(ArrayList<Integer> listTime) {
        this.listTime = listTime;
    }

    public boolean isOneShot() {
        return isOneShot;
    }

    public void setOneShot(boolean oneShot) {
        isOneShot = oneShot;
    }

    public Size getPicSize() {
        return picSize;
    }

    public void setPicSize(Size picSize) {
        this.picSize = picSize;
    }
}
