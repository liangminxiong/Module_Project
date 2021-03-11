package com.lmx.common.bitmap

import android.graphics.Bitmap
import kotlinx.coroutines.Deferred

/**
 * Created by lmx on 2021/1/20
 * Describe:图片加载任务管理类，防止创建重复任务
 */
object BitmapTaskManager {

    private val taskSet = HashMap<String, Deferred<Bitmap>>()

    fun contains(key: String) = taskSet.contains(key)

    fun add(key: String, task: Deferred<Bitmap>) {
        taskSet[key] = task
    }

    fun get(key: String) = taskSet[key]

    fun remove(key: String) {
        taskSet.remove(key)
    }
}