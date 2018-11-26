package com.yyx.commonsdk.app;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

import com.yyx.commonsdk.DialogFragment.BottomDialogFragment;
import com.yyx.commonsdk.DialogFragment.CenterDialogFragment;
import com.yyx.commonsdk.animation.AlphaAnimationFactory;
import com.yyx.commonsdk.animation.AnimationHelp;
import com.yyx.commonsdk.animation.FramesAnimationFactory;
import com.yyx.commonsdk.animation.RotateAnimationFactory;
import com.yyx.commonsdk.animation.ScaleAnimationFactory;
import com.yyx.commonsdk.animation.TranslationAnimationFactory;
import com.yyx.commonsdk.baseclass.Point3D;
import com.yyx.commonsdk.baseclass.PointF3D;
import com.yyx.commonsdk.baseclass.Side;
import com.yyx.commonsdk.baseclass.Size;
import com.yyx.commonsdk.file.FileUtils;
import com.yyx.commonsdk.log.MyLog;
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp;
import com.yyx.commonsdk.screenadaptation.SceenFitFactory;
import com.yyx.commonsdk.sound.AnSound;
import com.yyx.commonsdk.sound.MediaPlayerManager;
import com.yyx.commonsdk.sound.SoundPlayManager;
import com.yyx.commonsdk.string.SHA1;
import com.yyx.commonsdk.thread.ThreadPool;
import com.yyx.commonsdk.time.TimeUtil;

import java.io.File;
import java.util.ArrayList;

public class UseControl extends Activity {
    private Size size;
    private PointF3D pointF3D;
    private Point3D point3D;
    private Side side;
    private BottomDialogFragment bottomDialogFragment;
    private CenterDialogFragment centerDialogFragment;
    private LayoutParamsHelp<ConstraintLayout> help;
    private SceenFitFactory fitFactory;
    private AnSound anSound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        AlphaAnimationFactory.alpha(imageView,0,0,0,0,new float[]{0});
        ArrayList<AnimationHelp> helps = new ArrayList<>();
        AnimationHelp.outList(helps);
        ArrayList<String> pic = new ArrayList<>();
        ArrayList<Integer> time = new ArrayList<>();
        FramesAnimationFactory.largeFramesLoadInorder_PathFile(this, pic, new Size(0,0),time, true,null);
        RotateAnimationFactory.rotate("x",imageView,0,0,0,0,0f,0f,new float[]{0});
        ScaleAnimationFactory.scale(imageView,0,0,0,0,0f,0f,new float[]{0},new float[]{0});
        TranslationAnimationFactory.translationBessel(imageView, 0,0,0,0,new PointF());
        FileUtils.deleteFile(new File("1"), false);
        MyLog.debug = false;
        new MediaPlayerManager();
        new SoundPlayManager();
        SHA1.hexdigest("fdsa");
        ThreadPool.newSingleThreadExecutor();
        TimeUtil.timeStamp2Date(0,"");
    }
}
