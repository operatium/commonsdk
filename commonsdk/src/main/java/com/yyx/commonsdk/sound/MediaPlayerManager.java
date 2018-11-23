package com.yyx.commonsdk.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;

import com.yyx.commonsdk.thread.ThreadPool;
import com.yyx.commonsdk.log.MyLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 1. MediaPlayer支持：AAC、AMR、FLAC、MP3、MIDI、OGG、PCM等格式
 2. 播放Raw下的元数据

 //直接创建，不需要设置setDataSource
 mMediaPlayer=MediaPlayer.create(this, R.raw.audio);
 mMediaPlayer.start();
 1
 2
 3
 4
 3. MediaPlayer设置播放源的4中方式

 setDataSource (String path)
 //从sd卡中加载音乐
 mMediaPlayer.setDataSource("../music/samsara.mp3") ;
 //从网路加载音乐
 mMediaPlayer.setDataSource("http://..../xxx.mp3") ;
 //需使用异步缓冲
 mMediaPlayer.prepareAsync() ;
 1
 2
 3
 4
 5
 6
 7
 setDataSource (FileDescriptor fd)
 //需将资源文件放在assets文件夹
 AssetFileDescriptor fd = getAssets().openFd("samsara.mp3");
 mMediaPlayer.setDataSource(fd)
 mMediaPlayer.prepare() ;

 Ps:此方法系统需大于等于android
 1
 2
 3
 4
 5
 6
 setDataSource (Context context, Uri uri)
 这个方法没什么好说的，一般通过ContentProvider获取Android系统提供
 的共享music获取uri，然后设置数据播放
 1
 2
 setDataSource (FileDescriptor fd, long offset, long length)
 //需将资源文件放在assets文件夹
 AssetFileDescriptor fd = getAssets().openFd("samsara.mp3");
 mMediaPlayer.setDataSource(fd, fd.getStartOffset(), fd.getLegth())
 mMediaPlayer.prepare() ;
 * Created by java on 2017/10/23.
 */

public class MediaPlayerManager {
    private static class MediaPlayerManagerHolder {
        private static final MediaPlayerManager INSTANCE = new MediaPlayerManager();
    }

    public static final MediaPlayerManager getInstance() {
        return MediaPlayerManagerHolder.INSTANCE;
    }

    private SparseArray<MediaPlayer> maps = new SparseArray<>();
    private ArrayList<Integer> ids = new ArrayList<>();//其他声音的ID集合
    private HashMap<String,ArrayList<Integer>> tags = new HashMap<>();//标记每个声音的tag+次数  作用：找出同样的声音
    private static int id = 0;
    public static final int PLAY = 1000;
    public static final int PAUSE = 1001;
    public static final int STOP = 1002;


    //播放一次短音
    private int initMediaPlayer(MediaPlayer mediaPlayer,final String tag, boolean loop,final MediaPlayerCallback callback) {
        final int musicId = ++id;
        try {
            add(mediaPlayer, musicId, tag);
            mediaPlayer.setLooping(loop);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (callback != null)
                        callback.OnCompletion();
                    release(musicId, tag);
                    mp.release();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (callback != null)
                        callback.OnError();
                    release(musicId, tag);
                    mp.release();
                    return true;
                }
            });
            mediaPlayer.start();
        } catch (Exception e) {
            MyLog.e("201808271504", e.toString(), e);
        }
        MyLog.d("show", "music = " + musicId);
        return musicId;
    }

    //记录音频数据
    private void add(MediaPlayer mediaPlayer,int musicId,String tag){
        if (!TextUtils.isEmpty(tag)) {
            ArrayList<Integer> countList = tags.get(tag);
            if (countList == null) {
                countList = new ArrayList<>();
                tags.put(tag, countList);
            }
            countList.add(musicId);
        }
        maps.put(musicId, mediaPlayer);
        ids.add(musicId);
    }

    //释放音频数据
    private void release(int musicId,String tag){
        if (!TextUtils.isEmpty(tag)) {
            ArrayList<Integer> countList = tags.get(tag);
            if (countList != null) {
                countList.remove(Integer.valueOf(musicId));
                MyLog.d("show", "tag remove" + musicId);
            }
        }
        maps.delete(musicId);
        ids.remove(Integer.valueOf(musicId));
    }

    public int playResouce(Context context, int res, String tag,boolean loop, MediaPlayerCallback callback) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, res);
        return initMediaPlayer(mediaPlayer, tag, loop,callback);
    }

    public int playFile(Context context, String path, String tag,boolean loop, MediaPlayerCallback callback) {
        File file = new File(path);
        if (file.exists()) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.fromFile(file));
            return initMediaPlayer(mediaPlayer, tag, loop, callback);
        }else {
            return -1;
        }
    }

    public void pause(int id){
        MediaPlayer mediaPlayer = maps.get(id);
        if (mediaPlayer != null)
            mediaPlayer.pause();
    }

    public boolean resume(int id){
        try {
            MediaPlayer mediaPlayer = maps.get(id);
            if (mediaPlayer == null) {
                MyLog.d("music", "resume sound " + id + " is null");
                release(id,null);
                return false;
            }
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                MyLog.d("music", "resume sound " + id + " is not Playing , => start");
            }
        }catch (Exception e){
            MyLog.d("music", "resume sound " + id + " e = "+e.toString());
            MyLog.e("201806151143",e.toString(),e);
            return false;
        }
        return true;
    }

    public void stop(int id){
        try {
            final MediaPlayer mediaPlayer = maps.get(id);
            if (mediaPlayer != null) {
                release(id,null);
                ThreadPool.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }
                });
            }
            MyLog.d("music", "music = " + id + " stop and release");
        }catch (Exception e){
            MyLog.e("201805181500",e.toString(),e);
        }
    }

    public void stopOther(int...it){
        ArrayList<Integer> list = new ArrayList<>(ids);
        for (Integer id : list){
            boolean back = false;
            for (int x : it){
                if (id == x) {
                    back = true;
                    break;
                }
            }
            if (!back)
                stop(id);
        }
        ids.clear();
        for (int i : it) {
            ids.add(i);
        }
    }

    //删除所有tag的对象 停止其播放
    public void stopTag(String tag){
        if (!TextUtils.isEmpty(tag)) {
            ArrayList<Integer> count = tags.get(tag);
            if (count != null) {
                for (Integer it : count) {
                    stop(it);
                }
            }
            tags.remove(tag);
        }
    }

    //除了第一个音频 删除其他所有tag的对象 停止其播放
    public int stopOtherTag(String tag){
        if (!TextUtils.isEmpty(tag)) {
            ArrayList<Integer> count = tags.get(tag);
            if (count != null && !count.isEmpty()) {
                int onlyID = count.get(0);
                for (int i = 1; i < count.size(); i++) {
                    stop(count.get(i));
                }
                ArrayList<Integer> c = new ArrayList<>();
                c.add(onlyID);
                tags.put(tag, c);
                return onlyID;
            }
        }
        return 0;
    }


    //获取同一个tag 播放的次数
    public int getCount4SameTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            ArrayList<Integer> count = tags.get(tag);
            if (count == null)
                return 0;
            else {
                MyLog.d("show", "getCount4SameTag = " + count.size());
                return count.size();
            }
        }
        return 0;
    }



    //是否正在播放
    public int getStauts(int id){
        int staut = STOP;
        MediaPlayer mediaPlayer = maps.get(id);
        if (mediaPlayer != null) {
            if(mediaPlayer.isPlaying())
                staut = PLAY;
            else
                staut = PAUSE;
        }
        return staut;
    }

    //获取进度
    public int getProgress(int id){
        int p = 999;
        try {
            MediaPlayer mediaPlayer = maps.get(id);
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int pos = mediaPlayer.getCurrentPosition();
                int max = mediaPlayer.getDuration();
                p = pos * 100 / max;
            }
        }catch (Exception e){
            MyLog.e("201805101421",e.toString(),e);
        }
        return p;
    }

    //获取总长度
    public int getDuration(int id) {
        try {
            MediaPlayer mediaPlayer = maps.get(id);
            if (mediaPlayer != null) {
                return mediaPlayer.getDuration();
            }
        } catch (Exception e) {
            MyLog.e("201808180951", e.toString(), e);
        }
        return 0;
    }

    //删除文件
    private static void deleteFile(File file) {
        try {
            if (file != null && file.exists()) {
                if (file.isFile()) {
                    boolean ddl = file.delete();
                    if (!ddl){
                        if(!file.delete())
                            MyLog.d("show",file.getAbsolutePath()+" DeleteFile fail");
                    }
                } else if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File it : files) {
                        deleteFile(it);
                    }
                }
            }
        } catch (Exception e) {
            MyLog.e("201804261005",e.toString(),e);
        }
    }
}
