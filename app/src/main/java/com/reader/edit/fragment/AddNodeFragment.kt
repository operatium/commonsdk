package com.reader.edit.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.reader.R
import com.reader.edit.c.EditFileControl
import com.reader.edit.m.EditMessage
import com.reader.edit.m.EditWidgetValue
import com.yyx.commonsdk.app.GlideApp
import com.yyx.commonsdk.baseclass.PointF3D
import com.yyx.commonsdk.baseclass.Side
import com.yyx.commonsdk.baseclass.Size
import kotlinx.android.synthetic.main.edit_fragment_addnode.*
import org.greenrobot.eventbus.EventBus
import java.io.File

class AddNodeFragment : TakePhotoFragment() {
    private var bookId = 0
    private var pageId = 0
    private var rootNode: EditWidgetValue? = null
    private var parentName = ""

    private var node: EditWidgetValue? = null//预览节点
    private var picPath = ""//图片名称 相对当前编辑文件夹下


    companion object {
        fun create(rootNode: EditWidgetValue, bookId: Int, pageId: Int, parentName: String): AddNodeFragment {
            val fragment = AddNodeFragment()
            val bundle = Bundle()
            bundle.putParcelable("rootNode", rootNode)
            bundle.putInt("bookId", bookId)
            bundle.putInt("pageId", pageId)
            bundle.putString("parentName", parentName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_fragment_addnode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rootNode = arguments?.getParcelable("rootNode")
        bookId = arguments?.getInt("bookId")?:0
        pageId = arguments?.getInt("pageId")?:0
        parentName = arguments?.getString("parentName")?:""
        initView()
        addClickEvent()
    }

    fun initView(){
        parentname23.text = parentName
        grounp23.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.layout23 -> {
                    name23.setText("${bookId}_${pageId}_Layout${System.currentTimeMillis()%1000}", TextView.BufferType.EDITABLE)
                }
                R.id.image23 -> {
                    name23.setText("${bookId}_${pageId}_ImageView${System.currentTimeMillis()%1000}", TextView.BufferType.EDITABLE)
                }
                R.id.scroll23 -> {
                    name23.setText("${bookId}_${pageId}_ScrollView${System.currentTimeMillis()%1000}", TextView.BufferType.EDITABLE)
                }
            }
        }
    }

    fun addClickEvent(){
        //选择图片
        pic23.setOnClickListener {
            picname23.setText("book_${bookId}_${pageId}_${System.currentTimeMillis()%1000000}", TextView.BufferType.EDITABLE)
            if (picname23.text.toString().isEmpty()){
                Toast.makeText(activity,"图片需要命名",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startSelectPic(activity)
        }
        //预览
        look23.setOnClickListener {
            if (parentname23.text.isEmpty()) {
                Toast.makeText(activity,"父节点不能为空",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val type = when(grounp23.checkedRadioButtonId){
                R.id.layout23 -> "Layout"
                R.id.image23 -> "ImageView"
                R.id.scroll23 -> "SeamlessRollingView"
                else -> ""
            }
            node = EditWidgetValue(name23.text.toString(),type)
            node?.isEditable = 0
            if (type == "ImageView" || type == "SeamlessRollingView"){
                val pic = picname23.text.toString()
                if (pic.isEmpty())
                    Toast.makeText(activity,"图片不能为空",Toast.LENGTH_SHORT).show()
                else
                    node?.pic = pic
            }
            node?.position = PointF3D(posx23.text.toString().toFloat(),posy23.text.toString().toFloat(),posz23.text.toString().toFloat())
            val left = if (left23.isChecked) 0 else 1
            val top = if (top23.isChecked) 0 else 1
            val right = if (right23.isChecked) 0 else 1
            val bottom = if (buttom23.isChecked) 0 else 1
            node?.side = Side(left,top, right, bottom)
            node?.viewSize = Size(width23.text.toString().toInt(),height23.text.toString().toInt())

            val msg = EditMessage("lookNode")
            msg.node = node
            msg.value = parentName
            EventBus.getDefault().post(msg)
        }
        //使用图片尺寸
        setpicsize23.setOnClickListener {
            if (picPath.isNotEmpty()) {
                val path = EditFileControl().getBookDir(bookId.toString()).absolutePath+"/"+picPath
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeFile(path, options)
                width23.setText(options.outWidth.toString(),TextView.BufferType.EDITABLE)
                height23.setText(options.outHeight.toString(),TextView.BufferType.EDITABLE)
            }else{
                Toast.makeText(activity,"图片为空",Toast.LENGTH_SHORT).show()
            }
        }
        //关闭
        close23.setOnClickListener {
            EventBus.getDefault().post(EditMessage("hideRightMenu"))
        }
    }

    override fun takeSuccess(path: String?) {
        val fileController = EditFileControl()
        val file = File(path)
        val parent = file.parentFile
        val dir = fileController.getBookDir(bookId.toString()).absolutePath
        if (parent.absolutePath != dir) {
            val name = picname23.text.toString()
            if (File(dir, name).exists()){
                Toast.makeText(activity,"图片名称重复",Toast.LENGTH_SHORT).show()
                return
            }
            val result = file.copyTo(File(fileController.getBookDir(bookId.toString()),name),true,1048576)
            if (result.exists()) {
                GlideApp.with(pic23).load(result).into(pic23)
                picPath = name
            }
        }else {
            picPath = file.name
            GlideApp.with(pic23).load(File(path)).into(pic23)
            picname23.setText(file.name,TextView.BufferType.EDITABLE)
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        picsize23.setText("图片信息:\n宽: ${options.outWidth}px, 高: ${options.outHeight}px",TextView.BufferType.EDITABLE)
    }
}