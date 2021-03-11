package com.lmx.common.base

/**
 * Created by lmx on 2021/1/20
 * Describe:
 */
interface IBaseResponse<T> {
    fun code(): Int
    fun msg(): String
    fun data(): T
    fun isSuccess(): Boolean
}