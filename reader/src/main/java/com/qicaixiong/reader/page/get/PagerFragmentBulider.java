package com.qicaixiong.reader.page.get;

import android.support.v4.app.Fragment;

import com.qicaixiong.reader.get.EndFragmentCallback;
import com.qicaixiong.reader.page.v.PagerFragment;
import com.qicaixiong.reader.permission.PermissionManger;
import com.qicaixiong.reader.resource.c.CoordinatesConversion;
import com.qicaixiong.reader.resource.c.DataPipeline;
import com.qicaixiong.reader.m.show.AnPageModel;
import com.yyx.commonsdk.log.MyLog;

/**
 * PagerFragment的辅助构造类
 */

public class PagerFragmentBulider {

    public static PagerFragment createPager( AnPageModel anPageModel, CoordinatesConversion conversion, PermissionManger p) {
        PagerFragment fragment = new PagerFragment();
        fragment.setConversion(conversion);
        fragment.setPermissionManger(p);
        fragment.setMydata(DataPipeline.valueTranform(anPageModel,conversion));
        return fragment;
    }

    //创建最后一页
    public static Fragment createEndPager(EndFragmentCallback callback){
        Fragment fragment = null;
        if (callback != null)
            fragment = callback.createEndPageFragment();
        if (fragment == null) {
            fragment = new Fragment();
        }
        return fragment;
    }
}