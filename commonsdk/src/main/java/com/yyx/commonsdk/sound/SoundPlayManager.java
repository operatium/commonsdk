package com.yyx.commonsdk.sound;

import android.app.Application;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.text.TextUtils;

import com.yyx.commonsdk.log.MyLog;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by admin on 2018/9/6.
 */

public class SoundPlayManager {
    private static SoundPlayManager instance;

    private SoundPool soundPool;
    private SoundPlayMapHelp mapHelp = new SoundPlayMapHelp();

    public static SoundPlayManager getInstance() {
        if (instance == null) {
            instance = new SoundPlayManager();
            instance.createSoundPool(1);
        }
        return instance;
    }

    public static void destroyInstance() {
        if (instance != null) {
            instance.destory();
            instance = null;
        }
    }

    //初始化声音池
    public void createSoundPool(int max) {
        mapHelp.setMax(max);
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频的数量
            builder.setMaxStreams(max);
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            soundPool = new SoundPool(max, AudioManager.STREAM_MUSIC, 5);
        }
    }

    //播放
    public void play(final String path, String word, final int soundVol) {
        if (TextUtils.isEmpty(path)) return;
        if (soundPool == null) {
            createSoundPool(1);
        }
        final String tag = word + soundVol;
        if (mapHelp.isLoaded(tag)) {
            int soundid = mapHelp.getSoundID(tag);
            int streamID = soundPool.play(soundid, soundVol, soundVol, 1, 0, 1);
            if (streamID != 0)
                mapHelp.addStream(streamID);
        } else {
            soundPool.load(path, 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (status == 0) {
                        mapHelp.addSound(sampleId, path, tag);
                        int streamID = soundPool.play(sampleId, soundVol, soundVol, 0, 0, 1);
                        if (streamID != 0)
                            mapHelp.addStream(streamID);
                        MyLog.d("show", "play " + path + " / " + streamID);
                    }
                }
            });
        }
    }

    //播放
    public void play(final String path, String word, final float soundVol, final int repeatCount) {
        if (soundPool == null || TextUtils.isEmpty(path)) return;
        final String tag = word + soundVol + repeatCount;
        if (mapHelp.isLoaded(tag)) {
            int soundid = mapHelp.getSoundID(tag);
            int streamID = soundPool.play(soundid, soundVol, soundVol, 1, repeatCount, 1);
            if (streamID != 0)
                mapHelp.addStream(streamID);
        } else {
            soundPool.load(path, 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (status == 0) {
                        mapHelp.addSound(sampleId, path, tag);
                        int streamID = soundPool.play(sampleId, soundVol, soundVol, 0, repeatCount, 1);
                        if (streamID != 0)
                            mapHelp.addStream(streamID);
                        MyLog.d("show", "play " + path + " / " + streamID);
                    }
                }
            });
        }
    }

    //播放
    public void play(Context context, final String assetsPath, final String word, final int soundVol) {
        if (soundPool == null || TextUtils.isEmpty(assetsPath)) return;

        final String tag = word + soundVol;

        if (mapHelp.isLoaded(tag)) {
            int soundid = mapHelp.getSoundID(tag);
            int streamID = soundPool.play(soundid, soundVol, soundVol, 1, 0, 1);
            if (streamID != 0)
                mapHelp.addStream(streamID);
        } else {
            try {
                soundPool.load(context.getAssets().openFd(assetsPath), 1);
            } catch (IOException e) {
                MyLog.e("201810111713", e.toString(), e);
            }
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (status == 0) {
                        mapHelp.addSound(sampleId, assetsPath, tag);
                        int streamID = soundPool.play(sampleId, soundVol, soundVol, 0, 0, 1);
                        if (streamID != 0)
                            mapHelp.addStream(streamID);
                    }
                }
            });
        }
    }

    //暂停
    public void pause() {
        if (soundPool != null) {
            soundPool.autoPause();
        }
    }

    //恢复
    public void resume() {
        if (soundPool != null) {
            soundPool.autoResume();
        }
    }

    //清空声音
    public void clear() {
        if (soundPool != null) {
            ArrayList<Integer> list = mapHelp.getAllSoundId();
            for (int soundid : list) {
                soundPool.unload(soundid);
            }
            ArrayList<Integer> stream = mapHelp.getPlayingStream();
            for (int streamID : stream) {
                soundPool.stop(streamID);
                MyLog.d("show","stop "+streamID);
            }
        }
        mapHelp.clear();
    }

    //销毁
    public void destory() {
        if (null != soundPool) {
            clear();
            soundPool.release();
            soundPool = null;
            mapHelp = null;
        }
    }
}