package net.lucode.hackware.magicindicator

import android.content.Context
import androidx.viewpager.widget.ViewPager

fun dip2px(context: Context, dpValue: Double): Int {
    val density = context.resources.displayMetrics.density
    return (dpValue * density + 0.5).toInt()
}

fun getScreenWidth(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}

//实现颜色渐变，考虑到兼容性，不使用内置的ArgbEvaluator
fun eval(fraction: Float, startValue: Int, endValue: Int): Int {
    val startA = startValue shr 24 and 0xff
    val startR = startValue shr 16 and 0xff
    val startG = startValue shr 8 and 0xff
    val startB = startValue and 0xff
    val endA = endValue shr 24 and 0xff
    val endR = endValue shr 16 and 0xff
    val endG = endValue shr 8 and 0xff
    val endB = endValue and 0xff
    val currentA = startA + (fraction * (endA - startA)).toInt() shl 24
    val currentR = startR + (fraction * (endR - startR)).toInt() shl 16
    val currentG = startG + (fraction * (endG - startG)).toInt() shl 8
    val currentB = startB + (fraction * (endB - startB)).toInt()
    return currentA or currentR or currentG or currentB
}
// 简化和ViewPager绑定
fun bind(magicIndicator: MagicIndicator, viewPager: ViewPager) {
    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            magicIndicator.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            magicIndicator.onPageScrollStateChanged(state)
        }
    })
}