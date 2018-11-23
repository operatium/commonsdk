package com.reader.edit.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.reader.R;
import com.reader.edit.m.LayoutListItem;

public class LayoutViewHolder extends RecyclerView.ViewHolder {
    private TextView name, type;

    public static LayoutViewHolder create(Context context) {
        View view = View.inflate(context, R.layout.edit_view_layoutitem, null);
        return new LayoutViewHolder(view);
    }

    public LayoutViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name22);
        type = itemView.findViewById(R.id.type22);
    }

    public void bind(LayoutListItem item) {
        String str = "";
        for (int i = 0 ;i <item.getGrade();i++){
            str += "+";
        }
        str += item.getName();
        name.setText(str);
        type.setText("("+item.getType()+")");
    }
}