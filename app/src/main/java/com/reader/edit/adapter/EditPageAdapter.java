package com.reader.edit.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qicaibear.bookplayer.m.server.PbBooksDetail;
import com.reader.Rout;
import com.reader.edit.viewholder.PageViewHolder;
import com.yyx.commonsdk.screenadaptation.SceenFitFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class EditPageAdapter extends RecyclerView.Adapter<PageViewHolder> {
    private SceenFitFactory fitFactory;
    private ArrayList<PbBooksDetail> myData = new ArrayList<>();
    private String path;

    public EditPageAdapter(SceenFitFactory fitFactory,String path) {
        this.fitFactory = fitFactory;
        this.path = path;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PageViewHolder.createItem(parent.getContext(), fitFactory);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        final PbBooksDetail item = myData.get(position);
        holder.bind(item.getId(),item.getPageNo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Rout().ToEditPageActivity(v.getContext(),path,item.getBookId(),item.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return myData.size();
    }

    public void reLoad(HashMap<Integer, PbBooksDetail> map){
        if (map != null){
            myData.clear();
            for (HashMap.Entry<Integer,PbBooksDetail> item : map.entrySet()){
                myData.add(item.getValue());
            }
        }
        Collections.sort(myData, new Comparator<PbBooksDetail>() {
            @Override
            public int compare(PbBooksDetail a, PbBooksDetail b) {
                return a.getPageNo() - b.getPageNo();
            }
        });
        notifyDataSetChanged();
    }
}
