package com.qicaibear.bookplayer.m.server;


public class PbNodeGroupR   {
    private Integer id;

    private Integer bookId;

    private Integer bookDetailId;

    private String nodeGroupRName;

    private String nodeName;

    private String groupName;

    private Integer actionType;//（1-初始化，2-区域点击）

    private Integer delay;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBookDetailId() {
        return bookDetailId;
    }

    public void setBookDetailId(Integer bookDetailId) {
        this.bookDetailId = bookDetailId;
    }

    public String getNodeGroupRName() {
        return nodeGroupRName;
    }

    public void setNodeGroupRName(String nodeGroupRName) {
        this.nodeGroupRName = nodeGroupRName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}