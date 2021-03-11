package com.lmx.common.dialog

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lmx.common.R

/**
 * Created by lmx on 2021/1/19
 * Describe:底部弹出 dialogFragment
 */
abstract class BottomDialogFragment : androidx.fragment.app.DialogFragment() {

    abstract val mLayoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        // 设置 dialog 动画
        setStyle(STYLE_NO_TITLE, R.style.BottomDialog)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(mLayoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()

        initData()
    }

    abstract fun initView()

    abstract fun initData()

    override fun onStart() {
        super.onStart()
//        // 设置宽高
        val window = dialog!!.window
        val params = window!!.attributes
        params.gravity = Gravity.BOTTOM
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        window.attributes = params

    }

    /**
     *  设置 view 的点击事件
     */
    fun setOnclick(vararg views: View) {
        views.forEach {
            it.setOnClickListener(mOnClickListener)
        }
    }

    private val mOnClickListener = View.OnClickListener {
        onViewClick(it)
    }

    open fun onViewClick(v: View) {

    }

    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return if (isAdded || isRemoving) -1 else super.show(
            transaction,
            tag + System.currentTimeMillis()
        )
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (isAdded || isRemoving) return
        val mClass = DialogFragment::class.java
        val dismissed = mClass.getDeclaredField("mDismissed")
        dismissed.isAccessible = true
        dismissed.set(this, false)

        val shownByMe = mClass.getDeclaredField("mShownByMe")
        shownByMe.isAccessible = true
        shownByMe.set(this, true)
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        if (isAdded || isRemoving) return
        super.showNow(manager, tag + System.currentTimeMillis())
    }

    fun isCanceledOnTouchOutside() {
        dialog!!.setCanceledOnTouchOutside(false)//设置点击弹窗外面不能取消
    }

}