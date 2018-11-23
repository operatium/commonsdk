package com.reader.edit.viewholder;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.reader.R;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;
import com.yyx.commonsdk.screenadaptation.SceenFitFactory;

public class PageViewHolder extends RecyclerView.ViewHolder {
    private int pageID;
    private int pageNo;

    public PageViewHolder(View itemView) {
        super(itemView);
    }

    public static PageViewHolder createItem(Context context, SceenFitFactory fitFactory) {
        View view = View.inflate(context, R.layout.edit_view_pageitem, null);
        ConstraintLayout layout = view.findViewById(R.id.root18);
        new LayoutParamsHelp<>(layout).with(fitFactory).creatLayoutParams(-1, 50)
                .apply();

        return new PageViewHolder(view);
    }

    public void bind(int pageID, int pageNo) {
        this.pageID = pageID;
        this.pageNo = pageNo;
        TextView tv = itemView.findViewById(R.id.text18);
        tv.setText("pageID = " + pageID + ", pageNo = " + pageNo);
    }
}