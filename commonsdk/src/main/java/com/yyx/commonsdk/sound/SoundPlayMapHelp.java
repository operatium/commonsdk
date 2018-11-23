package com.yyx.commonsdk.sound;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 声音 word  soundID 一一对应
 * 声音提供缓存 每一个声音只有一份 可以提供soundID重复播放
 *
 * Created by admin on 2018/9/6.
 */

public class SoundPlayMapHelp {
    private int max;
    private HashMap<String ,AnSound> mySounds = new HashMap<>();// key = word , value = AnSound
    private HashMap<Integer,String> soundIDs = new HashMap<>();//key = soundID , value = word
    private ArrayList<Integer> playStreams = new ArrayList<>();//key = streamID

    public ArrayList<Integer> getAllSoundId(){
        return new ArrayList<>(soundIDs.keySet());
    }

    public void addSound(int soundID, String path, String word){
        mySounds.put(word,new AnSound(soundID,path,word));
        soundIDs.put(soundID,word);
    }

    public void deleteSound(int soundID){
        String word = soundIDs.get(soundID);
        if (word != null){
            mySounds.remove(word);
            soundIDs.remove(soundID);
        }
    }

    public void deleteSound(String word){
        AnSound sound = mySounds.get(word);
        if (sound != null){
            soundIDs.remove(sound.getSoundID());
            mySounds.remove(word);
        }
    }

    public int getSoundID(String word){
        AnSound sound = mySounds.get(word);
        if (sound == null){
            return 0;
        }else {
            return sound.getSoundID();
        }
    }

    public boolean isLoaded(String word){
        AnSound sound = mySounds.get(word);
        return sound != null;
    }

    public void clear(){
        mySounds.clear();
        soundIDs.clear();
        playStreams.clear();
    }

    public void addStream(int streamID){
        playStreams.add(streamID);
    }

    public ArrayList<Integer> getPlayingStream() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            int j = playStreams.size() - i;
            if (j >= 0 && j <playStreams.size())
                list.add(playStreams.get(j));
        }
        playStreams.clear();
        playStreams.addAll(list);
        return list;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
