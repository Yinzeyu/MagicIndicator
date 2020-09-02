package net.lucode.hackware.magicindicator.buildins.commonnavigator.abs

import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData

/**
 * 抽象的viewpager指示器，适用于CommonNavigator
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
interface IPagerIndicator {
    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    fun onPageSelected(position: Int)
    fun onPageScrollStateChanged(state: Int)
    fun onPositionDataProvide(dataList: MutableList<PositionData>)
}