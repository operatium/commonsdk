package com.qicaibear.bookplayer.get;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;

import com.qicaibear.bookplayer.c.CoordinatesConversion;
import com.qicaibear.bookplayer.c.FileController;
import com.qicaibear.bookplayer.c.PermissionManger;
import com.qicaibear.bookplayer.v.view.turnpage.PageTurnView;
import com.qicaibear.bookplayer.v.view.turnpage.PageTurnViewAdapter;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.sound.SoundPlayManager;

/**
 * 儿童绘本播放器
 */

public class HelloKidPlayer extends PageTurnView {
    private FileController fileController = new FileController();
    private PermissionManger permissionManger;
    private FragmentManager fm;
    private CoordinatesConversion conversion;
    private ReadBookCallback readcallback;
    private EndFragmentCallback callback;
    private PageTurnViewAdapter adapter;
    private String path;

    public HelloKidPlayer(Context context) {
        super(context);
    }

    public HelloKidPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HelloKidPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MyLog.d("show1", "HelloKidPlayer onDetachedFromWindow");
        SoundPlayManager.getInstance().clear();
    }

    public void setPlayMode(PermissionManger permissionManger
            , FragmentManager fm
            , CoordinatesConversion conversion
            , ReadBookCallback readcallback
            , EndFragmentCallback callback) {
        this.permissionManger = permissionManger;
        this.fm = fm;
        this.conversion = conversion;
        this.readcallback = readcallback;
        this.callback = callback;
    }

    public void setData(String path){
        this.path = path;
    }

    public void setFileController(FileController fileController){
        if (fileController != null)
            this.fileController = fileController;
    }

    public void start(){
        MyLog.d("show1", "HelloKidPlayer start");
        adapter = new PageTurnViewAdapter(permissionManger,fm,conversion,readcallback,callback);
        adapter.reLoad(path,fileController);
        setAdapter(adapter);
    }

    public void pause(){
        MyLog.d("show1", "HelloKidPlayer pause");
        SoundPlayManager.getInstance().clear();
    }

    public void resume(){
        MyLog.d("show1", "HelloKidPlayer resume");
    }

    public void stop(){
        MyLog.d("show1", "HelloKidPlayer stop");
    }
}
