package com.yyx.commonsdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2018/8/27.
 */

public class ThreadPool {
    private static ExecutorService sing;
    private static ExecutorService cache;

    public static ExecutorService newSingleThreadExecutor() {
        if (sing == null)
            sing = Executors.newSingleThreadExecutor();
        return sing;
    }

    public static ExecutorService newCachedThreadPool() {
        if (cache == null)
            cache = Executors.newCachedThreadPool();
        return cache;
    }

}
