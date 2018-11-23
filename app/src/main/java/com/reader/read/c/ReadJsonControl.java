package com.reader.read.c;

import com.alibaba.fastjson.JSON;
import com.qicaibear.bookplayer.c.FileController;
import com.qicaibear.bookplayer.m.server.AnimatorBooksDto;
import com.qicaibear.bookplayer.m.server.AnimatorResponseDto;
import com.yyx.commonsdk.thread.ThreadPool;


/**
 * 阅读前 下载json 存储方式和位置
 */
public class ReadJsonControl {

    public void writeJson(int bookId, final AnimatorBooksDto animatorBooksDto) {
        final String bookid = String.valueOf(bookId);

        ThreadPool.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                FileController controller = new FileController();
                //整本书json
                String json = JSON.toJSONString(animatorBooksDto);
                controller.writeBookContent(bookid, json);
                //分页
                if (animatorBooksDto.getAnimatorResponseDtoList() != null) {
                    for (AnimatorResponseDto it : animatorBooksDto.getAnimatorResponseDtoList()) {
                        String pageJson = JSON.toJSONString(it);
                        controller.writePageContent(bookid, String.valueOf(it.getBookDetailId()), pageJson);
                    }
                }
                //页码顺序
                if (animatorBooksDto.getPbBooksDetailList() != null) {
                    String bookdetailJson = JSON.toJSONString(animatorBooksDto.getPbBooksDetailList());
                    controller.writePageNoList(bookid, bookdetailJson);
                }
                //书籍信息
                if (animatorBooksDto.getPbBooks() != null) {
                    String bookinfoJson = JSON.toJSONString(animatorBooksDto.getPbBooks());
                    controller.writeBookInfo(bookid, bookinfoJson);
                }
            }
        });
    }
}