package com.lmx.common.app

import androidx.lifecycle.ViewModelProvider
import com.lmx.common.base.ViewModelFactory
import com.lmx.common.network.ExceptionHandle

/**
 * Created by lmx on 2021/1/20
 * Describe:
 */
interface GlobalConfig {

    fun viewModelFactory(): ViewModelProvider.Factory? = ViewModelFactory.getInstance()

    fun globalExceptionHandle(e: Throwable) = ExceptionHandle.handleException(e)

}