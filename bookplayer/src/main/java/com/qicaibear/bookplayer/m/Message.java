package com.qicaibear.bookplayer.m;

public class Message {
    private String who;
    private String actionGrounpName;
    private Long time;

    public Message(String who, String actionGrounpName, Long time) {
        this.who = who;
        this.actionGrounpName = actionGrounpName;
        this.time = time;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getActionGrounpName() {
        return actionGrounpName;
    }

    public void setActionGrounpName(String actionGrounpName) {
        this.actionGrounpName = actionGrounpName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
