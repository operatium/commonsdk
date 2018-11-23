package com.qicaixiong.reader.page.oldc;

import android.text.TextUtils;

import com.yyx.commonsdk.sound.SoundPlayManager;
import com.qicaixiong.reader.permission.PermissionManger;
import com.yyx.commonsdk.log.MyLog;

import java.io.File;

/**
 * Created by admin on 2018/8/28.
 */

public class PlaySoundControl {

    //点击区域 播放读音
    public static boolean rectPlay(PermissionManger permissionManger, String word, String soundPath) {
        if (permissionManger.isAllTextPlayContinuity() && permissionManger.isAllTextPlaying()){
            return false;
        }
        SoundPlayManager.getInstance().play(soundPath, word,1);
        if (!TextUtils.isEmpty(soundPath) && new File(soundPath).exists()) {
            return true;
        } else {
            MyLog.d("show", soundPath + " of3 sound is not exists / " + soundPath);
        }
        return false;
    }

    //点击单词 播放读音
    public static boolean wordPlay(PermissionManger permissionManger, String soundPath, String tag) {
        if (permissionManger.isAllTextPlayContinuity() && permissionManger.isAllTextPlaying()){
            return false;
        }
        SoundPlayManager.getInstance().play(soundPath, tag, 1);
        if (!TextUtils.isEmpty(soundPath) && new File(soundPath).exists()) {
            return true;
        } else {
            MyLog.d("show", soundPath + " of2 sound is not exists / ");
        }
        return false;
    }

    //点击重读
    public static boolean rePlay(PermissionManger permissionManger, String soundPath, String tag) {
        if (permissionManger.isMutePlayable())
            SoundPlayManager.getInstance().play(soundPath, tag, 0);
        else
            SoundPlayManager.getInstance().play(soundPath, tag, 1);

        if (!TextUtils.isEmpty(soundPath) && new File(soundPath).exists()) {
            return true;
        } else {
            MyLog.d("show", soundPath + " of1 sound is not exists / " + soundPath);
        }
        return false;
    }
}