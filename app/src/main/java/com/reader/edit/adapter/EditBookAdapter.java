package com.reader.edit.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.qicaibear.bookplayer.m.server.PbBooks;
import com.reader.Rout;
import com.reader.edit.c.EditFileControl;
import com.reader.net.NetApi;
import com.reader.read.m.BookViewHolderData;
import com.reader.read.view.BookViewHolder;
import com.yyx.commonsdk.file.FileUtils;
import com.yyx.commonsdk.screenadaptation.SceenFitFactory;

import java.io.File;
import java.util.ArrayList;

public class EditBookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    protected ArrayList<BookViewHolderData> urls = new ArrayList<>();
    private SceenFitFactory fitFactory;

    public EditBookAdapter(SceenFitFactory fitFactory) {
        this.fitFactory = fitFactory;
    }

    @NonNull
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BookViewHolder.createViewHolder(parent.getContext(), fitFactory);
    }

    public void onBindViewHolder(@NonNull BookViewHolder holder, final int position) {
        BookViewHolderData data = urls.get(position);

        holder.bind(data);

        final int bookid = data.getBookId();
        final String path = new EditFileControl().getBookDir(String.valueOf(bookid)).getAbsolutePath();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Rout().ToEditBookActivity(v.getContext(), path, bookid);
            }
        });
    }

    public int getItemCount() {
        return urls.size();
    }


    public void reLoad(File path) {
        EditFileControl editFileControl = new EditFileControl();
        urls.clear();
        if (path.listFiles() != null) {
            for (File it : path.listFiles()) {
                if (it.isFile())
                    continue;
                int bookID = Integer.valueOf(it.getName());
                String cover = "";
                int type = 2;
                String bookName = "";

                String bookinfojson = FileUtils.readCacheAbsolute(path.getAbsolutePath(), editFileControl.relativePathBookInfo());
                if (!TextUtils.isEmpty(bookinfojson)) {
                    PbBooks pbBooks = JSON.parseObject(bookinfojson, PbBooks.class);
                    if (pbBooks != null) {
                        cover = NetApi.picIP + "/" + pbBooks.getCover();
                        type = pbBooks.getType();
                        bookName = pbBooks.getName();
                    }
                }
                if (TextUtils.isEmpty(bookName))
                    bookName +=bookID;
                urls.add(new BookViewHolderData(bookID, cover, type, bookName));
            }
        }
        notifyDataSetChanged();
    }
}
