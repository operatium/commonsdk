package com.qicaibear.bookplayer.m.client;

/**
 * Created by admin on 2018/10/29.
 */

public class OrderAction {
    private String actionName;
    private int order;

    public OrderAction(String actionName, int order) {
        this.actionName = actionName;
        this.order = order;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
