package com.jd.permission.request

/**
 * Created by Jacee.
 * Date: 2022.02.11
 */
interface OnRationaleCallback {

    fun onRationale(permission: String, worker: ScopedWorker)

}