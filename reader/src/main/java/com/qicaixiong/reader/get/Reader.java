package com.qicaixiong.reader.get;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;

import com.qicaixiong.reader.book.c.StrokeTextViewBuilder;
import com.qicaixiong.reader.m.book.event.GoToPageEvent;
import com.qicaixiong.reader.m.book.event.NextPageEvent;
import com.qicaixiong.reader.book.v.BookFragment;
import com.qicaixiong.reader.m.page.event.AutoPlayEvent;
import com.yyx.commonsdk.sound.SoundPlayManager;
import com.qicaixiong.reader.permission.PermissionManger;
import com.qicaixiong.reader.resource.c.Downloader;
import com.qicaixiong.reader.resource.http.QueueDownload;
import com.qicaixiong.reader.resource.http.QueueDownloadManager;
import com.qicaixiong.reader.m.show.AnLocationUrlModel;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yyx.commonsdk.thread.ThreadPool;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class Reader {

    //初始化下载
    public static void initDownload(Activity activity, final String ip,
                                    final int serverVersion, final String AuthToken, final InitDownloadCallback callback) {
        final Context context = activity.getApplicationContext();
        AndPermission.with(activity)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Downloader.getInstance().init(context, serverVersion, AuthToken, ip);
                        if (callback != null)
                            callback.permissionGranted();
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                if (callback != null)
                    callback.permissionDenied();
            }
        })
                .start();
    }

    //下载

    /**
     *
     * @param needRedownload 是否在有内存缓存的情况下，任然需要重新走一遍下载流程， （在录音模式下，录音是利用了书籍下载队列下载，所以这里不可复用，必须再下载一次，才能确保录音下载下来）
     * @param activity 用于权限判断
     * @param bookId
     * @param downloadCallback 用于下载的时候的回调
     * @param callback  用于权限的回调
     */
    public static void downloadBook(final boolean needRedownload, final Activity activity, final int bookId
            , final DownloadCallback downloadCallback
            , final InitDownloadCallback callback) {
        AndPermission.with(activity)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        ThreadPool.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                Downloader.getInstance().getBookDetail(needRedownload, bookId, downloadCallback);
                            }
                        });
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (callback != null)
                            callback.permissionDenied();
                    }
                })
                .start();
    }

    //取消下载并且拒绝加载播放
    public static void stopBook(int bookId, BookFragment bookFragment) {
        if (QueueDownloadManager.getInstance().bookIsRuning(bookId)) {
            QueueDownload queueDownload = QueueDownloadManager.getInstance().getTask(bookId);
            queueDownload.stop();
        }
        bookFragment.stop();
        endRead();
    }

    //启动阅读
    public static void startRead(PermissionManger permission
            , BookFragment bookFragment
            , int pageNum
            , ViewPager.OnPageChangeListener listener) {//翻页回调
        if (bookFragment != null) {
            bookFragment.play(permission, pageNum, listener);
        }
    }

    //暂停
    public static void pause(BookFragment bookFragment){
        bookFragment.pause();
        endRead();
    }

    //恢复 返回false  恢复失败重新play
    public static boolean resume(BookFragment bookFragment){
        return bookFragment.resume();
    }

    //阅读结束
    public static void endRead(){
        SoundPlayManager.getInstance().clear();
        StrokeTextViewBuilder.getInstance().clear();
    }

    //释放所有资源
    public static void clearAllData(){
        AnLocationUrlModel.destroyManager();
        SoundPlayManager.destroyInstance();
        StrokeTextViewBuilder.destroyInstance();
    }

    //添加最后一页
    /** readBookCallback==null  启动回调函数部分不会执行 影响自动播放 */
    public static void addListener(BookFragment bookFragment, ReadBookCallback readBookCallback, EndFragmentCallback callback){
        bookFragment.setEndFragmentCallback(callback);
        bookFragment.setReadCallback(readBookCallback);
    }

    //在阅读器内进行重新到第一页开始阅读
    public static void rePlay(){
        EventBus.getDefault().post(new GoToPageEvent(0));
    }

    //播放当前页的整段文本读音
    public static void playAllSound(int page){
        EventBus.getDefault().post(new AutoPlayEvent(page));
    }

    //跳页
    public static void goToPage(int page){
        EventBus.getDefault().post(new GoToPageEvent(page));
    }

    //下一页
    public static void nextPage(){
        EventBus.getDefault().post(new NextPageEvent());
    }

    //跳转最后一页
    public static void goToLastPage(){
        EventBus.getDefault().post(new GoToPageEvent(ReaderHelp.getInstance().getPageModels().size()-1));
    }

    //获取某一页的图片全url
    public static String getFullImageURL(int page){
        return Downloader.getInstance().picIP + ReaderHelp.getInstance().getPageModels().get(page).getPicture().getUrl();
    }
}