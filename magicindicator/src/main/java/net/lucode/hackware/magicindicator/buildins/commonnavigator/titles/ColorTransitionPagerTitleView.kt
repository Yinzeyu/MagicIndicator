package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles

import android.content.Context
import net.lucode.hackware.magicindicator.eval

/**
 * 两种颜色过渡的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
open class ColorTransitionPagerTitleView(context: Context) : SimplePagerTitleView(context) {
    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        setTextColor(eval(leavePercent, selectedColor, normalColor))
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        setTextColor(eval(enterPercent, normalColor, selectedColor))
    }

    override fun onSelected(index: Int, totalCount: Int) {}
    override fun onDeselected(index: Int, totalCount: Int) {}
}