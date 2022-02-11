package com.jd.permission

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.jd.permission.request.PermissionMediator
import com.jd.permission.utils.check

/**
 * Created by Jacee.
 * Date: 2022.02.11
 */
object PermissionRequester {

    fun init(activity: FragmentActivity) = PermissionMediator(activity)

    fun check(context: Context, permission: String) = context.check(permission)

}