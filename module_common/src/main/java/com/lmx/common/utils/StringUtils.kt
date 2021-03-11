package com.lmx.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Vibrator
import android.text.TextUtils.isEmpty

object StringUtils {

    /**
     * 手机号验证
     */
    @JvmStatic
    fun isPhone(phone: String): Boolean {
        return !isEmpty(phone) && phone.matches("^1[23456789][0-9]\\d{8}$".toRegex())
    }

    /**
     * 设备品牌, eg: Samsung
     */
    @JvmStatic
    fun getVendor(): String? {
        return Build.BRAND
    }

    /**
     * 字符串长度限制
     * maxLen：字节大小
     * ellipsis：是否返回...
     */
    @JvmStatic
    fun getStringMaxLength(name: String, maxLen: Int, ellipsis: Boolean): String {
        if (isEmpty(name)) {
            return name
        }
        var count = 0
        var endIndex = 0
        val length = name.length

        for (i in 0 until length) {
            val item: Char = name[i]
            count = if (item.toInt() < 128) {
                count + 1
            } else {
                count + 2
            }
            if (maxLen == count || item.toInt() >= 128 && maxLen + 1 == count) {
                endIndex = i + 1
            }
        }
        if (endIndex > length) {
            endIndex = length
        }
        return if (count <= maxLen) {
            name
        } else {
            if (ellipsis) {
                name.substring(0, endIndex) + "..."
            } else {
                name.substring(0, endIndex)
            }
        }
    }

    /**
     * 震动一下
     * milliseconds ：时长
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    fun startVibrator(context: Context, milliseconds: Long) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(milliseconds)
    }

}