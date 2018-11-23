package com.qicaixiong.reader.page.v

import android.graphics.PointF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.qicaixiong.reader.m.page.data.NewAnPageModel
import com.qicaixiong.reader.bookmodel.animator.AnimatorValue
import com.qicaixiong.reader.bookmodel.animator.OrderAction
import com.qicaixiong.reader.page.c.Director
import com.qicaixiong.reader.resource.c.CoordinatesConversion
import com.qicaixiong.reader.resource.c.DataPipeline
import com.yyx.commonsdk.screenadaptation.LayoutParamsHelp
import java.util.LinkedList

/**
 * 书籍的一页
 * Created by admin on 2018/10/19.
 */

class NewPageFragment : Fragment(){
    private lateinit var pageModel : NewAnPageModel
//    private val control = NewPageFragmentControl()
    private var conversion : CoordinatesConversion? = null

    private val viewMap = HashMap<String, View>()//注册的全部控件
    private val actionMap = HashMap<String, AnimatorValue>()//注册的全部动作
    private val actionGroupMap = HashMap<String,ArrayList<OrderAction>>() //注册的全部动作组
    private val director : Director = Director()
    private val initActions = HashMap<String,String>()//所有控件 初始化动作组 集合
    private val popViews : LinkedList<ImageView> = LinkedList()

    companion object {
        fun newInstance(page: String, scale: Float, centerX: Float, centerY: Float, playerWidth: Int, playerHeight: Int, position:Int): NewPageFragment {
            val newFragment = NewPageFragment()
            val bundle = Bundle()
            bundle.putString("page", page)
            bundle.putFloat("scale", scale)
            bundle.putFloat("centerX", centerX)
            bundle.putFloat("centerY", centerY)
            bundle.putInt("playerWidth", playerWidth)
            bundle.putInt("playerHeight", playerHeight)
            bundle.putInt("position",position)
            newFragment.arguments = bundle
            return newFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val page = arguments?.getString("page")
        val scale = arguments?.getFloat("scale") ?: 1f
        val centerX = arguments?.getFloat("centerX") ?: 0f
        val centerY = arguments?.getFloat("centerY") ?: 0f
        val playerWidth = arguments?.getInt("playerWidth") ?: 0
        val playerHeight = arguments?.getInt("playerHeight") ?: 0
        val position = arguments?.getInt("position") ?: 0

        conversion = CoordinatesConversion(scale, PointF(centerX, centerY), playerWidth, playerHeight)
        //字符串 => 模型
        pageModel = DataPipeline.stringTranformModel(page)

/*        //todo
        when(page){
            "0" -> pageModel.rootWidget = control.page1()
            "1" -> pageModel.rootWidget = control.page2()
            "2" -> pageModel.rootWidget = control.page3()
            "3" -> pageModel.rootWidget = control.page4()
            "4" -> pageModel.rootWidget = control.demo4(actionMap, actionGroupMap)
        }
        MyLog.d("show","create fragment = " + page)

        //布局
//        val view = NodeControl.createNode(director, context, null
//                , Size(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
//                , pageModel.rootWidget, conversion, actionMap, actionGroupMap, viewMap, initActions, popViews)
*/
        LayoutParamsHelp<ViewGroup>(view).creatLayoutParams(-1, -1).apply()
//        view.background = ColorDrawable(Color.WHITE)
//        view.setTag(R.id.viewName, position)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
//            initActions.forEach { (key, value) -> director.whoRunAction(key, value, null, viewMap, actionMap, actionGroupMap, popViews) }
            initActions.clear()
        }
    }
}