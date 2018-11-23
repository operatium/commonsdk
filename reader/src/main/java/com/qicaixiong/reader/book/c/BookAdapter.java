package com.qicaixiong.reader.book.c;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qicaixiong.reader.get.EndFragmentCallback;
import com.qicaixiong.reader.get.ReadBookCallback;
import com.qicaixiong.reader.get.ReaderHelp;
import com.qicaixiong.reader.page.get.PagerFragmentBulider;
import com.qicaixiong.reader.page.v.PagerFragment;
import com.qicaixiong.reader.permission.PermissionManger;
import com.qicaixiong.reader.resource.c.CoordinatesConversion;
import com.qicaixiong.reader.m.show.AnPageModel;

/**
 * Created by admin on 2018/8/29.
 */

public class BookAdapter extends FragmentStatePagerAdapter {
    private ReadBookCallback readcallback;
    private EndFragmentCallback callback;
    private CoordinatesConversion conversion;
    private int pageCount;
    private PermissionManger permissionManger;
    private int firstPage;

    public BookAdapter(PermissionManger permissionManger, FragmentManager fm, CoordinatesConversion conversion
                       , ReadBookCallback readcallback
            , EndFragmentCallback callback
    , int firstPage) {
        super(fm);
        this.callback = callback;
        this.conversion = conversion;
        this.permissionManger = permissionManger;
        this.readcallback = readcallback;
        this.firstPage = firstPage;
    }

    @Override
    public Fragment getItem(int i) {
        if (i >= 0 && i < pageCount) {
            AnPageModel anPageModel = ReaderHelp.getInstance().getPageModels().get(i);
            PagerFragment fragment = PagerFragmentBulider.createPager(anPageModel,conversion, permissionManger);
            fragment.setFirstPage(firstPage);
            fragment.setStartCallback(readcallback);
            firstPage = -1;
            return fragment;
        } else {
            return PagerFragmentBulider.createEndPager(callback);
        }
    }

    @Override
    public int getCount() {
        if (callback == null)
            return pageCount;
        else
            return pageCount +1;
    }

    public void reLoad() {
        pageCount = ReaderHelp.getInstance().getPageModels().size();
    }

    public void clear(){
        pageCount = 0;
        callback = null;
    }
}
