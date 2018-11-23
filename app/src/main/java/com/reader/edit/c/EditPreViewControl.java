package com.reader.edit.c;


import com.qicaibear.bookplayer.m.client.WidgetValue;
import com.reader.edit.m.EditWidgetValue;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 管理预览节点的效果
 */
public class EditPreViewControl {
    private EditWidgetValue editWidgetValue;
    private HashMap<String, ArrayList<String>> parentMap;//节点间关系
    private HashMap<String, EditWidgetValue> nodeValueMap;//节点数据

    public void reLoad(EditWidgetValue editWidgetValue) {
        this.editWidgetValue = editWidgetValue;
        ArrayList<WidgetValue> children = editWidgetValue.getChildren();
    }


}
