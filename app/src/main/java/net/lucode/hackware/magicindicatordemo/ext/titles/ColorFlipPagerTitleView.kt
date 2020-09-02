package net.lucode.hackware.magicindicatordemo.ext.titles

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * Created by hackware on 2016/7/24.
 */
class ColorFlipPagerTitleView(context: Context) : SimplePagerTitleView(context) {
    var changePercent = 0.5f
    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        if (leavePercent >= changePercent) {
            setTextColor(normalColor)
        } else {
            setTextColor(selectedColor)
        }
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        if (enterPercent >= changePercent) {
            setTextColor(selectedColor)
        } else {
            setTextColor(normalColor)
        }
    }

    override fun onSelected(index: Int, totalCount: Int) {}
    override fun onDeselected(index: Int, totalCount: Int) {}
}