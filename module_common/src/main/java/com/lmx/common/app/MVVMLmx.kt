package com.lmx.common.app

/**
 * Created by lmx on 2021/1/20
 * Describe:
 */
object MVVMLmx {

    private val DEFULT = object : GlobalConfig {}

    private var mConfig: GlobalConfig = DEFULT

    fun install(config: GlobalConfig) {
        mConfig = config
    }

    fun getConfig() = mConfig

}