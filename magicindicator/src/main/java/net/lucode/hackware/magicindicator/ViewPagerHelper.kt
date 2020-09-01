package net.lucode.hackware.magicindicator

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

/**
 * 简化和ViewPager绑定
 * Created by hackware on 2016/8/17.
 */
object ViewPagerHelper {
    @kotlin.jvm.JvmStatic
    fun bind(magicIndicator: MagicIndicator, viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
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
}