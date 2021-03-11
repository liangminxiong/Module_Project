package com.lmx.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lmx.common.ext.dp2px

/**
 * Created by lmx on 2019/12/26
 * Describe:View工具类
 */
object ViewUtils {

    /**
     * TextView 两边边的图片
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    fun setTextViewImage(context: Context, textView: TextView, imageIdStart: Int, imageIdEnd: Int) {
        if (imageIdEnd == 0 && imageIdStart == 0) {
            textView.setCompoundDrawables(null, null, null, null)// 设置到控件中
            return
        }
        var drawableStart: Drawable? = null
        var drawableEnd: Drawable? = null
        if (imageIdStart != 0) {
            drawableStart = context.resources.getDrawable(imageIdStart)// 找到资源图片
            drawableStart.setBounds(
                0,
                0,
                drawableStart.minimumWidth,
                drawableStart.minimumHeight
            )// 设置图片宽高
        }
        if (imageIdEnd != 0) {
            drawableEnd = context.resources.getDrawable(imageIdEnd)// 找到资源图片
            drawableEnd.setBounds(
                0,
                0,
                drawableEnd.minimumWidth,
                drawableEnd.minimumHeight
            )// 设置图片宽高
        }
        textView.setCompoundDrawables(drawableStart, null, drawableEnd, null)// 设置到控件中
    }

    /**
     * TextView 右边图片
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    fun setTextViewEndImage(context: Context, textView: TextView, imageId: Int) {
        if (imageId == 0) {
            textView.setCompoundDrawables(null, null, null, null)// 设置到控件中
            return
        }
        val drawable = context.resources.getDrawable(imageId)// 找到资源图片
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)// 设置图片宽高
        textView.setCompoundDrawables(null, null, drawable, null)// 设置到控件中
    }

    /**
     * TextView 左边图片
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    fun setTextViewStartImage(context: Context, textView: TextView, imageId: Int) {
        if (imageId == 0) {
            textView.setCompoundDrawables(null, null, null, null)// 设置到控件中
            return
        }
        val drawable = context.resources.getDrawable(imageId)// 找到资源图片
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)// 设置图片宽高
        textView.setCompoundDrawables(drawable, null, null, null)// 设置到控件中

    }

    /**
     * TextView 上边图片
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    fun setTextViewTopImage(context: Context, textView: TextView, imageId: Int) {
        if (imageId == 0) {
            textView.setCompoundDrawables(null, null, null, null)// 设置到控件中
            return
        }
        val drawable = context.resources.getDrawable(imageId)// 找到资源图片
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)// 设置图片宽高
        textView.setCompoundDrawables(null, drawable, null, null)// 设置到控件中
    }

    /**
     * TextView 底部图片
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    fun setTextViewBottomImage(context: Context, textView: TextView, imageId: Int) {
        if (imageId == 0) {
            textView.setCompoundDrawables(null, null, null, null)// 设置到控件中
            return
        }
        val drawable = context.resources.getDrawable(imageId)// 找到资源图片
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)// 设置图片宽高
        textView.setCompoundDrawables(null, null, null, drawable)// 设置到控件中

    }

    /**
     * 设置ImageView大小
     */
    @JvmStatic
    fun setImageViewWH(context: Context, imageView: ImageView, width: Int, height: Int) {
        val layoutParams = imageView.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        imageView.layoutParams = layoutParams
    }

    /**
     * 设置TextView大小
     */
    @JvmStatic
    fun setTextViewViewWH(context: Context, textView: TextView, width: Int, height: Int) {
        val layoutParams = textView.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        textView.layoutParams = layoutParams
    }

    /**
     * 设置EditText大小
     */
    @JvmStatic
    fun setEditTextViewWH(context: Context, imageView: EditText, width: Int, height: Int) {
        val layoutParams = imageView.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        imageView.layoutParams = layoutParams
    }

    /**
     * 设置EditText大小
     */
    @JvmStatic
    fun setRecyclerViewWH(
        context: Context,
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        width: Int,
        height: Int
    ) {
        val layoutParams = recyclerView.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        recyclerView.layoutParams = layoutParams
    }

    /**
     * 获取控件的高度
     */
    @JvmStatic
    fun getViewMeasuredHeight(view: View): Int {
        calculateViewMeasure(view)
        return view.measuredHeight
    }

    /**
     * 获取控件的宽度
     */
    @JvmStatic
    fun getViewMeasuredWidth(view: View): Int {
        calculateViewMeasure(view)
        return view.measuredWidth
    }

    /**
     * 测量控件的尺寸
     */
    private fun calculateViewMeasure(view: View) {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
    }

    /**
     * 设置文本空隙
     */
    @JvmStatic
    fun setTextViewPadding(
        context: Context,
        textView: TextView,
        start: Int,
        top: Int,
        end: Int,
        bottom: Int
    ) {
        textView.setPadding(
            context.dp2px(start),
            context.dp2px(top),
            context.dp2px(end),
            context.dp2px(bottom)
        )
    }

    /**
     * 设置ConstraintLayout大小
     */
    @JvmStatic
    fun setConstraintLayoutWH(context: Context, view: ConstraintLayout, width: Int, height: Int) {
        val layoutParams = view.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        view.layoutParams = layoutParams
    }

    /**
     * 设置ConstraintLayout大小
     */
    @JvmStatic
    fun setConstraintLayoutW(context: Context, view: ConstraintLayout, width: Int) {
        val layoutParams = view.layoutParams
        layoutParams.width = width
        view.layoutParams = layoutParams
    }

    /**
     * 获取通知栏高度
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getStatusHeigh(context: Activity): Int {
        val rectangle = Rect()
        context.window.decorView.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }
}