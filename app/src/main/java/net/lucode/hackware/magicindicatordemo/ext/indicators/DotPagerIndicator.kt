package net.lucode.hackware.magicindicatordemo.ext.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData
import net.lucode.hackware.magicindicator.dip2px

/**
 * 非手指跟随的小圆点指示器
 * Created by hackware on 2016/7/13.
 */
class DotPagerIndicator(context: Context) : View(context), IPagerIndicator {
    private var mDataList: List<PositionData>? = null
    private var mRadius: Float
    private var mYOffset: Float
    private var mDotColor: Int
    private var mCircleCenterX = 0f
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onPageSelected(position: Int) {
        mDataList?.let {
            if (it.isEmpty()) {
                return
            }
            val data = it[position]
            mCircleCenterX = data.mLeft + data.width() / 2.toFloat()
            invalidate()
        }

    }

    override fun onPositionDataProvide(dataList: List<PositionData>?) {
        mDataList = dataList
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = mDotColor
        canvas.drawCircle(mCircleCenterX, height - mYOffset - mRadius, mRadius, mPaint)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    var radius: Float
        get() = mRadius
        set(radius) {
            mRadius = radius
            invalidate()
        }
    var yOffset: Float
        get() = mYOffset
        set(yOffset) {
            mYOffset = yOffset
            invalidate()
        }
    var dotColor: Int
        get() = mDotColor
        set(dotColor) {
            mDotColor = dotColor
            invalidate()
        }

    init {
        mRadius = dip2px(context, 3.0).toFloat()
        mYOffset = dip2px(context, 3.0).toFloat()
        mDotColor = Color.WHITE
    }
}