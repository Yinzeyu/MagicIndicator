package net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData
import net.lucode.hackware.magicindicator.dip2px
import java.util.*
import kotlin.math.abs

/**
 * 直线viewpager指示器，带颜色渐变
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class LinePagerIndicator(context: Context) : View(context), IPagerIndicator {
    private var mMode = 0 // 默认为MODE_MATCH_EDGE模式


    // 控制动画
    private var mStartInterpolator: Interpolator? = LinearInterpolator()
    private var mEndInterpolator: Interpolator? = LinearInterpolator()
    var yOffset = 0f// 相对于底部的偏移量，如果你想让直线位于title上方，设置它即可

    var lineHeight = 0f
    var xOffset = 0f
    var lineWidth = 0f
    var roundRadius = 0f
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private set
    private var mPositionDataList: List<PositionData>? = null
    var colors: MutableList<Int>? = null
    private val mLineRect = RectF()

    init {
        paint.style = Paint.Style.FILL
        lineHeight = dip2px(context, 3.0).toFloat()
        lineWidth = dip2px(context, 10.0).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(mLineRect, roundRadius, roundRadius, paint)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mPositionDataList?.let {
            if (it.isEmpty()) {
                return
            }
            colors?.let { color ->
                // 计算颜色
                if (it.isNotEmpty()) {
                    val currentColor = color[abs(position) % color.size]
                    val nextColor = color[abs(position + 1) % color.size]
                    paint.color = ArgbEvaluatorHolder.eval(positionOffset, currentColor, nextColor)
                }
            }
            // 计算锚点位置
            val current: PositionData = FragmentContainerHelper.getImitativePositionData(it, position)
            val next: PositionData = FragmentContainerHelper.getImitativePositionData(it, position + 1)
            val leftX: Float
            val nextLeftX: Float
            val rightX: Float
            val nextRightX: Float
            when (mMode) {
                MODE_MATCH_EDGE -> {
                    leftX = current.mLeft + xOffset
                    nextLeftX = next.mLeft + xOffset
                    rightX = current.mRight - xOffset
                    nextRightX = next.mRight - xOffset
                }
                MODE_WRAP_CONTENT -> {
                    leftX = current.mContentLeft + xOffset
                    nextLeftX = next.mContentLeft + xOffset
                    rightX = current.mContentRight - xOffset
                    nextRightX = next.mContentRight - xOffset
                }
                else -> {    // MODE_EXACTLY
                    leftX = current.mLeft + (current.width() - lineWidth) / 2
                    nextLeftX = next.mLeft + (next.width() - lineWidth) / 2
                    rightX = current.mLeft + (current.width() + lineWidth) / 2
                    nextRightX = next.mLeft + (next.width() + lineWidth) / 2
                }
            }
            mLineRect.left = leftX + (nextLeftX - leftX) * mStartInterpolator!!.getInterpolation(positionOffset)
            mLineRect.right = rightX + (nextRightX - rightX) * mEndInterpolator!!.getInterpolation(positionOffset)
            mLineRect.top = height - lineHeight - yOffset
            mLineRect.bottom = height - yOffset
            invalidate()
        }
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPositionDataProvide(dataList: List<PositionData>?) {
        mPositionDataList = dataList
    }

    var mode: Int
        get() = mMode
        set(mode) {
            mMode = if (mode == MODE_EXACTLY || mode == MODE_MATCH_EDGE || mode == MODE_WRAP_CONTENT) {
                mode
            } else {
                throw IllegalArgumentException("mode $mode not supported.")
            }
        }

    fun setColors(vararg colors: Int?) {
        this.colors = Arrays.asList(*colors)
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

    companion object {
        const val MODE_MATCH_EDGE = 0 // 直线宽度 == title宽度 - 2 * mXOffset
        const val MODE_WRAP_CONTENT = 1 // 直线宽度 == title内容宽度 - 2 * mXOffset
        const val MODE_EXACTLY = 2 // 直线宽度 == mLineWidth
    }


}