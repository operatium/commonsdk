package com.yyx.commonsdk.log;

import android.util.Log;

import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by admin on 2018/8/30.
 */

public class MyLog {
    public static boolean debug = true;

    public static void d(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
        BuglyLog.d(tag,msg);
    }

    public static void e(String tag, String msg, Exception e) {
        if (debug)
            Log.e(tag, msg, e);
        BuglyLog.e(tag, msg);
        CrashReport.postCatchedException(e);
    }
}