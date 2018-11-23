package com.reader.read.activity

import android.graphics.PointF
import android.os.Bundle
import com.qicaibear.bookplayer.c.CoordinatesConversion
import com.qicaibear.bookplayer.c.PermissionManger

import com.reader.BaseActivity
import com.reader.R
import kotlinx.android.synthetic.main.read_acitivity_play.*

class PreReadActivity : BaseActivity() {
    private var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_acitivity_play)
        path = intent.extras?.getString("path") ?: ""

        val w = resources.displayMetrics.widthPixels
        val h = resources.displayMetrics.heightPixels

        val scale: Float
        val center = PointF()
        val width: Int
        val height: Int
        if (w > h) {
            scale = h / 720f
            center.x = w / 2f
            center.y = h / 2f
            width = w
            height = h
        } else {
            scale = w / 720f
            center.x = h / 2f
            center.y = w / 2f
            width = h
            height = w
        }
        val conversion = CoordinatesConversion(scale,center,width,height)
        player14.setPlayMode(PermissionManger(),supportFragmentManager,conversion,null,null)
        player14.setData(path)
        player14.start()
    }

    override fun onPause() {
        super.onPause()
        player14?.pause()
    }

    override fun onResume() {
        super.onResume()
        player14?.resume()
    }
}
