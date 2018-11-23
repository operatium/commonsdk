package com.qicaixiong.reader.m.inputmodel;

import com.qicaixiong.reader.m.http.NetBean;

import java.util.List;

/**
 * Created by admin on 2018/8/31.
 */

public class BooksDetailBean extends NetBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bookId : 4
         * id : 12
         * pageNo : 1
         * rectangleList : [{"audioUrl":"2018-08-30/73fd0ab42789460681736771a84847fe.mp3","layer":2,"leftTopX":2,"leftTopY":2,"rightBottomX":3,"rightBottomY":3,"word":"dog"}]
         * repeatX : 11
         * repeatY : 111
         * sentence : {"time":3000,"url":"2018-08-31/a21e4a6eaaf741348428d2147a1cffda.mp3"}
         * url : 2018-09-01/2b993256c9ff4526894f4cce77f1002d.jpg
         * wordList : [{"audioUrl":"2018-08-30/73fd0ab42789460681736771a84847fe.mp3","endTime":1122222,"fontBold":1,"fontColor":"red","fontSize":1,"positionX":11,"positionY":11,"startTime":11,"word":"dog","wordTime":11}]
         */

        private int bookId;
        private int id;
        private int pageNo;
        private int repeatX;
        private int repeatY;
        private SentenceBean sentence;
        private String url;
        private List<RectangleListBean> rectangleList;
        private List<WordListBean> wordList;

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getRepeatX() {
            return repeatX;
        }

        public void setRepeatX(int repeatX) {
            this.repeatX = repeatX;
        }

        public int getRepeatY() {
            return repeatY;
        }

        public void setRepeatY(int repeatY) {
            this.repeatY = repeatY;
        }

        public SentenceBean getSentence() {
            return sentence;
        }

        public void setSentence(SentenceBean sentence) {
            this.sentence = sentence;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<RectangleListBean> getRectangleList() {
            return rectangleList;
        }

        public void setRectangleList(List<RectangleListBean> rectangleList) {
            this.rectangleList = rectangleList;
        }

        public List<WordListBean> getWordList() {
            return wordList;
        }

        public void setWordList(List<WordListBean> wordList) {
            this.wordList = wordList;
        }

        public static class SentenceBean {
            /**
             * time : 3000
             * url : 2018-08-31/a21e4a6eaaf741348428d2147a1cffda.mp3
             */

            private int time;
            private String url;

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class RectangleListBean {
            /**
             * audioUrl : 2018-08-30/73fd0ab42789460681736771a84847fe.mp3
             * layer : 2
             * leftTopX : 2
             * leftTopY : 2
             * rightBottomX : 3
             * rightBottomY : 3
             * word : dog
             */

            private String audioUrl;
            private int layer;
            private int leftTopX;
            private int leftTopY;
            private int rightBottomX;
            private int rightBottomY;
            private String word;

            public String getAudioUrl() {
                return audioUrl;
            }

            public void setAudioUrl(String audioUrl) {
                this.audioUrl = audioUrl;
            }

            public int getLayer() {
                return layer;
            }

            public void setLayer(int layer) {
                this.layer = layer;
            }

            public int getLeftTopX() {
                return leftTopX;
            }

            public void setLeftTopX(int leftTopX) {
                this.leftTopX = leftTopX;
            }

            public int getLeftTopY() {
                return leftTopY;
            }

            public void setLeftTopY(int leftTopY) {
                this.leftTopY = leftTopY;
            }

            public int getRightBottomX() {
                return rightBottomX;
            }

            public void setRightBottomX(int rightBottomX) {
                this.rightBottomX = rightBottomX;
            }

            public int getRightBottomY() {
                return rightBottomY;
            }

            public void setRightBottomY(int rightBottomY) {
                this.rightBottomY = rightBottomY;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class WordListBean {
            /**
             * audioUrl : 2018-08-30/73fd0ab42789460681736771a84847fe.mp3
             * endTime : 1122222
             * fontBold : 1
             * fontColor : red
             * fontSize : 1
             * positionX : 11
             * positionY : 11
             * startTime : 11
             * word : dog
             * wordTime : 11
             */

            private String audioUrl;
            private int endTime;
            private int fontBold;
            private String fontColor;
            private int fontSize;
            private int positionX;
            private int positionY;
            private int startTime;
            private String word;
            private int wordTime;

            public String getAudioUrl() {
                return audioUrl;
            }

            public void setAudioUrl(String audioUrl) {
                this.audioUrl = audioUrl;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public int getFontBold() {
                return fontBold;
            }

            public void setFontBold(int fontBold) {
                this.fontBold = fontBold;
            }

            public String getFontColor() {
                return fontColor;
            }

            public void setFontColor(String fontColor) {
                this.fontColor = fontColor;
            }

            public int getFontSize() {
                return fontSize;
            }

            public void setFontSize(int fontSize) {
                this.fontSize = fontSize;
            }

            public int getPositionX() {
                return positionX;
            }

            public void setPositionX(int positionX) {
                this.positionX = positionX;
            }

            public int getPositionY() {
                return positionY;
            }

            public void setPositionY(int positionY) {
                this.positionY = positionY;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public int getWordTime() {
                return wordTime;
            }

            public void setWordTime(int wordTime) {
                this.wordTime = wordTime;
            }
        }
    }
}
