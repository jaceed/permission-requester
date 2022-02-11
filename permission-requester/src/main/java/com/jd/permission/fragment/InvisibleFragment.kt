package com.jd.permission.fragment

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.jd.permission.request.OnRationaleCallback
import com.jd.permission.request.OnRequestPermissionResult
import com.jd.permission.request.ScopedWorker
import com.jd.permission.utils.check

/**
 * Created by Jacee.
 * Date: 2022.02.11
 */
class InvisibleFragment: Fragment(), ScopedWorker {

    private lateinit var permission: String
    private lateinit var onHandleInteraction: OnHandleInteraction
    private var onRationaleCallback: OnRationaleCallback? = null
    private var onRequestPermissionResult: OnRequestPermissionResult? = null

    private val requestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        handleRequestResult(it)
    }

    private fun handleRequestResult(granted: Boolean) {
        onRequestPermissionResult?.onRequested(granted, permission, !granted && !requireActivity().shouldShowRequestPermissionRationale(permission))
        onHandleInteraction.onFinish()
    }

    override fun request() {
        requestLauncher.launch(permission)
    }

    fun request(permission: String, handleInteraction: OnHandleInteraction, rationaleCallback: OnRationaleCallback?, permissionResult: OnRequestPermissionResult?) {
        this.permission = permission
        onHandleInteraction = handleInteraction
        onRationaleCallback = rationaleCallback
        onRequestPermissionResult = permissionResult

        onRationaleCallback?.takeIf {
            !requireContext().check(permission) && requireActivity().shouldShowRequestPermissionRationale(permission)
        }?.onRationale(permission, this) ?: run {
            request()
        }
    }


    interface OnHandleInteraction {
        fun onFinish()
    }

}