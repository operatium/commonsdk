package com.reader.function

import android.os.Bundle

import com.reader.BaseActivity
import com.reader.R
import com.reader.edit.fragment.FunctionFragment

class FunctionActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.function_activity_main)
        initView()
    }

    fun initView(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pages20, FunctionFragment())
        transaction.commitAllowingStateLoss()
    }
}
