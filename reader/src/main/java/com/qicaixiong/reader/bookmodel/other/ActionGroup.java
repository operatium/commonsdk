package com.qicaixiong.reader.bookmodel.other;

import com.qicaixiong.reader.bookmodel.animator.OrderAction;

import java.util.ArrayList;

public class ActionGroup {
    private String myName;
    private ArrayList<OrderAction> actions;

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public ArrayList<OrderAction> getActions() {
        return actions;
    }

    public void setActions(ArrayList<OrderAction> actions) {
        this.actions = actions;
    }
}
