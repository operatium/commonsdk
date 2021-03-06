package com.qicaibear.bookplayer.m.client;

public class Event {

    private String myName;
    private String nodeName;
    private String actionGroupName;
    private int startDelay;
    private String tpye;

    public Event(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getActionGroupName() {
        return actionGroupName;
    }

    public void setActionGroupName(String actionGroupName) {
        this.actionGroupName = actionGroupName;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(int startDelay) {
        this.startDelay = startDelay;
    }

    public String getTpye() {
        return tpye;
    }

    public void setTpye(String tpye) {
        this.tpye = tpye;
    }

    @Override
    public String toString() {
        return "Event{" +
                "myName='" + myName + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", actionGroupName='" + actionGroupName + '\'' +
                ", startDelay=" + startDelay +
                ", tpye='" + tpye + '\'' +
                '}';
    }
}
