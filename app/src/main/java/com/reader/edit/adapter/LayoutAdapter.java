package com.reader.edit.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qicaibear.bookplayer.m.client.WidgetValue;
import com.reader.edit.m.EditMessage;
import com.reader.edit.m.LayoutListItem;
import com.reader.edit.viewholder.LayoutViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class LayoutAdapter extends RecyclerView.Adapter<LayoutViewHolder> {
    private ArrayList<LayoutListItem> myData = new ArrayList<>();
    @NonNull
    @Override
    public LayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return LayoutViewHolder.create(parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull LayoutViewHolder holder, int position) {
        holder.bind(myData.get(position));
        final String name = myData.get(position).getName();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditMessage msg = new EditMessage("selectNode");
                msg.setValue(name);
                EventBus.getDefault().post(msg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void reLoad(WidgetValue value){
        if (value != null){
            myData.clear();
            parse(value, myData, 0);
        }
    }

    private void parse(WidgetValue value,ArrayList<LayoutListItem> myData,int parentGrade) {
        int myGrade = parentGrade + 1;
        LayoutListItem item = new LayoutListItem(value.getMyName(), value.getViewType());
        item.setGrade(myGrade);
        if (value.getChildren() != null) {
            for (WidgetValue it : value.getChildren()) {
                parse(it, myData, myGrade);
            }
        }
        myData.add(item);
    }
}
