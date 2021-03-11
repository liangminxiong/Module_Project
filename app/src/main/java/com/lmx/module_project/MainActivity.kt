package com.lmx.module_project

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.lmx.common.ConstantValue
import com.lmx.common.base.BaseActivity
import com.lmx.common.base.NoViewModel
import com.lmx.common.ext.logd
import com.lmx.common.widget.NineGridView

class MainActivity : BaseActivity<NoViewModel, ViewDataBinding>() {

    override fun layoutId() = R.layout.app_activity_main

    override fun initView(savedInstanceState: Bundle?) {
        val tvContent = findViewById<TextView>(R.id.tvContent)
        tvContent.text = ConstantValue.URL_BASE
        tvContent.setOnClickListener {
            // 设置话题
            TestActivity.openActivity(this)
        }

        val images = mutableListOf<String>()
        val nineGridView = findViewById<NineGridView>(R.id.nineGridView)
        val adapter = ImageAdapter(this)
        nineGridView.setAdapter(adapter)
        nineGridView.setOnImageClickListener { position, view ->
            "position==$position view = $view".logd()
        }

        findViewById<TextView>(R.id.tvChangeAdapter).setOnClickListener {
            images.add("https://oss.yuliao520.cn/TEST/mm/head/20201110/858a603d-f418-4c5e-8d22-6a059606f9b6.jpg")
            adapter.setImages(images)
            nineGridView.setAdapter(adapter)
        }
    }

    override fun initData() {
//        showLoading(true)
    }

}