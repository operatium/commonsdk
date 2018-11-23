package com.qicaibear.bookplayer.v.view.turnpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.qicaibear.bookplayer.c.CoordinatesConversion;
import com.qicaibear.bookplayer.c.FileController;
import com.qicaibear.bookplayer.c.PermissionManger;
import com.qicaibear.bookplayer.get.EndFragmentCallback;
import com.qicaibear.bookplayer.get.ReadBookCallback;
import com.qicaibear.bookplayer.m.server.PbBooksDetail;
import com.qicaibear.bookplayer.v.fragment.PageFragment;
import com.yyx.commonsdk.file.FileUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/11/3.
 */

public class PageTurnViewAdapter {
    private ArrayList<Integer> myData = new ArrayList<>();//pageID

    //必要参数
    private FragmentManager fragmentManager;
    private CoordinatesConversion conversion;
    private PermissionManger permissionManger;
    private String path;

    //回调函数
    private ReadBookCallback readcallback;
    private EndFragmentCallback endPageCallback;

    public PageTurnViewAdapter(PermissionManger permissionManger, FragmentManager fm, CoordinatesConversion conversion
            , ReadBookCallback readcallback
            , EndFragmentCallback endPageCallback) {
        fragmentManager = fm;
        this.endPageCallback = endPageCallback;
        this.conversion = conversion;
        this.permissionManger = permissionManger;
        this.readcallback = readcallback;
    }


    public void reLoad(String path, FileController fileController) {
        this.path = path;
        String pageNoJson = FileUtils.readCacheAbsolute(path, fileController.relativePathPageNoList());//List<PbBooksDetail>
        List<PbBooksDetail> pageNoList = JSON.parseArray(pageNoJson, PbBooksDetail.class);
        if (pageNoList != null && !pageNoList.isEmpty()) {
            Collections.sort(pageNoList, new Comparator<PbBooksDetail>() {
                @Override
                public int compare(PbBooksDetail a, PbBooksDetail b) {
                    return a.getPageNo() - b.getPageNo();
                }
            });
            myData.clear();
            for (PbBooksDetail it : pageNoList){
                myData.add(it.getId());
            }
            if (endPageCallback != null)
                myData.add(-1);
        }
    }

    public Fragment getItem(int position) {
        int pageId = myData.get(position);
        if (pageId == -1) {
            if (endPageCallback != null)
                return endPageCallback.createEndPageFragment(position);
        } else {
            return PageFragment.Companion.newInstance(path, pageId, conversion.getSceenFitFactory().getScale()
                    , conversion.getCenter().x, conversion.getCenter().y, conversion.getWidth(), conversion.getHeight(), position);
        }
        return null;
    }

    public int getCount() {
        return myData.size();
    }

    //翻页函数
    public void turnPage(int viewID, int mCurrentPage) {
        if (getCount() <= 0)
            return;
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
                if (pageFragment != null){
                    if (i == mCurrentPage)
                        pageFragment.setUserVisibleHint(true);
                    else
                        pageFragment.setUserVisibleHint(false);
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
