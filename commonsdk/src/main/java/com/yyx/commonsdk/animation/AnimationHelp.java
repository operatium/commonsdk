package com.yyx.commonsdk.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/10/17.
 */

public class AnimationHelp {
    private AnimatorSet animatorSet = new AnimatorSet();
    private AnimatorSet.Builder builder;

    public AnimationHelp() {
    }

    //配置用的 动画组
    public AnimationHelp createGroup(Animator... animator) {
        builder = animatorSet.play(animator[0]);
        for (int i = 1; i<animator.length ; i++){
            addTogotherGroup(animator[i]);
        }
        return this;
    }

    //配置用的 动画组
    public AnimationHelp addTogotherGroup(Animator animator){
        if (builder == null)
            builder = animatorSet.play(animator);
        else {
            builder.with(animator);
        }
        return this;
    }

    //获取动画集
    public AnimatorSet outPutAnimatorSet(){
        return animatorSet;
    }

    //可执行 顺序动画
    public AnimatorSet createSequentially(Animator... set){
        animatorSet.playSequentially(set);
        return animatorSet;
    }

    //可执行 并行动画
    public AnimatorSet outTogether(Animator... set){
        animatorSet.playTogether(set);
        return animatorSet;
    }

    //执行 列表动画 的 播放
    public static AnimatorSet outList(ArrayList<AnimationHelp> list){
        ArrayList<Animator> animators = new ArrayList<>();
        for (AnimationHelp help : list){
            animators.add(help.outPutAnimatorSet());
        }
        Animator[] animators1 = new Animator[animators.size()];
        animators.toArray(animators1);
        return new AnimationHelp().createSequentially(animators1);
    }
}
