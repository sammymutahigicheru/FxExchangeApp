package com.dvt.core.helpers

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.Window
import android.view.WindowManager

fun changeActivityStatusBarColor(activity: Activity){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window: Window = activity.window
        window.statusBarColor = Color.TRANSPARENT

    } else {
        val window: Window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT
    }
}