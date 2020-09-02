package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView

/**
 * 通用的指示器标题，子元素内容由外部提供，事件回传给外部
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/7/3.
 */
class CommonPagerTitleView(context: Context) : FrameLayout(context), IMeasurablePagerTitleView {
    var onPagerTitleChangeListener: OnPagerTitleChangeListener? = null
    var contentPositionDataProvider: ContentPositionDataProvider? = null
    override fun onSelected(index: Int, totalCount: Int) {
        onPagerTitleChangeListener?.onSelected(index, totalCount)
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        onPagerTitleChangeListener?.onDeselected(index, totalCount)
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        onPagerTitleChangeListener?.onLeave(index, totalCount, leavePercent, leftToRight)
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        onPagerTitleChangeListener?.onEnter(index, totalCount, enterPercent, leftToRight)
    }

    override val contentLeft: Int = contentPositionDataProvider?.getContentLeft() ?: left

    override val contentTop: Int = contentPositionDataProvider?.getContentTop() ?: top

    override val contentRight: Int = contentPositionDataProvider?.getContentRight() ?: right
    override val contentBottom: Int = contentPositionDataProvider?.getContentBottom() ?: bottom

    /**
     * 外部直接将布局设置进来
     *
     * @param contentView
     */
    fun setContentView(contentView: View) {
        setContentView(contentView, null)
    }

    fun setContentView(contentView: View, lp: LayoutParams?) {
        var ll = lp
        removeAllViews()
        if (ll == null) {
            ll = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        addView(contentView, ll)
    }

    fun setContentView(layoutId: Int) {
        val child = LayoutInflater.from(context).inflate(layoutId, null)
        setContentView(child, null)
    }

    interface OnPagerTitleChangeListener {
        fun onSelected(index: Int, totalCount: Int)
        fun onDeselected(index: Int, totalCount: Int)
        fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean)
        fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean)
    }

    interface ContentPositionDataProvider {
        fun getContentLeft(): Int
        fun getContentTop(): Int
        fun getContentRight(): Int
        fun getContentBottom(): Int
    }
}