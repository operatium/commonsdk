package com.qicaibear.bookplayer.c;

import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.sound.SoundPlayManager;

import java.util.Collection;
import java.util.HashMap;

/**
 * 音轨管理
 */
public class SoundController {
    private String myName;
    private HashMap<Integer, SoundPlayManager> poolMap = new HashMap<>();

    public void play(int audioTrack, String path, String word, float soundVol, int repeatCount) {
        SoundPlayManager pool = poolMap.get(audioTrack);
        if (pool == null) {
            pool = new SoundPlayManager();
            pool.createSoundPool(1);
            poolMap.put(audioTrack, pool);
        }
        pool.play(path, word, soundVol, repeatCount);
        MyLog.d("show","SoundController play ("+path+") , soundVol = " + soundVol+" , repeatCount = "+repeatCount);
    }

    public void pause() {
        Collection<SoundPlayManager> pools = poolMap.values();
        for (SoundPlayManager it : pools) {
            it.pause();
        }
    }

    public void resume() {
        Collection<SoundPlayManager> pools = poolMap.values();
        for (SoundPlayManager it : pools) {
            it.resume();
        }
    }

    public void destory() {
        Collection<SoundPlayManager> pools = poolMap.values();
        for (SoundPlayManager it : pools) {
            it.destory();
        }
        poolMap.clear();
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }
}