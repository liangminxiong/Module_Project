package com.lmx.common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import org.litepal.LitePal

/**
 * Created by lmx on 2021/1/19
 * Describe: base application
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
        Utils.init(this)
        LitePal.initialize(this)
    }
}