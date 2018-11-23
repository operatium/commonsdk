package com.qicaixiong.reader.resource.c;

import android.graphics.Point;
import android.graphics.PointF;

import com.qicaixiong.reader.m.inputmodel.BooksDetailBean;
import com.qicaixiong.reader.m.page.data.NewAnPageModel;
import com.qicaixiong.reader.m.show.AnClickModel;
import com.qicaixiong.reader.m.show.AnLocationUrlModel;
import com.qicaixiong.reader.m.show.AnPageModel;
import com.qicaixiong.reader.m.show.AnTextModel;
import com.yyx.commonsdk.log.MyLog;

import java.util.ArrayList;

/**
 * 网络数据模型 对接 本地书籍数据模型的管道
 *
 * 1. BooksDetailBean 是网络请求的直接获取到的对象
 * 2. BookSummary 是具体某本书通过拆分BooksDetailBean 生成的 书籍的概述
 * 3.
 * Created by admin on 2018/8/31.
 */

public class DataPipeline {
//
//    //将网络数据 拆分 成 总纲（自定义的本地model形式） + 每一页（原装网络json数据）
//    public static BookSummary splitNetData(BooksDetailBean bean, String Etag,int length) {
//        try {
//
//            if (bean.getStatus() != 0 || bean.getData() == null || bean.getData().isEmpty()) {
//                return null;
//            }
//            BookSummary bookSummary = new BookSummary();
//
//            int bookid = bean.getData().get(0).getBookId();
//            String bookID = String.valueOf(bookid);
//
//            //概述
//            bookSummary.setBookID(bookid);
//            bookSummary.setEtag(Etag);
//            bookSummary.setContentlenth(length);
//            bookSummary.setPageCount(bean.getData().size());
//            //todo 后期加字段
//            bookSummary.setName("---");
//
//            ArrayList<String> pages = new ArrayList<>();
//            for (BooksDetailBean.DataBean it : bean.getData()) {
//
//                String pageID = String.valueOf(it.getId());
//
//                pages.add(pageID);
//                //拆分页
//                FileController.writeAnPageJSON(bookID, pageID, JSON.toJSONString(it));
//            }
//            bookSummary.setPages(pages);
//            FileController.writeAnBookSummary(bookID, JSON.toJSONString(bookSummary));
//
//            return bookSummary;
//        } catch (Exception e) {
//            MyLog.e("201806251056", e.toString(), e);
//            return null;
//        }
//    }
//
//    //从本地读到内存中
//    public static AnPageModel tranformPage(String bookID,String pageID,CoordinatesConversion conversion){
//        String json = FileController.readAnPageJSON(bookID,pageID);
//        if (TextUtils.isEmpty(json))
//            return new AnPageModel();
//        AnPageModel pageModel = new AnPageModel();
//        try{
//            BooksDetailBean.DataBean page = JSON.parseObject(json,BooksDetailBean.DataBean.class);
//            //pagecode
//            pageModel.setPageNo(page.getPageNo());
//            pageModel.setPageId(page.getId());
//            //图片
//            pageModel.setPicture(AnLocationUrlModel.getDownloadedObject(page.getUrl(),page.getBookId(),".png"));
//            if (page.getSentence() != null) {
//                //整段语音url
//                pageModel.setVoice(AnLocationUrlModel.getDownloadedObject(page.getSentence().getUrl(), page.getBookId(), ".mp3"));
//                //整段语音的时长
//                pageModel.setVoiceTime(page.getSentence().getTime());
//            }
//            //重播按钮
//            pageModel.setReplay(conversion.getLocationPoint(new PointF(page.getRepeatX(), page.getRepeatY())));
//            pageModel.setReplaySize(new Point(conversion.getSceenFitFactory().getSizeInt(60)
//                    ,conversion.getSceenFitFactory().getSizeInt(60)));
//            //矩形点击区域
//            ArrayList<AnClickModel> clickModels = new ArrayList<>();
//            for (BooksDetailBean.DataBean.RectangleListBean rect : page.getRectangleList()) {
//                AnClickModel clickModel = new AnClickModel(rect.getWord()
//                        , conversion.getLocationPoint(new PointF(rect.getLeftTopX(), rect.getLeftTopY()))
//                        , conversion.getLocationPoint(new PointF(rect.getRightBottomX(), rect.getRightBottomY()))
//                        , AnLocationUrlModel.getDownloadedObject(rect.getAudioUrl(),page.getBookId(),".mp3"));
//                clickModels.add(clickModel);
//            }
//            pageModel.setClickModels(clickModels);
//            //文本
//            ArrayList<AnTextModel> textModels = new ArrayList<>();
//            for (BooksDetailBean.DataBean.WordListBean it : page.getWordList()) {
//                AnTextModel textModel = new AnTextModel();
//                textModel.setWord(it.getWord());
//                PointF position = conversion.getLocationPoint(new PointF(it.getPositionX(),it.getPositionY()));
//                textModel.setPositionX(Float.valueOf(position.x).intValue());
//                textModel.setPositionY(Float.valueOf(position.y).intValue());
//                int fontsize = it.getFontSize();
//                textModel.setFontSize(Float.valueOf(conversion.getLocationWidth(fontsize)).intValue());
//                textModel.setFontColor(it.getFontColor());
//                textModel.setFontBold(it.getFontBold());
//                textModel.setAudio(AnLocationUrlModel.getDownloadedObject(it.getAudioUrl(),page.getBookId(),".mp3"));
//                textModel.setPlayTime(it.getWordTime());
//                textModel.setStartTime(it.getStartTime());
//                textModel.setEndTime(it.getEndTime());
//                textModels.add(textModel);
//            }
//            pageModel.setTexts(textModels);
//        }catch (Exception e){
//            MyLog.e("201809251653",e.toString(),e);
//        }
//        return pageModel;
//    }
//
//    //生成这本书的全部URL对象 用于下载
//    public static void createAnLoactionURLs(BooksDetailBean bean) {
//        try{
//            if (bean != null && bean.getData() != null && !bean.getData().isEmpty()) {
//                //一页
//                for (BooksDetailBean.DataBean page : bean.getData()) {
//                    //图片
//                    AnLocationUrlModel.getObjectToDownload(page.getUrl(), page.getBookId(), ".png");
//                    if (page.getSentence() != null) {
//                        AnLocationUrlModel.getObjectToDownload(page.getSentence().getUrl(), page.getBookId(), ".mp3");
//                    }
//                    for (BooksDetailBean.DataBean.RectangleListBean rect : page.getRectangleList()) {
//                        AnLocationUrlModel.getObjectToDownload(rect.getAudioUrl(), page.getBookId(), ".mp3");
//                    }
//                }
//            }
//        }catch (Exception e){
//            MyLog.e("21809040924",e.toString(),e);
//        }
//    }

    //网络数据转换成 本地视图数据
    public static AnPageModel netDataTranformPageModel(BooksDetailBean.DataBean page) {
        AnPageModel pageModel = new AnPageModel();
        try {
            //pagecode
            pageModel.setPageNo(page.getPageNo());
            pageModel.setPageId(page.getId());
            //图片
            pageModel.setPicture(AnLocationUrlModel.getObjectToDownload(page.getUrl(), page.getBookId(), ".png"));
            if (page.getSentence() != null) {
                //整段语音url
                pageModel.setVoice(AnLocationUrlModel.getObjectToDownload(page.getSentence().getUrl(), page.getBookId(), ".mp3"));
                //整段语音的时长
                pageModel.setVoiceTime(page.getSentence().getTime());
            }
            //重播按钮
            pageModel.setReplay(new PointF(page.getRepeatX(), page.getRepeatY()));
            pageModel.setReplaySize(new Point(60, 60));
            //矩形点击区域
            ArrayList<AnClickModel> clickModels = new ArrayList<>();
            for (BooksDetailBean.DataBean.RectangleListBean rect : page.getRectangleList()) {
                AnClickModel clickModel = new AnClickModel(rect.getWord()
                        , new PointF(rect.getLeftTopX(), rect.getLeftTopY())
                        , new PointF(rect.getRightBottomX(), rect.getRightBottomY())
                        , AnLocationUrlModel.getObjectToDownload(rect.getAudioUrl(), page.getBookId(), ".mp3"));
                clickModels.add(clickModel);
            }
            pageModel.setClickModels(clickModels);
            //文本
            ArrayList<AnTextModel> textModels = new ArrayList<>();
            for (BooksDetailBean.DataBean.WordListBean it : page.getWordList()) {
                AnTextModel textModel = new AnTextModel();
                textModel.setWord(it.getWord());
                PointF position = new PointF(it.getPositionX(), it.getPositionY());
                textModel.setPositionX(Float.valueOf(position.x).intValue());
                textModel.setPositionY(Float.valueOf(position.y).intValue());
                int fontsize = it.getFontSize();
                textModel.setFontSize(Float.valueOf(fontsize).intValue());
                textModel.setFontColor(it.getFontColor());
                textModel.setFontBold(it.getFontBold());
                textModel.setAudio(AnLocationUrlModel.getObjectToDownload(it.getAudioUrl(), page.getBookId(), ".mp3"));
                textModel.setPlayTime(it.getWordTime());
                textModel.setStartTime(it.getStartTime());
                textModel.setEndTime(it.getEndTime());
                textModels.add(textModel);
            }
            pageModel.setTexts(textModels);
        } catch (Exception e) {
            MyLog.e("201810031000", e.toString(), e);
        }
        return pageModel;
    }

    //坐标转换
    public static AnPageModel valueTranform(AnPageModel anPageModel, CoordinatesConversion conversion) {
        AnPageModel pageModel = new AnPageModel();
        try {
            //pagecode
            pageModel.setPageNo(anPageModel.getPageNo());
            pageModel.setPageId(anPageModel.getPageId());
            //图片
            pageModel.setPicture(anPageModel.getPicture());

            //整段语音url
            pageModel.setVoice(anPageModel.getVoice());
            //整段语音的时长
            pageModel.setVoiceTime(anPageModel.getVoiceTime());

            //重播按钮
            pageModel.setReplay(conversion.getLocationPoint(anPageModel.getReplay()));
            int x = conversion.getLocationWidth(anPageModel.getReplaySize().x);
            int y = conversion.getLocationWidth(anPageModel.getReplaySize().y);
            pageModel.setReplaySize(new Point(x,y));
            //矩形点击区域
            ArrayList<AnClickModel> clickModels = new ArrayList<>();
            for (AnClickModel rect : anPageModel.getClickModels()) {
                AnClickModel clickModel = new AnClickModel(rect.getWord()
                        , conversion.getLocationPoint(rect.getStartPoint())
                        , conversion.getLocationPoint(rect.getEndPoint())
                        , rect.getSound());
                clickModels.add(clickModel);
            }
            pageModel.setClickModels(clickModels);
            //文本
            ArrayList<AnTextModel> textModels = new ArrayList<>();
            for (AnTextModel it : anPageModel.getTexts()) {
                AnTextModel textModel = new AnTextModel();
                textModel.setWord(it.getWord());
                Point p = conversion.getLocationPoint(new Point(it.getPositionX(),it.getPositionY()));
                textModel.setPositionX(p.x);
                textModel.setPositionY(p.y);
                int fontsize = it.getFontSize();
                textModel.setFontSize(Float.valueOf(conversion.getLocationWidth(fontsize)).intValue());
                textModel.setFontColor(it.getFontColor());
                textModel.setFontBold(it.getFontBold());
                textModel.setAudio(it.getAudio());
                textModel.setPlayTime(it.getPlayTime());
                textModel.setStartTime(it.getStartTime());
                textModel.setEndTime(it.getEndTime());
                textModels.add(textModel);
            }
            pageModel.setTexts(textModels);
        } catch (Exception e) {
            MyLog.e("201810032039", e.toString(), e);
        }
        return pageModel;
    }

    public static NewAnPageModel stringTranformModel(String pageJson){
        return new NewAnPageModel();
    }
}