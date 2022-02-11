package com.jd.permissionrequest

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.jd.permission.PermissionRequester

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.request).setOnClickListener {
            PermissionRequester.init(this)
                .permissionFor(Manifest.permission.RECORD_AUDIO)
                .observeRationale { _, scopedWorker ->
                    Log.d(TAG, "onRationale")
                    AlertDialog.Builder(this@MainActivity)
                        .setMessage("You have to grant permission to use")
                        .setNegativeButton("cancel") { dialog, _ -> dialog.dismiss() }
                        .setPositiveButton("grant") { dialog, _ ->
                            dialog.dismiss()
                            scopedWorker.request()
                        }.show()
                }
                .request { granted, _, disallowed ->
                    Log.d(TAG, "granted: $granted, noMore: $disallowed")
                    if (disallowed)
                        AlertDialog.Builder(this@MainActivity)
                            .setMessage("completely banned")
                            .show()
                }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}