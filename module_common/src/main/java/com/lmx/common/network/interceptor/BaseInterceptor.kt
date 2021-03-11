package com.lmx.common.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by lmx on 2021/1/20
 * Describe: BaseInterceptor
 */
class BaseInterceptor(private val headers: Map<String, String>?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder().run {
            if (!headers.isNullOrEmpty()) {
                for (headMap in headers) {
                    addHeader(headMap.key, headMap.value).build()
                }
            }
            build()
        })
    }
}