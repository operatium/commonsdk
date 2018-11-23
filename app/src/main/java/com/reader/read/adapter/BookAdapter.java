package com.reader.read.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.reader.net.BooListBean;
import com.reader.read.m.BookViewHolderData;
import com.reader.read.view.BookViewHolder;
import com.yyx.commonsdk.screenadaptation.SceenFitFactory;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    protected ArrayList<BookViewHolderData> urls = new ArrayList<>();
    private SceenFitFactory fitFactory;

    public BookAdapter(SceenFitFactory fitFactory) {
        this.fitFactory = fitFactory;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BookViewHolder.createViewHolder(parent.getContext(),fitFactory);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(urls.get(position));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void reLoad(List<BooListBean.DataBean> dataBeans){
        for (BooListBean.DataBean it :dataBeans){
            urls.add(new BookViewHolderData(it.getId(),"http://imgs.hellokid.com/" + it.getCover(), it.getType(),it.getName()));
        }
        notifyDataSetChanged();
    }
}
