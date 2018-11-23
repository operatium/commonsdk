package com.reader.navigate

import android.animation.Animator
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.ViewAnimationUtils
import android.widget.TextView

import com.reader.BaseActivity
import com.reader.R
import com.reader.Rout
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp
import com.yyx.commonsdk.screenadaptation.SceenFitFactory
import kotlinx.android.synthetic.main.activity_navigate.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate)
        initView()
        addClickEvent()
    }

    fun initView() {
        val sceenfit = SceenFitFactory.createFitFactory(resources.displayMetrics.heightPixels, 720)
        setTextView(add11, sceenfit)
        setTextView(work11, sceenfit)
        setTextView(function11,sceenfit)
    }

    fun addClickEvent() {
        add11.setOnClickListener {
            Rout().ToEditListActivity(this@HomeActivity)
        }

        work11.setOnClickListener {
            Rout().ToBookListActivity(this@HomeActivity)
        }

        function11.setOnClickListener {
            Rout().ToFunctionActivity(this@HomeActivity)
        }
    }

    fun setTextView(tv: TextView, sceenfit: SceenFitFactory) {
        val addbg = GradientDrawable()
        addbg.setColor(Color.parseColor("#FF612C"))
        tv.background = addbg
        LayoutParamsHelp<ConstraintLayout>(tv).with(sceenfit)
                .getLayoutParams(220, 66).apply()
        sceenfit.textViewSets(tv, 36, null, Color.WHITE)
    }

    fun gotoNextActivityAnimation(posiotion: Point, listenr: Animator.AnimatorListener) {
        val animator = ViewAnimationUtils.createCircularReveal(root11, posiotion.x, posiotion.y
                , resources.displayMetrics.heightPixels.toFloat(), add11.height / 2f)
        animator.addListener(listenr)
        animator.start()
    }
}