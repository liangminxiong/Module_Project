package com.lmx.common.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.lmx.common.app.MVVMLmx
import com.lmx.common.dialog.LoadingDialog
import com.lmx.common.event.Message
import com.lmx.common.ext.toast
import java.lang.reflect.ParameterizedType

/**
 * Created by lmx on 2021/1/21
 * Describe:
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel: VM

    protected var mBinding: DB? = null

    //是否第一次加载
    private var isFirst: Boolean = true

    private var dialog: LoadingDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
            return mBinding?.root
        }
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
        createViewModel()
        lifecycle.addObserver(viewModel)
        //注册 UI事件
        registorDefUIChange()
        initView(savedInstanceState)
    }

    open fun initView(savedInstanceState: Bundle?) {}

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    abstract fun layoutId(): Int

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        viewModel.defUI.showDialog.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            showLoading()
        })
        viewModel.defUI.dismissDialog.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            dismissLoading()
        })
        viewModel.defUI.toastEvent.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            context?.toast(it)
        })
        viewModel.defUI.msgEvent.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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
    private fun showLoading(isCancel: Boolean) {
        if (dialog == null && !activity?.isFinishing!!) {
            dialog = LoadingDialog(context)
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
    private fun dismissLoading() {
        dialog?.run { if (isShowing) dismiss() }
    }

    /**
     * 创建 ViewModel
     *
     * 共享 ViewModel的时候，重写  Fragmnt 的 getViewModelStore() 方法，
     * 返回 activity 的  ViewModelStore 或者 父 Fragmnt 的 ViewModelStore
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
            toast(activity!!,it)
        }
    }

}