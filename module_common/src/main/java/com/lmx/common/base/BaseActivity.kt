package com.lmx.common.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.lmx.common.R
import com.lmx.common.app.MVVMLmx
import com.lmx.common.dialog.LoadingDialog
import com.lmx.common.event.Message
import com.lmx.common.ext.toast
import java.lang.reflect.ParameterizedType

/**
 * Created by lmx on 2021/1/21
 * Describe:
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: VM

    protected var mBinding: DB? = null

    private var dialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        lifecycle.addObserver(viewModel)
        //注册 UI事件
        registorDefUIChange()
        initView(savedInstanceState)
        initData()
    }

    abstract fun layoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()


    /**
     * DataBinding
     */
    private fun initViewDataBinding() {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.setContentView(this, layoutId())
            mBinding?.lifecycleOwner = this
        } else setContentView(layoutId())
        createViewModel()
    }


    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {

        viewModel.defUI.showDialog.observe(this, androidx.lifecycle.Observer {
            showLoading()
        })
        viewModel.defUI.dismissDialog.observe(this, androidx.lifecycle.Observer {
            dismissLoading()
        })
        viewModel.defUI.toastEvent.observe(this, androidx.lifecycle.Observer {
            this.toast(it)
        })
        viewModel.defUI.msgEvent.observe(this, androidx.lifecycle.Observer {
            handleEvent(it)
        })
    }

    open fun handleEvent(msg: Message) {}

    private fun showLoading() {
        showLoading(true)
    }

    /**
     * 打开等待框
     */
    open fun showLoading(isCancel: Boolean) {
        if (dialog == null && !isFinishing) {
            dialog = LoadingDialog(this)
        }
        dialog?.let {
            if (!it.isShowing) {
                it.setCancelable(isCancel)
                it.show()
            }
        }
    }

    /**
     * 关闭等待框
     */
    open fun dismissLoading() {
        dialog?.run { if (isShowing) dismiss() }
    }


    /**
     * 创建 ViewModel
     */
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel = ViewModelProvider(viewModelStore, defaultViewModelProviderFactory)
                .get(tClass) as VM
        }
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return MVVMLmx.getConfig().viewModelFactory() ?: super.getDefaultViewModelProviderFactory()
    }

    /**
     * 过渡动画
     */
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        rightToLeftAnimation()
    }


    open fun startActivityNoAnimation(intent: Intent?) {
        super.startActivity(intent)
    }

    override fun finish() {
        super.finish()
        leftToRightAnimation()
    }

    open fun finishNoAnimation() {
        super.finish()
    }

    open fun startActivityFade(intent: Intent?) {
        super.startActivity(intent)
        fadeAnimation()
    }

    open fun finishFade() {
        super.finish()
        fadeAnimation()
    }

    open fun rightToLeftAnimation() {
        overridePendingTransition(
            R.anim.activity_right_to_left_enter,
            R.anim.activity_right_to_left_exit
        )
    }

    open fun leftToRightAnimation() {
        overridePendingTransition(
            R.anim.activity_left_to_right_enter,
            R.anim.activity_left_to_right_exit
        )
    }

    open fun fadeAnimation() {
        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }

    /**
     * 从下往上动画
     */
    open fun bottomToTopAnimation() {
        overridePendingTransition(R.anim.bottom_start, R.anim.bottom_start)
    }

    /**
     * 从下往上动画
     */
    open fun finishTopToBottom() {
        super.finish()
        overridePendingTransition(R.anim.bottom_end, R.anim.bottom_end)
    }

    /**
     * 设置权限组
     */
    open fun getPermissions(vararg permissions: String?) {
        val xxPermissions = XXPermissions.with(this)
        //.permission(Permission.REQUEST_INSTALL_PACKAGES)
        // 申请悬浮窗权限
        //.permission(Permission.SYSTEM_ALERT_WINDOW)
        // 申请通知栏权限
        //.permission(Permission.NOTIFICATION_SERVICE)
        // 申请系统设置权限
        //.permission(Permission.WRITE_SETTINGS)
        // 申请单个权限
        xxPermissions.permission(permissions) // 申请多个权限
        requestPermissions(xxPermissions)
    }

    private fun requestPermissions(xxPermissions: XXPermissions) {
        xxPermissions.request(object : OnPermissionCallback {
            @SuppressLint("ShowToast")
            override fun onGranted(permissions: List<String>, all: Boolean) {
                if (all) {
                    hasAllPermissions()
                } else {
                    showToast("获取部分权限成功，但部分权限未正常授予")
                }
            }

            override fun onDenied(permissions: List<String>, never: Boolean) {
                if (never) {
//                            toast("被永久拒绝授权，请手动授予录音和日历权限");
                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
//                        XXPermissions.startPermissionActivity(
//                            this@BaseActivity,
//                            permissions
//                        )
                    showToast("被永久拒绝授权，请手动授予权限")
                } else {
                    showToast("获取权限失败，权限未正常授予")
                }
            }
        })
    }

    /**
     * 设置权限组
     */
    fun permission(permissions: MutableList<String?>) {
        val xxPermissions = XXPermissions.with(this)
        //.permission(Permission.REQUEST_INSTALL_PACKAGES)
        // 申请悬浮窗权限
        //.permission(Permission.SYSTEM_ALERT_WINDOW)
        // 申请通知栏权限
        //.permission(Permission.NOTIFICATION_SERVICE)
        // 申请系统设置权限
        //.permission(Permission.WRITE_SETTINGS)
        // 申请单个权限
        xxPermissions.permission(permissions) // 申请多个权限
        requestPermissions(xxPermissions)
    }

    /**是否拥有全部权限*/
    fun hasAllPermissions() {

    }

    /**
     * 吐司
     */
    open fun showToast(msg: String?) {
        msg?.let {
            toast(it)
        }
    }

    //设置字体为默认大小，不随系统字体大小改而改变
    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1f) //非默认值
            resources
        super.onConfigurationChanged(newConfig)
    }
}