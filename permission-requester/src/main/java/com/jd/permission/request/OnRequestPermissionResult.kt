package com.jd.permission.request

/**
 * Created by Jacee.
 * Date: 2022.02.11
 */
interface OnRequestPermissionResult {

    fun onRequested(granted: Boolean, permission: String, disallowed: Boolean)

}