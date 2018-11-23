package com.qicaixiong.reader.page.v

import android.os.Bundle
import android.support.v4.app.Fragment

import com.qicaixiong.reader.m.page.data.NewAnPageModel

/**
 * Created by admin on 2018/10/19.
 */

class frag : Fragment() {
    private val model: NewAnPageModel? = null

    companion object {

        fun newInstance(name: String, passwd: String): frag {
            val newFragment = frag()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("passwd", passwd)
            newFragment.arguments = bundle
            return newFragment
        }
    }
}
