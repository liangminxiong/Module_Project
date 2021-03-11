package com.lmx.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.lmx.common.R

/**
 * Created by lmx on 2021/1/19
 * Describe:
 */
@Suppress("DEPRECATION")
open class LoadingDialog(context: Context, theme: Int) : Dialog(context, theme) {

    private var rotateAnimation: RotateAnimation? = null
    private var ivLoading: ImageView? = null

    constructor(context: Context?) : this(
        context!!,
        R.style.LoadingDialog
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_dialog_loading)
        ivLoading = findViewById(R.id.iv_loading)
        ivLoading?.let { it1 ->
            rotateAnimation = RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotateAnimation?.let {
                it.interpolator = LinearInterpolator()
                it.duration = 1800
                it.repeatCount = -1
                it1.startAnimation(rotateAnimation)
                it.fillAfter = true
            }
        }
    }

    open fun showDialog(msg: String?) {
        try {
            if (!isShowing) {
                show()
            }
            if (!TextUtils.isEmpty(msg)) {
                findViewById<TextView>(R.id.tv_loading).text = msg
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    override fun show() {
        startAnimation()
        super.show()
    }

    override fun dismiss() {
        stopAnimation()
        super.dismiss()
    }

    /**
     * delay 毫秒
     */
    fun showAutoDismiss(msg: String?, delay: Long) {
        showDialog(msg)
        Handler().postDelayed({ dismiss() }, delay)
    }

    private fun startAnimation() {
        ivLoading?.let { it1 ->
            rotateAnimation?.let {
                it1.startAnimation(it)
            }
        }
    }

    private fun stopAnimation() {
        rotateAnimation?.cancel()
    }
}