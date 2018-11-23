package com.qicaixiong.reader.book.c;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qicaixiong.reader.get.EndFragmentCallback;
import com.qicaixiong.reader.get.ReadBookCallback;
import com.qicaixiong.reader.bookmodel.other.BookInfo;
import com.qicaixiong.reader.page.v.NewPageFragment;
import com.qicaixiong.reader.permission.PermissionManger;
import com.qicaixiong.reader.resource.c.CoordinatesConversion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2018/11/3.
 */

public class PageTurnViewAdapter {
    private String json;
    private ArrayList<String> pageData;
    private BookInfo bookInfo;

    private FragmentManager fragmentManager;
    private ReadBookCallback readcallback;
    private EndFragmentCallback callback;
    private CoordinatesConversion conversion;
    private PermissionManger permissionManger;

    private FragmentTransaction mCurTransaction = null;
    private HashMap<Integer, Fragment> mFragments = new HashMap<>();

    public PageTurnViewAdapter(PermissionManger permissionManger, FragmentManager fm, CoordinatesConversion conversion
            , ReadBookCallback readcallback
            , EndFragmentCallback callback) {
        fragmentManager = fm;
        this.callback = callback;
        this.conversion = conversion;
        this.permissionManger = permissionManger;
        this.readcallback = readcallback;
    }


    public void reLoad(String json){
        this.json = json;
        pageData = new ArrayList<>();
        pageData.add("0");
        pageData.add("1");
        pageData.add("2");
        pageData.add("3");
        pageData.add("4");
    }

    public Fragment getItem(int position) {
        if (position >= 0 && position < getCount()) {
            String pageJson = pageData.get(position);
            return NewPageFragment.Companion.newInstance(pageJson,conversion.getSceenFitFactory().getScale()
            , conversion.getCenter().x,conversion.getCenter().y, conversion.getWidth(), conversion.getHeight(),position);
        } else {
            if (callback != null)
                return callback.createEndPageFragment();
        }
        return null;
    }

    public int getCount() {
        if (pageData == null)
            return 0;
        if (callback == null)
            return pageData.size();
        else
            return pageData.size() +1;
    }

    //翻页函数
    public void turnPage(int viewID, int mCurrentPage) {
        mCurrentPage = getPage(mCurrentPage);
        int nextPage = getPage(mCurrentPage + 1);
        int prePage = getPage(mCurrentPage - 1);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < getCount(); i++) {
            String tag = "" + i;
            Fragment pageFragment = fragmentManager.findFragmentByTag(tag);
            if (i == mCurrentPage || i == nextPage || i == prePage) {
                if (pageFragment == null) {
                    pageFragment = getItem(i);
                    transaction.add(viewID, pageFragment, tag);
                }
                if (pageFragment != null && i == mCurrentPage){
                    pageFragment.setUserVisibleHint(true);
                }
            } else {
                if (pageFragment != null)
                    transaction.remove(pageFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    //修正页码
    public int getPage(int page) {
        int c = getCount();
        if (page < 0) {
            page = 0;
        } else if (page >= c) {
            page = c - 1;
        }
        return page;
    }
}
