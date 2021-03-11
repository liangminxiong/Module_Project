package com.lmx.common.ext

import android.util.Log
import com.lmx.common.BuildConfig

const val TAG = "LMX"
const val TAG_ERROR = "LMX_ERROR"

var showLog = BuildConfig.DEBUG
/**
 * Created by lmx on 2019/12/26
 * Describe:
 */
private enum class LEVEL {
    V, D, I, W, E
}

fun String.logv(tag: String = TAG) =
    log(
        LEVEL.V,
        tag,
        this
    )

fun String.logd(tag: String = TAG) =
    log(
        LEVEL.D,
        tag,
        this
    )

fun String.logi(tag: String = TAG) =
    log(
        LEVEL.I,
        tag,
        this
    )

fun String.logw(tag: String = TAG_ERROR) =
    log(
        LEVEL.W,
        tag,
        this
    )

fun String.loge(tag: String = TAG) =
    log(
        LEVEL.E,
        tag,
        this
    )

private fun log(level: LEVEL, tag: String, message: String) {
    if (!showLog) return
    when (level) {
        LEVEL.V -> Log.v(tag, message)
        LEVEL.D -> Log.d(tag, message)
        LEVEL.I -> Log.i(tag, message)
        LEVEL.W -> Log.w(tag, message)
        LEVEL.E -> Log.e(tag, message)
    }
}