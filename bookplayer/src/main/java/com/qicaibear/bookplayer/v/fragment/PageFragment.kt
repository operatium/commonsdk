package com.qicaibear.bookplayer.v.fragment

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.qicaibear.bookplayer.R
import com.qicaibear.bookplayer.c.*
import com.qicaibear.bookplayer.m.EventContext
import com.qicaibear.bookplayer.m.Message
import com.qicaibear.bookplayer.m.client.AnimatorValue
import com.qicaibear.bookplayer.m.client.Event
import com.qicaibear.bookplayer.m.client.OrderAction
import com.qicaibear.bookplayer.m.client.RectAreaAction
import com.yyx.commonsdk.baseclass.Size
import com.yyx.commonsdk.file.FileUtils
import com.yyx.commonsdk.log.MyLog
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception
import java.util.LinkedList

/**
 * 书籍的一页
 * Created by admin on 2018/10/19.
 */

class PageFragment : BaseFragment() {
    //解析json创建
    private val actionMap = HashMap<String, AnimatorValue>()//注册的全部动作
    private val actionGroupMap = HashMap<String, ArrayList<OrderAction>>() //注册的全部动作组
    private val initActions = ArrayList<Event>()//所有控件 初始化动作组 集合
    private val eventMap = HashMap<String, Event>()//事件 集合
    private val rectMap = HashMap<String, ArrayList<RectAreaAction>>() //节点的区域事件

    //绘制创建
    private val viewMap = HashMap<String, View>()//注册的全部控件
    private val popViews: LinkedList<ImageView> = LinkedList()

    private val director: Director = Director()
    private val soundController = SoundController()//声音
    private var eventBusIsRegistered = false //是否注册eventbus

    companion object {
        fun newInstance(path: String, pageId: Int, scale: Float, centerX: Float, centerY: Float, playerWidth: Int, playerHeight: Int, position: Int): PageFragment {
            val newFragment = PageFragment()
            val bundle = Bundle()
            bundle.putString("path", path)
            bundle.putInt("pageId", pageId)
            bundle.putFloat("scale", scale)
            bundle.putFloat("centerX", centerX)
            bundle.putFloat("centerY", centerY)
            bundle.putInt("playerWidth", playerWidth)
            bundle.putInt("playerHeight", playerHeight)
            bundle.putInt("position", position)
            newFragment.arguments = bundle
            return newFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        val path = arguments?.getString("path") ?: ""
        val pageId = arguments?.getInt("pageId") ?: 0
        val scale = arguments?.getFloat("scale") ?: 1f
        val centerX = arguments?.getFloat("centerX") ?: 0f
        val centerY = arguments?.getFloat("centerY") ?: 0f
        val playerWidth = arguments?.getInt("playerWidth") ?: 0
        val playerHeight = arguments?.getInt("playerHeight") ?: 0
        val position = arguments?.getInt("position") ?: 0
        try {
            soundController.myName = pageId.toString()
            director.eventContext = EventContext(context, path, soundController)

            val pageJson = FileUtils.readCacheAbsolute(path, pageId.toString())

            val conversion = CoordinatesConversion(scale, PointF(centerX, centerY), playerWidth, playerHeight)
            //字符串 => 模型
            val pageDataController = PagerDataPipeline()
            val root = pageDataController.createViewModel(pageJson, actionMap, actionGroupMap, initActions, eventMap, rectMap)
            if (root != null) {
                //布局
                view = NodeControl.createNode(root, director, null
                        , Size(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
                        , conversion, actionMap, actionGroupMap, viewMap, eventMap, rectMap, popViews)
            }
        } catch (e: Exception) {
            MyLog.e("201811162048", e.toString(), e)
        }
        if (view == null) {
            val layout = ConstraintLayout(context)
            layout.setBackgroundColor(Color.WHITE)
            view = layout
        }
        //根节点必须全屏
        LayoutParamsHelp<ViewGroup>(view).getLayoutParams(-1, -1).apply()
        //必须用于外部访问
        view.setTag(R.id.viewName, position)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MyLog.d("show1", "${soundController.myName} onViewCreated ")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.isVisibleToUser = isVisibleToUser
        if (super.created) {
            if (isVisibleToUser) {
                if (!EventBus.getDefault().isRegistered(this))
                    EventBus.getDefault().register(this)
                if (!initActions.isEmpty()) {
                    MyLog.d("show1", "${soundController.myName} setUserVisibleHint = ${initActions.size}")
                    initActions.forEach { it ->
                        director.runEvent(it.myName, null, viewMap, actionMap, actionGroupMap, eventMap, popViews)
                    }
                }
            } else {
                EventBus.getDefault().unregister(this)
                soundController.destory()
                director.actionsCancel(viewMap)
            }
        }
        MyLog.d("show1", "${soundController.myName} isVisibleToUser = ${isVisibleToUser} EventBus = ${EventBus.getDefault().isRegistered(this)} ")
    }

    override fun onResume() {
        super.onResume()
        if (eventBusIsRegistered)
            EventBus.getDefault().register(this)
        soundController.resume()
        MyLog.d("show1", "${soundController.myName} onResume EventBus = ${ EventBus.getDefault().isRegistered(this)} ")
    }

    override fun onPause() {
        super.onPause()
        eventBusIsRegistered = EventBus.getDefault().isRegistered(this)
        EventBus.getDefault().unregister(this)
        soundController.pause()
        director.actionsCancel(viewMap)
        MyLog.d("show1", "${soundController.myName} onPause EventBus = ${ EventBus.getDefault().isRegistered(this)} ")
    }

    override fun onStart() {
        super.onStart()
        MyLog.d("show1", "${soundController.myName} onStart")
    }

    override fun onStop() {
        super.onStop()
        MyLog.d("show1", "${soundController.myName} onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MyLog.d("show1", "${soundController.myName} onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        MyLog.d("show1", "${soundController.myName} onDestroy")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun helloEventBus(event: Message) {
        MyLog.d("show", "helloEventBus " + event.toString())
        val who = event.who
        val grounpAction = event.actionGrounpName
        val view = viewMap[who]
        val time : Long = (view?.getTag(R.id.viewEvent)?:-1) as Long
        if (time > 0 && time == event.time) {
            director.whoRunAction(who, grounpAction, null, viewMap, actionMap, actionGroupMap, popViews)
        }
    }
}