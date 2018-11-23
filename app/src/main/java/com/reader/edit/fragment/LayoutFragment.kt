package com.reader.edit.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qicaibear.bookplayer.m.client.WidgetValue

import com.reader.R
import com.reader.edit.adapter.LayoutAdapter
import kotlinx.android.synthetic.main.edit_fragment_layout.*

/**
 * 编辑图层
 */
class LayoutFragment : Fragment() {
    private var rootNode : WidgetValue? = null
    private var adapter : LayoutAdapter? = null

    companion object {
        fun create(rootNode : WidgetValue) : LayoutFragment{
            val fragment = LayoutFragment()
            val bundle = Bundle()
            bundle.putParcelable("rootNode",rootNode)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootNode = arguments?.getParcelable("rootNode")

        adapter = LayoutAdapter()
        adapter?.reLoad(rootNode)
        list21.adapter = adapter
        list21.layoutManager = LinearLayoutManager(context)
    }
}
