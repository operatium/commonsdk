package com.reader.edit.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reader.R
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp
import com.yyx.commonsdk.screenadaptation.SceenFitFactory
import kotlinx.android.synthetic.main.edit_fragment_function.*

class FunctionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_fragment_function, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reSize()
        addClickEvent()
    }

    fun reSize() {
        val sceenfit = SceenFitFactory.createFitFactory(resources.displayMetrics.heightPixels, 720)
        LayoutParamsHelp<ConstraintLayout>(apply16).with(sceenfit)
                .getLayoutParams(220, 66)
                .applyMargin_ConstraintLayout(0, 0, 0, 20)
                .apply()
        val addbg = GradientDrawable()
        addbg.setColor(Color.parseColor("#FF612C"))
        apply16.background = addbg
        sceenfit.textViewSets(apply16, 36, null, Color.WHITE)
    }

    fun addClickEvent() {
        apply16.setOnClickListener {
            val qianzhui = guding16.text.toString()
            val houzhui = suxx16.text.toString()
            val count = count16.text.toString().toInt()
            val i = idx16.text.toString().toInt()
            var str = "["
            for (item in i..(i + count)) {
                val name =  String.format("\"$qianzhui%02d$houzhui\"", item)
                if (item != i)
                    str = "$str,$name"
                else
                    str +=name
            }
            str += "]"
            out16.setText(str)
        }
    }
}
