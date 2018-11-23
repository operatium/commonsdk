package com.reader.read.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qicaibear.bookplayer.c.FileController;
import com.qicaibear.bookplayer.c.PermissionManger;
import com.reader.R;
import com.reader.Rout;
import com.reader.net.BookDownloader;
import com.reader.read.m.BookViewHolderData;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yyx.commonsdk.app.GlideApp;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.screenadaptation.SceenFitFactory;

import java.util.List;

public class BookViewHolder extends RecyclerView.ViewHolder {

    public BookViewHolder(View itemView) {
        super(itemView);
    }

    public static BookViewHolder createViewHolder(Context context, SceenFitFactory fitFactory) {
        View view = View.inflate(context, R.layout.read_view_bookitem, null);
        ConstraintLayout root15 = view.findViewById(R.id.root15);
        TextView text = view.findViewById(R.id.text15);
        root15.setLayoutParams(new ViewGroup.LayoutParams(fitFactory.getSizeInt(200), fitFactory.getSizeInt(300)));

        fitFactory.textViewSets(text, 24, null, 0);
        text.setMaxWidth(fitFactory.getSizeInt(150));
        return new BookViewHolder(view);
    }

    public void bind(BookViewHolderData data) {
        if (data == null)
            return;
        final int id = data.getBookId();
        final int type = data.getType();
        String url = data.getBookUrl();
        final String name = data.getBookName();

        ImageView cover = itemView.findViewById(R.id.pic15);
        final TextView text = itemView.findViewById(R.id.text15);

        GlideApp.with(cover).load(url).placeholder(R.drawable.home_bg).error(R.drawable.home_bg).into(cover);
        text.setText(name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                if(AndPermission.hasPermissions(v.getContext(),Permission.Group.STORAGE)){
                    clickEvent(context,id,type,text);
                }else {
                    AndPermission.with(v.getContext()).runtime().permission(Permission.Group.STORAGE)
                            .onDenied(new Action<List<String>>() {
                                @Override
                                public void onAction(List<String> data) {
                                    Toast.makeText(context,"没有权限",Toast.LENGTH_SHORT).show();
                                }
                            }).onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            clickEvent(context,id,type,text);
                        }
                    }).start();
                }
            }
        });
    }

    private void clickEvent(final Context context,final int id,final int type, final TextView text){

        final String path = new FileController().getBookDir(String.valueOf(id)).getAbsolutePath();
        if (type == 2) {
            BookDownloader downloader = new BookDownloader();
            downloader.setListaner(new BookDownloader.DownloadListenerAdapter() {
                @Override
                public void success() {
                    try {
                        new Rout().ToPreReadActivity(context, path);
                    } catch (Exception e) {
                        MyLog.e("201811141751", e.toString(), e);
                    }
                }

                @Override
                public void error(String error) {
                    try {
                        String str = "重试";
                        text.setText(str);
                    } catch (Exception e) {
                        MyLog.e("201811141751", e.toString(), e);
                    }
                }

                @Override
                public void progress(int currentCount, int totalCount) {
                    try {
                        String str = String.valueOf(currentCount) + " / " + totalCount;
                        text.setText(str);
                    } catch (Exception e) {
                        MyLog.e("201811141751", e.toString(), e);
                    }
                }
            });
            downloader.getBook(id);
        }
    }
}