package com.reader.read.activity

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast

import com.reader.BaseActivity
import com.reader.R
import com.reader.net.NetControl
import com.reader.read.adapter.BookAdapter
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp
import com.yyx.commonsdk.screenadaptation.SceenFitFactory
import kotlinx.android.synthetic.main.read_activity_main.*

class BookListActivity : BaseActivity() {
    var sceenfit: SceenFitFactory? = null
    var adapter :BookAdapter?= null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_activity_main)
        initView()
        http()
    }

    private fun initView() {
        sceenfit = SceenFitFactory.createFitFactory(resources.displayMetrics.heightPixels, 720)
        //标题
        LayoutParamsHelp<ConstraintLayout>(title13).with(sceenfit)
                .getLayoutParams(200, 80)
                .apply()
        sceenfit?.textViewSets(title13, 42, null, 0)

        //列表
        LayoutParamsHelp<ConstraintLayout>(book13).with(sceenfit)
                .getLayoutParams(0, 600)
                .applyMargin_ConstraintLayout(0, 10, 0, 0)
                .apply()

        setAdapter()
    }

    private fun setAdapter() {
        if(adapter == null) {
            adapter = BookAdapter(sceenfit)
        }
        book13.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = GridLayoutManager.HORIZONTAL
        book13.layoutManager = layoutManager
    }

    private fun http() {
        NetControl.getInstance().getBooksList({ list ->
            adapter?.reLoad(list)
        }, { t ->
            Toast.makeText(this@BookListActivity, "网络错误", Toast.LENGTH_SHORT).show()
        })
    }
}