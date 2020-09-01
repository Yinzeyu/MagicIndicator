package net.lucode.hackware.magicindicator.buildins.commonnavigator.model

/**
 * 保存指示器标题的坐标
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class PositionData {
    @kotlin.jvm.JvmField
    var mLeft = 0
    var mTop = 0
    @kotlin.jvm.JvmField
    var mRight = 0
    var mBottom = 0
    @kotlin.jvm.JvmField
    var mContentLeft = 0
    @kotlin.jvm.JvmField
    var mContentTop = 0
    @kotlin.jvm.JvmField
    var mContentRight = 0
    @kotlin.jvm.JvmField
    var mContentBottom = 0
    fun width(): Int {
        return mRight - mLeft
    }

    fun height(): Int {
        return mBottom - mTop
    }

    fun contentWidth(): Int {
        return mContentRight - mContentLeft
    }

    fun contentHeight(): Int {
        return mContentBottom - mContentTop
    }

    fun horizontalCenter(): Int {
        return mLeft + width() / 2
    }

    fun verticalCenter(): Int {
        return mTop + height() / 2
    }
}