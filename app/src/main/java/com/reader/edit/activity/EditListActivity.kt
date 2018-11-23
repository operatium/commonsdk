package com.reader.edit.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.widget.EditText
import android.widget.Toast

import com.reader.BaseActivity
import com.reader.R
import com.reader.edit.adapter.EditBookAdapter
import com.reader.edit.c.EditFileControl
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp
import com.yyx.commonsdk.screenadaptation.SceenFitFactory
import kotlinx.android.synthetic.main.edit_activity_booklist.*
import android.text.InputType



class EditListActivity : BaseActivity() {
    var fitFactory = SceenFitFactory()
    val editJsonControl = EditFileControl()
    var myAdapter : EditBookAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity_booklist)
        if (AndPermission.hasPermissions(this, Permission.Group.STORAGE)) {
            createEditDir()
        } else {
            AndPermission.with(this).runtime().permission(Permission.Group.STORAGE)
                    .onDenied {
                        Toast.makeText(this@EditListActivity, "没有读写文件的权限", Toast.LENGTH_SHORT).show()
                    }.onGranted {
                        createEditDir()
                    }.start()
        }

        fitFactory.scale = resources.displayMetrics.heightPixels / 720f

        reSize()

        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = GridLayoutManager.HORIZONTAL
        list19.layoutManager = layoutManager

        myAdapter = EditBookAdapter(fitFactory)
        myAdapter?.reLoad(editJsonControl.root)
        list19.adapter = myAdapter

        addClickEvent()
    }

    fun createEditDir() {
        editJsonControl.root.mkdirs()
    }

    fun reSize() {
        LayoutParamsHelp<ConstraintLayout>(add19).with(fitFactory)
                .getLayoutParams(0, 30)
                .apply()
        val addbg = GradientDrawable()
        addbg.setColor(Color.parseColor("#FF612C"))
        add19.background = addbg
        fitFactory.textViewSets(add19, 22, null, Color.WHITE)
    }

    fun addClickEvent() {
        add19.setOnClickListener {
            newBook()
        }
    }

    fun newBook() {
        val et = EditText(this)
        val inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
        et.inputType = inputType

        AlertDialog.Builder(this).setTitle("bookID")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et)
                .setPositiveButton("确定") { dig, which ->
                    val input = et.getText().toString()
                    if (input.isNotEmpty()){
                        editJsonControl.getBookDir(input).mkdirs()
                        myAdapter?.reLoad(editJsonControl.root)
                    }
                    dig.dismiss()
                }
                .setNegativeButton("取消"){ dig, which ->
                    dig.dismiss()
                }
                .show()
    }
}