package net.lucode.hackware.magicindicator.buildins.commonnavigator.abs

/**
 * 可测量内容区域的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
interface IMeasurablePagerTitleView : IPagerTitleView {
    val contentLeft: Int
    val contentTop: Int
    val contentRight: Int
    val contentBottom: Int
}