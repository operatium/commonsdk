package com.reader.edit.m;

public class EditMessage {
    private String name;
    private String value;
    private EditWidgetValue node;

    public EditMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EditWidgetValue getNode() {
        return node;
    }

    public void setNode(EditWidgetValue node) {
        this.node = node;
    }
}
