package com.jd.permission.request

import androidx.fragment.app.FragmentActivity

/**
 * Created by Jacee.
 * Date: 2022.02.11
 */
class PermissionMediator(private val activity: FragmentActivity) {

    fun permissionFor(permissions: String): Requester {
        return Requester(activity, permissions)
    }

}