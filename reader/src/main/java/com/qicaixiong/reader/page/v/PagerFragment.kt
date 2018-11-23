package com.qicaixiong.reader.page.v

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qicaixiong.reader.R
import com.qicaixiong.reader.book.c.StrokeTextViewBuilder
import com.qicaixiong.reader.get.ReaderHelp
import com.qicaixiong.reader.get.ReadBookCallback
import com.qicaixiong.reader.other.MessageEvent
import com.qicaixiong.reader.page.oldc.BackgroundControl
import com.qicaixiong.reader.page.oldc.PagerFragmentControl
import com.qicaixiong.reader.page.oldc.PlaySoundControl
import com.qicaixiong.reader.page.oldc.TextTurnControl
import com.qicaixiong.reader.m.page.event.AutoPlayEvent
import com.qicaixiong.reader.m.page.event.ClickRectEvent
import com.qicaixiong.reader.m.page.event.ReplayEvent
import com.qicaixiong.reader.permission.PermissionManger
import com.qicaixiong.reader.resource.c.CoordinatesConversion
import com.qicaixiong.reader.m.show.AnPageModel
import com.yyx.commonsdk.log.MyLog
import com.yyx.commonsdk.string.SHA1
import kotlinx.android.synthetic.main.page_fragment_mian.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PagerFragment : Fragment() {
    private lateinit var myPager: AnPageModel
    var mydata : AnPageModel? = null
    var myPopText: HashMap<String, Int> = HashMap()//每个弹出动画的次数 控制角度 不重叠
    private var valueAnimators: ArrayList<ValueAnimator?> = ArrayList()
    var conversion: CoordinatesConversion? = null
    var permissionManger: PermissionManger = PermissionManger()

    var startCallback : ReadBookCallback? = null
    var firstPage = 0//播放的第一页，一般是0  跳页播放的时候不是0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        return inflater.inflate(R.layout.page_fragment_mian, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mydata == null)
            return
        else
            myPager = mydata!!
        initFragment()

        if (myPager.pageNo == firstPage && permissionManger.isCurrentPageAutoPlay) {
            EventBus.getDefault().post(AutoPlayEvent(firstPage))
            MyLog.d("show","onViewCreated AutoPlayEvent " + firstPage)
            if (startCallback != null)
                startCallback?.startReadBook()
        }
    }

    override fun onStop() {
        super.onStop()
        clear()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            // 相当于onResume()方法
        } else {
            clear()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    private fun initFragment() {

        //初始化背景大图
        PagerFragmentControl.initPagerBackgroundImage(readerbackground1, myPager,conversion)
        //添加文本
        PagerFragmentControl.initText(permissionManger, root1, myPager.texts)

        //添加背景上的点击区域 和事件
        PagerFragmentControl.addRectClicks(permissionManger, readerbackground1, myPager.clickModels, myPager.pageNo)
        //放置重读按钮
        if (permissionManger.isRePlayButtonable) {
            val path = myPager.voice?.absolutePath
            val tag = SHA1.hexdigest(path)
            val rePlayButton = PagerFragmentControl.initRePlayButton(tag, myPager, root1)
            rePlayButton?.setOnClickListener {
                playAll()
            }
        }
    }

    //整段的语音播放
    fun playAll() {
        if (myPager.voice != null) {
            val path = myPager.voice?.absolutePath
            if (TextUtils.isEmpty(path))
                return
            val tag = SHA1.hexdigest(path)
            //播放正常后 播放动画
            val b = PlaySoundControl.rePlay(permissionManger, path, tag)
            if (b) {
                permissionManger.isAllTextPlaying = true
                val time = myPager.voiceTime.toLong()
                readerbackground1.postDelayed({
                    permissionManger.isAllTextPlaying = false
                },time)
                //通知 重读按钮 隐藏
                EventBus.getDefault().post(ReplayEvent(tag, true, time.toInt()))
                //播放动画 动画的回调是 自动翻页
                val valueAnimator = TextTurnControl.start(permissionManger,myPager.texts, time)
                valueAnimators.add(valueAnimator)
            }
        }
    }

    fun clear() {
        stopAnimations()
        StrokeTextViewBuilder.getInstance().clear()
        if (popgrounp1 != null)
            popgrounp1.removeAllViews()
    }

    fun stopAnimations() {
        for (item in valueAnimators) {
            if (item != null) {
                item.removeAllListeners()
                item.removeAllUpdateListeners()
                item.cancel()
            }
        }
        valueAnimators.clear()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event) {
            is ClickRectEvent -> {
                if (event.pageNo == myPager.pageNo)
                    if (permissionManger.isRectClickable ) {
                        PlaySoundControl.rectPlay(permissionManger,event.word, event.soundPath)
                        BackgroundControl.showClickRect(popgrounp1, event, myPopText)
                        ReaderHelp.getInstance().addWord(event.word)
                    }
            }
            is AutoPlayEvent -> {
                MyLog.d("show","AutoPlayEvent ("+ event.pageNo+")  page = "+ myPager.pageNo)
                if (myPager.pageNo == event.pageNo)
                    playAll()
            }
        }
    }
}