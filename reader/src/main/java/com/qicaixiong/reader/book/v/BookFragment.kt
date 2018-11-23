package com.qicaixiong.reader.book.v

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.qicaixiong.reader.R
import com.qicaixiong.reader.book.c.BookAdapter
import com.qicaixiong.reader.m.book.data.BookFragmentModel
import com.qicaixiong.reader.m.book.event.NextPageEvent
import com.qicaixiong.reader.m.book.event.GoToPageEvent
import com.qicaixiong.reader.get.EndFragmentCallback
import com.qicaixiong.reader.get.ReadBookCallback
import com.qicaixiong.reader.get.ReaderHelp
import com.qicaixiong.reader.other.MessageEvent
import com.qicaixiong.reader.m.page.event.AutoPlayEvent
import com.qicaixiong.reader.permission.PermissionManger
import com.qicaixiong.reader.resource.c.CoordinatesConversion
import com.yyx.commonsdk.log.MyLog
import com.yyx.commonsdk.thread.ThreadPool
import kotlinx.android.synthetic.main.book_fragment_mian.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BookFragment : Fragment() {
    var myModel: BookFragmentModel? = null//当前页的数据
    var coverURL: String? = null//封面
    var nextPageListener: ViewPager.OnPageChangeListener? = null//翻页的回调
    var endFragmentCallback: EndFragmentCallback? = null//封底页回调
    var readCallback :ReadBookCallback?=null//阅读书籍的回调
    var conversion: CoordinatesConversion? = null//坐标数据转换类
    var canPlay : Boolean = true//控制是否允许播放
    var permission = PermissionManger()
    var pageChangeListener : ViewPager.OnPageChangeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.book_fragment_mian, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (myModel == null) {
            myModel = BookFragmentModel()
            myModel?.currentPage = 0
        }

        if (TextUtils.isEmpty(coverURL)) {
            root2.background = ColorDrawable(Color.BLACK)
        } else {
            Glide.with(root2).asDrawable().load(coverURL).into(object : CustomViewTarget<View, Drawable>(root2) {
                override fun onResourceCleared(placeholder: Drawable?) {

                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    root2.background = ColorDrawable(Color.BLACK)
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    root2.background = resource
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    //开始阅读
    fun play(permission: PermissionManger, pageIdx: Int, listener1: ViewPager.OnPageChangeListener?) {

        if(!canPlay) return

        this.permission = permission

        nextPageListener = listener1

        if (book2 != null) {
            book2.visibility = View.VISIBLE
            val adpter = BookAdapter(permission,childFragmentManager, conversion, readCallback,endFragmentCallback, pageIdx)
            adpter.reLoad()
            pageChangeListener = object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    if (!canPlay)
                        return
                    listener1?.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if (!canPlay)
                        return
                    listener1?.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    MyLog.d("show","onPageSelected " + position)
                    if (!canPlay)
                        return
                    if (permission.isCurrentPageAutoPlay) {
                        ThreadPool.newCachedThreadPool().execute {
                            Thread.sleep(100)
                            EventBus.getDefault().post(AutoPlayEvent(position))
                        }
                    }
                    listener1?.onPageSelected(position)
                }
            }
            book2.addOnPageChangeListener(pageChangeListener!!)
            book2.adapter = adpter
            myModel?.allPageCount = ReaderHelp.getInstance().pageModels.size
            if (pageIdx >= 0 && pageIdx < myModel?.allPageCount?:0)
                book2.currentItem = pageIdx
        }
    }

    fun stop() {
        canPlay = false
        val adapter = BookAdapter(null,null,null,null,null,0)
        book2?.adapter = adapter
        book2?.removeAllViews()
    }

    fun pause(){
        canPlay = false
        book2?.removeAllViews()
    }

    fun resume():Boolean{
        canPlay = true
        val page = getCurrentpage()
        val adapter = book2?.adapter ?: return false
        (adapter as BookAdapter).reLoad()
        book2?.adapter = adapter
        if (page == 0){
            //没有翻页回调
            pageChangeListener?.onPageSelected(0)
        }
        book2?.currentItem = page
        return true
    }

    fun getCurrentpage():Int{
        return book2?.currentItem?:0
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event) {
            is NextPageEvent -> {
                val page = book2.currentItem + 1
                if (page >= 0 && page < myModel?.allPageCount ?: 0)
                    book2.currentItem = page
                else {
                    if (permission.isLoopAutoPlay)
                        book2.currentItem = 0
                }
            }
            is GoToPageEvent -> {
                val page = event.page
                if (page >= 0 && page < myModel?.allPageCount?: 0) {
                    book2.currentItem = page
                } else if (page < 0) {
                    book2.currentItem = 0
                } else if (page >= myModel?.allPageCount?: 0) {
                    book2.currentItem = myModel?.allPageCount?: 0
                }
            }
        }
    }
}