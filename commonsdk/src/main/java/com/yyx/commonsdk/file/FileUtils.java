package com.yyx.commonsdk.file;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.yyx.commonsdk.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by java on 2017/6/29.
 */

public class FileUtils {
    public static int version = 0;
    
    /**
     * 获取指定文件夹下面的所有文件，并组成一个List返回
     */
    public static List<File> getFileList(String dirPath) {
        List<File> fileList = new ArrayList<>();
        File[] files = getAllFilesWithDir(dirPath);
        if (files != null) {
            for (File file : files) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 获取指定文件夹下面的所有文件
     */
    private static File[] getAllFilesWithDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            return null;
        }
        File[] files = dir.listFiles();
        return files;
    }

    public static boolean createDir(String dir){
        boolean result = false;
        try{
            File f = new File(dir);
            if (!f.exists())
                result = f.mkdirs();
            else
                result = true;
        }catch (Exception e){
            MyLog.e("201706281106",e.toString(),e);
        }
        return result;
    }

    //缓存
    public static DiskLruCache getCache(Context context, String dir)
    {
        DiskLruCache cache = null;
        if (context == null)
            return null;
        try {
            String dirname = "yyxcache";
            if (dir!= null && !dir.isEmpty())
                dirname = dir;
            cache = DiskLruCache.open(new File(context.getFilesDir(), dirname), 1, 1, 10 * 1024 * 1024);
        }catch (Exception e)
        {
            MyLog.e("201706131338",e.toString(),e);
        }
        return cache;
    }
    //dir是相对路径
    public static void writeCache(Context context,String dir,String key, String str)
    {
        try{
            if (str == null || context == null)
                return;
            DiskLruCache diskLruCache = getCache(context,dir);
            if (diskLruCache!=null)
            {
                key = key.toLowerCase();
                DiskLruCache.Editor edit= diskLruCache.edit(key);
                OutputStream outputStream = edit.newOutputStream(version);
                outputStream.write(str.getBytes());
                outputStream.close();
                edit.commit();
                diskLruCache.flush();
                diskLruCache.close();
            }
            else
            {
                Log.i("201706131347","(缓存失败)"+"key = "+key);
            }
        }catch (Exception e)
        {
            MyLog.e("201706131346",e.toString(),e);
        }
    }

    //dir是相对路径
    public static String readCache(Context context,String dir,String key)
    {
        String str="";
        try{
            DiskLruCache diskLruCache = getCache(context,dir);
            if (diskLruCache!=null)
            {
                key = key.toLowerCase();
                DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                if (snapshot != null) {
                    InputStream inputStream = snapshot.getInputStream(version);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i;
                    while ((i = inputStream.read()) != -1) {
                        baos.write(i);
                    }
                    str = baos.toString();
                    inputStream.close();
                    baos.close();
                }
                diskLruCache.flush();
                diskLruCache.close();
            }
            else
            {
                Log.i("201706131410","(读取缓存失败)"+"key = "+key);
            }
        }catch (Exception e)
        {
            MyLog.e("201706131411",e.toString(),e);
        }
        return str;
    }

    //绝对路径 缓存
    public static DiskLruCache getCacheAbsolute( String dir) {
        DiskLruCache cache = null;
        try {
            cache = DiskLruCache.open(new File(dir), 1, 1, 10 * 1024 * 1024);
        } catch (Exception e) {
            MyLog.e("201804251356",e.toString(),e);
        }
        return cache;
    }

    //dir是绝对路径
    public static void writeCacheAbsolute(String dir,String key, String str)
    {
        try{
            if (str == null )
                return;
            DiskLruCache diskLruCache = getCacheAbsolute(dir);
            if (diskLruCache!=null)
            {
                key = key.toLowerCase();
                DiskLruCache.Editor edit= diskLruCache.edit(key);
                OutputStream outputStream = edit.newOutputStream(version);
                outputStream.write(str.getBytes());
                outputStream.close();
                edit.commit();
                diskLruCache.flush();
                diskLruCache.close();
            }
            else
            {
                Log.i("201804251350","(缓存失败)"+"key = "+key);
            }
        }catch (Exception e)
        {
            MyLog.e("201804251351",e.toString(),e);
        }
    }

    //dir是绝对路径
    public static String readCacheAbsolute(String dir,String key)
    {
        String str="";
        try{
            DiskLruCache diskLruCache = getCacheAbsolute(dir);
            if (diskLruCache!=null)
            {
                key = key.toLowerCase();
                DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                if (snapshot != null) {
                    InputStream inputStream = snapshot.getInputStream(version);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i;
                    while ((i = inputStream.read()) != -1) {
                        baos.write(i);
                    }
                    str = baos.toString();
                    inputStream.close();
                    baos.close();
                }
                diskLruCache.flush();
                diskLruCache.close();
            }
            else
            {
                Log.i("201804251544","(读取缓存失败)"+"key = "+key);
            }
        }catch (Exception e)
        {
            MyLog.e("201804251543",e.toString(),e);
        }
        return str;
    }

    //Hashmap写入文件
    public static void writeHashmap(Context context,HashMap<Integer,Long> map,String dir, String filename){
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<Integer,Long> it : map.entrySet()){
            try {
                jsonObject.put(it.getKey().toString(),it.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        writeCache(context,dir,filename,jsonObject.toString());
    }

    //文件输入Hashmap
    public static HashMap<Integer,Long> readHashmap(Context context,String dir, String filename){
        HashMap<Integer,Long> data = new HashMap<>();
        try {
            String js = readCache(context, dir, filename);
            JSONObject jsonObject = null;
            try {
                if (!js.isEmpty())
                    jsonObject = new JSONObject(js);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObject != null) {
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String Key = iterator.next();
                    Long value = 0L;
                    try {
                        value = jsonObject.getLong(Key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    data.put(Integer.valueOf(Key), value);
                }
            }
        }catch (Exception e){
            MyLog.e("201708260949",e.toString(),e);
        }
        return data;
    }

    /**
     * 获取文件夹大小
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList == null)
                return 0;
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            MyLog.e("201802060019",e.toString(),e);
        }
        return size;
    }

    /**
     * 获取文件夹大小
     */
    public static long getFolderSize(File file, Boolean isreadsize) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList == null)
                return 0;
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    if (isreadsize){
                        String unzippath = fileList[i].getAbsolutePath();
                        String sizestr = FileUtils.readCacheAbsolute(unzippath,"size");
                        if (sizestr.isEmpty()){
                            size = size + getFolderSize(fileList[i],isreadsize);
                        }else{
                            size += Long.valueOf(sizestr);
                            MyLog.d("show",unzippath+" read size of = "+sizestr);
                        }
                    }else
                        size = size + getFolderSize(fileList[i],isreadsize);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            MyLog.e("201802060019",e.toString(),e);
        }
        return size;
    }

    //删除文件 一级目录
    public static void deleteFileParent(File file) {
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
                        deleteFileChild(it);
                    }
                }
            }
        } catch (Exception e) {
            MyLog.e("201707111513",e.toString(),e);
        }
    }

    //删除文件 二级目录
    public static void deleteFileChild(File file) {
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
                        deleteFile(it,false);
                    }
                }
            }
        } catch (Exception e) {
            MyLog.e("201804261005",e.toString(),e);
        }
    }

    //删除文件 三级目录
    public static void deleteFile(File file, boolean isDelDir) {
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
                        deleteFile(it,true);
                    }
                    if (isDelDir){
                        if(!file.delete())
                            MyLog.d("show",file.getAbsolutePath()+" delete is fail");
                    }
                }
            }
        } catch (Exception e) {
            MyLog.e("201804261005",e.toString(),e);
        }
    }
}
