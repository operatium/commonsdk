package com.reader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reader.edit.activity.EditBookActivity;
import com.reader.edit.activity.EditListActivity;
import com.reader.edit.activity.EditPageActivity;
import com.reader.function.FunctionActivity;
import com.reader.read.activity.BookListActivity;
import com.reader.read.activity.PreReadActivity;

public class Rout {

    //编辑列表
    public void ToEditListActivity(Context context){
        Intent intent = new Intent(context, EditListActivity.class);
        context.startActivity(intent);
    }

    //编辑一本书
    public void ToEditBookActivity(Context context, String path, int bookId){
        Intent intent = new Intent(context, EditBookActivity.class);
        intent.putExtra("path",path);
        intent.putExtra("bookId",bookId);
        context.startActivity(intent);
    }

    //编辑一页
    public void ToEditPageActivity(Context context, String path, int bookId, int pageId){
        Intent intent = new Intent(context, EditPageActivity.class);
        intent.putExtra("bookId",bookId);
        intent.putExtra("pageId",pageId);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }

    //绘本列表
    public void ToBookListActivity(Context context){
        context.startActivity(new Intent(context, BookListActivity.class));
    }

    //绘本阅览
    public void ToPreReadActivity(Context context, String path){
        Intent intent = new Intent(context, PreReadActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }

    //工具
    public void ToFunctionActivity(Context context){
        Intent intent = new Intent(context, FunctionActivity.class);
        context.startActivity(intent);
    }
}
