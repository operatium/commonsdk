package com.reader.edit.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.qicaibear.bookplayer.m.server.PbBooksDetail

import com.reader.BaseActivity
import com.reader.R
import com.reader.edit.adapter.EditPageAdapter
import com.reader.edit.c.EditFileControl
import com.yyx.commonsdk.file.FileUtils
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp
import com.yyx.commonsdk.screenadaptation.SceenFitFactory
import kotlinx.android.synthetic.main.activity_editbook.*

class EditBookActivity : BaseActivity() {
    val editJsonControl = EditFileControl()
    var path = ""
    var pagedata = HashMap<Int, PbBooksDetail>()
    var bookId = -1
    var adapter : EditPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editbook)
        path = intent.extras?.getString("path")?:""
        bookId = intent.extras?.getInt("bookId")?:-1

        val sceenfit = SceenFitFactory.createFitFactory(resources.displayMetrics.heightPixels, 720)

        reSize(sceenfit)
        initView(sceenfit)
        startPostponedEnterTransition()
        addClickEvent()
    }

    fun initView(sceenfit:SceenFitFactory) {
        val data = FileUtils.readCacheAbsolute(path, editJsonControl.relativePathPageNoList())
        if (!data.isNullOrEmpty()){
            val object1 = JSON.parseArray(data,PbBooksDetail::class.java)
            if (object1 != null && !object1.isEmpty()){
                pagedata.clear()
                object1.forEach {it ->
                    if (it != null)
                        pagedata[it.id] = it
                }
            }
        }

        pages12.layoutManager = LinearLayoutManager(this)
        adapter = EditPageAdapter(sceenfit, path)
        adapter?.reLoad(pagedata)
        pages12.adapter = adapter
    }

    fun reSize(sceenfit:SceenFitFactory){
        //标题
        LayoutParamsHelp<ConstraintLayout>(title12).with(sceenfit)
                .getLayoutParams(0, 0)
                .applyMargin_ConstraintLayout(0, 20, 0, 0)
                .apply()
        //输入id
        LayoutParamsHelp<ConstraintLayout>(text12).with(sceenfit)
                .getLayoutParams(0, 0)
                .applyMargin_ConstraintLayout(0, 20, 0, 0)
                .apply()

        //列表
        LayoutParamsHelp<ConstraintLayout>(pages12).with(sceenfit)
                .getLayoutParams(0, 0)
                .applyMargin_ConstraintLayout(0, 20, 0, 0)
                .apply()

        //新建页面
        LayoutParamsHelp<ConstraintLayout>(save12).with(sceenfit)
                .getLayoutParams(220, 66)
                .applyMargin_ConstraintLayout(0, 0, 0, 20)
                .apply()
        val addbg = GradientDrawable()
        addbg.setColor(Color.parseColor("#FF612C"))
        save12.background = addbg
        sceenfit.textViewSets(save12, 36, null, Color.WHITE)
    }

    fun inAnimation() {
        save12.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                save12.viewTreeObserver.removeOnPreDrawListener(this)
                val animator = ViewAnimationUtils.createCircularReveal(root12
                        , save12.x.toInt() + save12.width / 2, save12.y.toInt() + save12.height / 2
                        , save12.height / 2f, resources.displayMetrics.heightPixels.toFloat())
                animator.duration = 2000
                animator.start()
                return true
            }

        })
    }

    fun addClickEvent() {
        save12.setOnClickListener {
            newPage()
        }
    }

    fun newPage(){
        val pageID = edit12.text.toString().toInt()
        val pageNo = idx12.text.toString().toInt()
        if(pagedata.contains(pageID)){
            Toast.makeText(this@EditBookActivity,"已经存在",Toast.LENGTH_SHORT).show()
            return
        }
        val data = PbBooksDetail()
        data.bookId = bookId
        data.id = pageID
        data.status = 1
        data.pageNo = pageNo
        pagedata[pageID] = data
        FileUtils.writeCacheAbsolute(path, editJsonControl.relativePathPageContent(pageID.toString()), "")
        saveJson()
    }

    fun saveJson(){
        val list = ArrayList<PbBooksDetail>()
        for ((id,item) in pagedata){
            list.add(item)
        }
        val json = JSON.toJSONString(list)
        FileUtils.writeCacheAbsolute(path,editJsonControl.relativePathPageNoList(), json)
        adapter?.reLoad(pagedata)
    }
}