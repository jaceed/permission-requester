package com.jd.permission.request

import androidx.fragment.app.FragmentActivity
import com.jd.permission.fragment.InvisibleFragment

/**
 * Created by Jacee.
 * Date: 2022.02.11
 */
class Requester(private val activity: FragmentActivity, private val permission: String) : InvisibleFragment.OnHandleInteraction {

    private var onRationaleCallback: OnRationaleCallback? = null

    private val invisibleFragment: InvisibleFragment
        get() {
            val fragment = activity.supportFragmentManager.findFragmentByTag(INVISIBLE_TAG) as? InvisibleFragment
            if (fragment != null) {
                return fragment
            }
            val newFragment = InvisibleFragment()
            activity.supportFragmentManager.beginTransaction()
                .add(newFragment, INVISIBLE_TAG)
                .commitNowAllowingStateLoss()
            return newFragment
        }

    fun observeRationale(onRationaleCallback: OnRationaleCallback): Requester {
        this.onRationaleCallback = onRationaleCallback
        return this
    }

    fun observeRationale(rationale: (String, ScopedWorker) -> Unit): Requester {
        this.onRationaleCallback = object : OnRationaleCallback {
            override fun onRationale(permission: String, worker: ScopedWorker) {
                rationale(permission, worker)
            }
        }
        return this
    }

    fun request(onRequestPermissionResult: OnRequestPermissionResult? = null) {
        invisibleFragment.request(permission, this, onRationaleCallback, onRequestPermissionResult)
    }

    fun request(result: (granted: Boolean, permission: String, disallowed: Boolean) -> Unit) {
        invisibleFragment.request(permission, this, onRationaleCallback, object : OnRequestPermissionResult {
            override fun onRequested(granted: Boolean, permission: String, disallowed: Boolean) {
                result(granted, permission, disallowed)
            }
        })
    }

    override fun onFinish() {
        val fragment = activity.supportFragmentManager.findFragmentByTag(INVISIBLE_TAG) as? InvisibleFragment ?: return
        activity.supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commitNowAllowingStateLoss()
    }

    companion object {
        private const val INVISIBLE_TAG = "tag_invisible_fragment_of_permission"
    }

}