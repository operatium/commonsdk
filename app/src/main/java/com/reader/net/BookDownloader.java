package com.reader.net;

import android.support.annotation.NonNull;

import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;
import com.qicaibear.bookplayer.c.FileController;
import com.qicaibear.bookplayer.m.client.ResouceData;
import com.qicaibear.bookplayer.m.server.AnimatorBooksDto;
import com.qicaibear.bookplayer.m.server.PbResource;
import com.reader.read.c.ReadJsonControl;
import com.yyx.commonsdk.log.MyLog;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;

import static com.reader.net.TagUtil.*;

public class BookDownloader {
    private DownloadListaner listaner = new DownloadListenerAdapter();
    private DownloadContext downloadContext;
    private int currentCount;//当前下载文件数量
    private int totalCount;//文件总数

    //请求Json
    public void getBook(final int bookId) {
        final String bookid = String.valueOf(bookId);
        NetControl.getInstance().getBook(bookId, new Consumer<AnimatorBooksDto>() {
            @Override
            public void accept(final AnimatorBooksDto animatorBooksDto) throws Exception {
                final File parentDir = new FileController().getBookDir(bookid);

                new ReadJsonControl().writeJson(bookId, animatorBooksDto);

                if (!animatorBooksDto.getPbResourceList().isEmpty()) {
                    ArrayList<ResouceData> resouceList = new ArrayList<>();
                    for (PbResource it : animatorBooksDto.getPbResourceList()) {
                        ResouceData data = new ResouceData(it.getResourceName(), "http://files.qicaibear.com/" + it.getUrl());
                        resouceList.add(data);
                    }
                    downloadResouce(parentDir, resouceList);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                listaner.error(throwable.getMessage());
            }
        });
    }

    //下载资源
    public void downloadResouce(File parentPathFile, ArrayList<ResouceData> resouce) {
        if (resouce == null)
            return;
        currentCount = 0;
        totalCount = resouce.size();
        final DownloadContext.Builder builder = new DownloadContext.QueueSet()
                .setParentPathFile(parentPathFile)
                .setMinIntervalMillisCallbackProcess(300)
                .commit();
        for (ResouceData data : resouce) {
            builder.bind(data.getUrl())
                    .addTag(KEY_TASK_NAME, data.getName());
        }
        downloadContext = builder.setListener(new DownloadContextListener() {
            @Override
            public void taskEnd(@NonNull DownloadContext context,
                                @NonNull DownloadTask task,
                                @NonNull EndCause cause,
                                @Nullable Exception realCause,
                                int remainCount) {
            }

            @Override
            public void queueEnd(@NonNull DownloadContext context) {
                listaner.success();
            }
        }).build();
        downloadContext.start(createListener(), false);
    }

    private DownloadListener1 createListener() {
        return new DownloadListener1() {

            @Override
            public void taskStart(@NonNull DownloadTask task,
                                  @NonNull Listener1Assist.Listener1Model model) {
            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset,
                                  long totalLength) {
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause,
                                @android.support.annotation.Nullable Exception realCause,
                                @NonNull Listener1Assist.Listener1Model model) {
                currentCount += 1;
                listaner.progress(currentCount, totalCount);
                MyLog.d("show", task.getFilename() + " => " + currentCount + " / " + totalCount);
            }
        };
    }

    public DownloadListaner getListaner() {
        return listaner;
    }

    public void setListaner(DownloadListaner listaner) {
        if (listaner == null)
            listaner = new DownloadListenerAdapter();
        this.listaner = listaner;
    }

    public DownloadContext getDownloadContext() {
        return downloadContext;
    }

    public void setDownloadContext(DownloadContext downloadContext) {
        this.downloadContext = downloadContext;
    }

    public static interface DownloadListaner {
        void success();

        void error(String error);

        void progress(int currentCount, int totalCount);
    }

    public static class DownloadListenerAdapter implements DownloadListaner {
        @Override
        public void success() {
        }

        @Override
        public void error(String error) {
        }

        @Override
        public void progress(int currentCount, int totalCount) {
        }
    }
}