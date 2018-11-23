package com.qicaibear.bookplayer.m.server;

public class PbNodeDto   {
    private Integer id;

    private Integer bookId;

    private Integer bookDetailId;

    private String nodeName;

    private Integer nodeType;           //控件类型 1 layout 2 imageview 3 seamrollingview

    private String nodeSide;

    private String point;

    private String nodeSize;

    private String resourceName;

    private float picAspectRadio;          //类型3宽高比

    private String direction;

    private Integer parentNodeId;

    private String parentNodeName;

    private Integer status;

    private Integer sort;

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

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeSide() {
        return nodeSide;
    }

    public void setNodeSide(String nodeSide) {
        this.nodeSide = nodeSide;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(String nodeSize) {
        this.nodeSize = nodeSize;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public float getPicAspectRadio() {
        return picAspectRadio;
    }

    public void setPicAspectRadio(float picAspectRadio) {
        this.picAspectRadio = picAspectRadio;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(Integer parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public String getParentNodeName() {
        return parentNodeName;
    }

    public void setParentNodeName(String parentNodeName) {
        this.parentNodeName = parentNodeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}