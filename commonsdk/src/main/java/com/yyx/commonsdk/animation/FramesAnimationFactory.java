package com.yyx.commonsdk.animation;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yyx.commonsdk.app.GlideApp;
import com.yyx.commonsdk.baseclass.Size;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * Created by admin on 2018/10/18.
 */

public class FramesAnimationFactory {

    //本地文件
    public static void largeFramesLoadInorder_PathFile(Context context, final ArrayList<String> listPic, Size picSize
            , final ArrayList<Integer> time, final boolean isOneShot, final FramesAnimationReady callback) {
        final SparseArray map = new SparseArray<Drawable>();
        for (int i = 0; i < listPic.size(); i++) {
            final int idx = i;
            GlideApp.with(context).asDrawable().load(new File(listPic.get(i))).override(picSize.getWidth(), picSize.getHeight())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            Drawable drawable = resource;
                            if (callback != null) {
                                drawable = callback.onFramesReady(resource, idx);
                            }
                            map.put(idx, drawable);
                            if (map.size() == listPic.size()) {
                                AnimationDrawable animationDrawable = new AnimationDrawable();
                                animationDrawable.setOneShot(isOneShot);
                                for (int i = 0; i < map.size(); i++) {
                                    animationDrawable.addFrame((Drawable) map.get(i), time.get(i));
                                }
                                if (callback != null)
                                    callback.onResourceReady(animationDrawable);
                            }
                        }
                    });
        }
    }

    //本地资源
    public static void largeFramesLoadInorder_LocationResouce(Context context, final ArrayList<Integer> listPic, Size picSize
            , final ArrayList<Integer> time, final boolean isOneShot, final FramesAnimationReady callback) {
        final SparseArray map = new SparseArray<Drawable>();
        for (int i = 0; i < listPic.size(); i++) {
            final int idx = i;
            GlideApp.with(context).asDrawable().load(listPic.get(i)).override(picSize.getWidth(), picSize.getHeight())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            Drawable drawable = resource;
                            if (callback != null) {
                                drawable = callback.onFramesReady(resource, idx);
                            }
                            map.put(idx, drawable);
                            if (map.size() == listPic.size()) {
                                AnimationDrawable animationDrawable = new AnimationDrawable();
                                animationDrawable.setOneShot(isOneShot);
                                for (int i = 0; i < map.size(); i++) {
                                    animationDrawable.addFrame((Drawable) map.get(i), time.get(i));
                                }
                                if (callback != null)
                                    callback.onResourceReady(animationDrawable);
                            }
                        }
                    });
        }
    }

    public interface FramesAnimationReady {
        Drawable onFramesReady(Drawable resource, int idx);
        void onResourceReady(AnimationDrawable animationDrawable);
    }
}