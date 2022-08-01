package com.demo.cointrackerapp.utils

import android.app.ActivityManager
import android.content.Context



@Suppress("DEPRECATION")
fun Context.isMyServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.getRunningServices(Integer.MAX_VALUE)
        .any { it.service.className == serviceClass.name }
}