package net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData

/**
 * 用于测试的指示器，可用来检测自定义的IMeasurablePagerTitleView是否正确测量内容区域
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class TestPagerIndicator(context: Context) : View(context), IPagerIndicator {
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var outRectColor  = Color.RED
    var innerRectColor = Color.GREEN
    private val mOutRect = RectF()
    private val mInnerRect = RectF()
    private var mPositionDataList: MutableList<PositionData> = mutableListOf()
    init {
        mPaint.style = Paint.Style.STROKE
    }
    override fun onDraw(canvas: Canvas) {
        mPaint.color = outRectColor
        canvas.drawRect(mOutRect, mPaint)
        mPaint.color = innerRectColor
        canvas.drawRect(mInnerRect, mPaint)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mPositionDataList?.let {
            if (it.isEmpty()) {
                return
            }
            // 计算锚点位置
            val current: PositionData = FragmentContainerHelper.getImitativePositionData(it, position)
            val next: PositionData = FragmentContainerHelper.getImitativePositionData(it, position + 1)
            mOutRect.left = current.mLeft + (next.mLeft - current.mLeft) * positionOffset
            mOutRect.top = current.mTop + (next.mTop - current.mTop) * positionOffset
            mOutRect.right = current.mRight + (next.mRight - current.mRight) * positionOffset
            mOutRect.bottom = current.mBottom + (next.mBottom - current.mBottom) * positionOffset
            mInnerRect.left = current.mContentLeft + (next.mContentLeft - current.mContentLeft) * positionOffset
            mInnerRect.top = current.mContentTop + (next.mContentTop - current.mContentTop) * positionOffset
            mInnerRect.right = current.mContentRight + (next.mContentRight - current.mContentRight) * positionOffset
            mInnerRect.bottom = current.mContentBottom + (next.mContentBottom - current.mContentBottom) * positionOffset
            invalidate()
        }
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPositionDataProvide(dataList: MutableList<PositionData>) {
        mPositionDataList = dataList
    }
}