package net.lucode.hackware.magicindicator

import android.content.Context

fun dip2px(context: Context, dpValue: Double): Int {
    val density = context.resources.displayMetrics.density
    return (dpValue * density + 0.5).toInt()
}

fun getScreenWidth(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}