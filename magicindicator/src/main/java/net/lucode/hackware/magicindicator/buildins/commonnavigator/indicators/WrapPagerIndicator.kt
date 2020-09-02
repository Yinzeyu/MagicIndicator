package net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData
import net.lucode.hackware.magicindicator.dip2px

/**
 * 包裹住内容区域的指示器，类似天天快报的切换效果，需要和IMeasurablePagerTitleView配合使用
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class WrapPagerIndicator(context: Context) : View(context), IPagerIndicator {
    private var verticalPadding =dip2px(context, 6.0)
    private var horizontalPadding = dip2px(context, 10.0)
    var fillColor = 0
    private var mRoundRadius = 0f
    private var mStartInterpolator: Interpolator? = LinearInterpolator()
    private var mEndInterpolator: Interpolator? = LinearInterpolator()
    private var mPositionDataList: List<PositionData>? = null
    var paint: Paint =  Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRect = RectF()
    private var mRoundRadiusSet = false
    init {
        paint.style = Paint.Style.FILL
    }
    override fun onDraw(canvas: Canvas) {
        paint.color = fillColor
        canvas.drawRoundRect(mRect, mRoundRadius, mRoundRadius, paint)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mPositionDataList?.let {
            if (it.isEmpty()) {
                return
            }
            // 计算锚点位置
            val current: PositionData = FragmentContainerHelper.getImitativePositionData(it, position)
            val next: PositionData = FragmentContainerHelper.getImitativePositionData(it, position + 1)
            mRect.left = current.mContentLeft - horizontalPadding + (next.mContentLeft - current.mContentLeft) * mEndInterpolator!!.getInterpolation(positionOffset)
            mRect.top = current.mContentTop - verticalPadding.toFloat()
            mRect.right = current.mContentRight + horizontalPadding + (next.mContentRight - current.mContentRight) * mStartInterpolator!!.getInterpolation(positionOffset)
            mRect.bottom = current.mContentBottom + verticalPadding.toFloat()
            if (!mRoundRadiusSet) {
                mRoundRadius = mRect.height() / 2
            }
            invalidate()
        }
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPositionDataProvide(dataList: List<PositionData>?) {
        mPositionDataList = dataList
    }

    var roundRadius: Float
        get() = mRoundRadius
        set(roundRadius) {
            mRoundRadius = roundRadius
            mRoundRadiusSet = true
        }
    var startInterpolator: Interpolator?
        get() = mStartInterpolator
        set(startInterpolator) {
            mStartInterpolator = startInterpolator
            if (mStartInterpolator == null) {
                mStartInterpolator = LinearInterpolator()
            }
        }
    var endInterpolator: Interpolator?
        get() = mEndInterpolator
        set(endInterpolator) {
            mEndInterpolator = endInterpolator
            if (mEndInterpolator == null) {
                mEndInterpolator = LinearInterpolator()
            }
        }


}