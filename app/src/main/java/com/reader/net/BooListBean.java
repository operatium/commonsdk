package com.reader.net;

import java.util.List;

public class BooListBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cover : 2018-10-18/c8e0e643e4554da9b4159aaf399a3aa9.jpg
         * description : Frank the Rat
         * difficulty : D
         * id : 27
         * isFavorite : 0
         * name : Frank the Rat
         * producer : Oberyn
         * publishTime : 2018-10-18
         * relationWords : frank,rat,bag,hat.pan.apple,bat,cat
         * wordsCount : 10
         */

        private String cover;
        private String description;
        private String difficulty;
        private int id;
        private int isFavorite;
        private String name;
        private String producer;
        private String publishTime;
        private String relationWords;
        private int wordsCount;
        private int type;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(int isFavorite) {
            this.isFavorite = isFavorite;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProducer() {
            return producer;
        }

        public void setProducer(String producer) {
            this.producer = producer;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getRelationWords() {
            return relationWords;
        }

        public void setRelationWords(String relationWords) {
            this.relationWords = relationWords;
        }

        public int getWordsCount() {
            return wordsCount;
        }

        public void setWordsCount(int wordsCount) {
            this.wordsCount = wordsCount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
