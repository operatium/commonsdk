package com.reader.edit.activity

import android.graphics.PointF
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.qicaibear.bookplayer.c.*
import com.qicaibear.bookplayer.m.EventContext
import com.qicaibear.bookplayer.m.client.*
import com.reader.BaseActivity
import com.reader.R
import com.reader.edit.c.EditFileControl
import com.reader.edit.c.EditNodeControl
import com.reader.edit.fragment.AddNodeFragment
import com.reader.edit.fragment.LayoutFragment
import com.reader.edit.m.EditMessage
import com.reader.edit.m.EditWidgetValue
import com.yyx.commonsdk.baseclass.PointF3D
import com.yyx.commonsdk.baseclass.Side
import com.yyx.commonsdk.baseclass.Size
import com.yyx.commonsdk.file.FileUtils
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp
import com.yyx.commonsdk.screenadaptation.SceenFitFactory
import kotlinx.android.synthetic.main.activity_editpage.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class EditPageActivity : BaseActivity() {
    val sceenfit = SceenFitFactory()
    val editJsonControl = EditFileControl()
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
    private val conversion = CoordinatesConversion()

    //视图模型
    private var node: EditWidgetValue? = null
    private var bookId = 0
    private var pageId = 0
    private var path = ""

    //当前数据
    private var selectNode: String = ""

    private val soundController = SoundController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editpage)
        EventBus.getDefault().register(this)

        bookId = intent.extras?.getInt("bookId") ?: 0
        pageId = intent.extras?.getInt("pageId") ?: 0
        path = intent.extras?.getString("path") ?: ""

        var w = resources.displayMetrics.widthPixels
        var h = resources.displayMetrics.heightPixels
        if (w < h) {
            w = resources.displayMetrics.heightPixels
            h = resources.displayMetrics.widthPixels
        }
        sceenfit.scale = h / 720f
        val size = Size(w, w * 720 / 1600)
        director.eventContext = EventContext(this, path, soundController)
        conversion.sceenFitFactory = SceenFitFactory.createFitFactory(size.height, 720)
        conversion.center = PointF(size.width / 2f, size.height / 2f)
        conversion.width = size.width
        conversion.height = size.height

        initView()
        addClickEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun initView() {
        reSize()

        if (node == null) {
            val root = EditWidgetValue("root", "Layout")
            root.position = PointF3D()
            root.side = Side(0, 0, 0, 0)
            root.viewSize = Size(-1, -1)
            //背景图
            val bg = EditWidgetValue("background", "ImageView")
            bg.viewSize = Size(-1, -1)
            bg.side = Side(0, 0, 0, 0)
            bg.position = PointF3D()

            val children = ArrayList<WidgetValue>()
            children.add(bg)

            root.children = children

            node = root
        }
        player10.removeAllViews()
        EditNodeControl.createNode(node, director, player10, Size(conversion.width, conversion.height)
                , conversion, actionMap, actionGroupMap, viewMap, eventMap, rectMap, popViews)
    }

    fun reSize() {
        LayoutParamsHelp<ConstraintLayout>(show10).with(sceenfit)
                .getLayoutParams(720, 720)
                .apply()

        LayoutParamsHelp<ConstraintLayout>(menu10).with(sceenfit)
                .getLayoutParams(0,200)
                .apply()
    }


    fun addClickEvent() {
        //底部按钮
        kaiguan10.setOnClickListener {
            menu10.visibility = View.VISIBLE
        }
        //关闭菜单
        guan10.setOnClickListener {
            menu10.visibility = View.INVISIBLE
        }
        //保存
        save10.setOnClickListener {
            val data = PagerDataPipelineToJson().parseJson(bookId, pageId, node, viewMap, actionMap, actionGroupMap, initActions, eventMap, rectMap)
            val json = JSON.toJSONString(data)
            FileUtils.writeCacheAbsolute(path, editJsonControl.relativePathPageContent(pageId.toString()), json)
        }
        //选择图层
        selectlayout10.setOnClickListener {
            if (node != null) {
                val transaction = supportFragmentManager.beginTransaction()
                val fragment = LayoutFragment.create(node!!)
                transaction.replace(R.id.show10, fragment)
                transaction.commitAllowingStateLoss()
            }
        }
        //展示
        hide10.text = "隐藏"
        hide10.setOnClickListener {
            if (hide10.text == "展示") {
                show10.visibility = View.VISIBLE
                hide10.text = "隐藏"
            } else {
                show10.visibility = View.INVISIBLE
                hide10.text = "展示"
            }
        }
        //添加节点
        addnode10.setOnClickListener {
            player10.removeAllViews()
            EditNodeControl.createNode(node, director, player10, Size(conversion.width, conversion.height)
                    , conversion, actionMap, actionGroupMap, viewMap, eventMap, rectMap, popViews)
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = AddNodeFragment.create(node!!,bookId, pageId, selectNode)
            transaction.replace(R.id.show10, fragment)
            transaction.commitAllowingStateLoss()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(msg: EditMessage) {
        when(msg.name){
            "selectNode" ->{
                selectNode = msg.value
                Toast.makeText(this,"选中$selectNode",Toast.LENGTH_SHORT).show()
            }

            "lookNode" ->{
                val parentName = msg.value
                val selectView = viewMap.get(parentName)
                if (selectView != null && selectView is ConstraintLayout) {
                    val nodeValue = msg.node
                    val viewName = nodeValue.myName
                    val childView = selectView.findViewWithTag<View>(viewName)
                    if (childView != null) {
                        selectView.removeView(childView)
                    }
                    val parentSize = Size(selectView.width, selectView.height)
                    val parentConversion = selectView.getTag(R.id.viewCoordinatesConversion) as CoordinatesConversion
                    val conversion = CoordinatesConversion(parentConversion.sceenFitFactory.scale
                            , PointF(selectView.x + selectView.width / 2f, selectView.y + selectView.height / 2f)
                            , parentConversion.width, parentConversion.height)
                    val view = EditNodeControl.createNode(nodeValue, director, selectView, parentSize, conversion
                            , actionMap, actionGroupMap, viewMap, eventMap, rectMap, popViews)
                    view.tag = viewName
                }else{
                    Toast.makeText(this,"无法添加",Toast.LENGTH_SHORT).show()
                }
            }

            "hideRightMenu" ->{
                hide10.text = "展示"
                show10.visibility = View.INVISIBLE
            }
        }
    }
}