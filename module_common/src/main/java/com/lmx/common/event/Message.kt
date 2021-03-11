package com.lmx.common.event

/**
 * Created by lmx on 2021/1/20
 * Describe:
 */
class Message @JvmOverloads constructor(
    var code: Int = 0,
    var msg: String = "",
    var arg1: Int = 0,
    var arg2: Int = 0,
    var obj: Any? = null
)