package com.lmx.common.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

/**
 * Created by lmx on 2019/12/26
 * Describe:View工具类
 */
object ScreenUtil {

    private var sNoncompatDensity: Float = 0f
    private var sNoncompatScaleDensity: Float = 0f

    @JvmStatic
    fun setCustomDensity(activity: Activity, application: Application) {
        val appDisplayMetrics = application.resources.displayMetrics

        if (sNoncompatDensity == 0f) {
            sNoncompatDensity = appDisplayMetrics.density
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onLowMemory() {
                }

                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig.fontScale > 0) {
                        sNoncompatScaleDensity = application.resources.displayMetrics.scaledDensity
                    }
                }


            })
        }

        val targetDensity: Float = appDisplayMetrics.widthPixels / 375f
        val targetScaleDensity: Float = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity)
        val targetDensityDpi: Int = (160 * targetDensity).toInt()

        appDisplayMetrics.run {
            density = targetDensity
            scaledDensity = targetScaleDensity
            densityDpi = targetDensityDpi
        }

        activity.resources.displayMetrics.run {
            density = targetDensity
            scaledDensity = targetScaleDensity
            densityDpi = targetDensityDpi
        }
    }


    //获取view x坐标
    @JvmStatic
    fun getX(v: View?): Float {
        return (if (v != null) {
            v.left + getParentX(v.parent)
        } else 0) as Float
    }

    //获取view x坐标
    @JvmStatic
    fun getParentX(parent: ViewParent?): Float {
        if (parent != null && parent is ViewGroup) {
            return parent.left + getParentX(parent.getParent())
        }
        return 0f
    }

    //获取view y坐标
    @JvmStatic
    fun getY(v: View?, mActivity: Activity): Float {
        if (v != null) {
            return v.top + getParentY(v.parent) - getSystemBarHeight(mActivity)
        }
        return 0f
    }

    //获取view bottom坐标
    @JvmStatic
    fun getBottom(v: View?, mActivity: Activity): Float {
        if (v != null) {
            return v.bottom + getParentY(v.parent) - getSystemBarHeight(mActivity)
        }
        return 0f
    }

    @JvmStatic
    fun getParentY(parent: ViewParent?): Float {
        if (parent != null && parent is ViewGroup) {
            return parent.top + getParentY(parent.getParent())
        }
        return 0f
    }

    //获取bar height
    @JvmStatic
    fun getSystemBarHeight(mActivity: Activity): Float {
        val rectangle = Rect()
        mActivity.window.decorView.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top.toFloat()
    }

    //获取可见view bottom坐标
    @JvmStatic
    fun getVisibleBottom(v: View?): Int {
        val globalRect = Rect()
        if (v != null) {
            v.getGlobalVisibleRect(globalRect)
            return v.bottom
        }
        return 0
    }
}