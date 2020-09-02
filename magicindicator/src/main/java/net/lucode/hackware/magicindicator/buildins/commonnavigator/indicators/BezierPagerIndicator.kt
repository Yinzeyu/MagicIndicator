package net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData
import net.lucode.hackware.magicindicator.dip2px
import net.lucode.hackware.magicindicator.eval
import java.util.*
import kotlin.math.abs

/**
 * 贝塞尔曲线ViewPager指示器，带颜色渐变
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class BezierPagerIndicator(context: Context) : View(context), IPagerIndicator {
    private var mPositionDataList: MutableList<PositionData> = mutableListOf()
    private var mLeftCircleRadius = 0f
    private var mLeftCircleX = 0f
    private var mRightCircleRadius = 0f
    private var mRightCircleX = 0f
    private var yOffset = dip2px(context, 1.5).toFloat()
    var maxCircleRadius = dip2px(context, 3.5).toFloat()
    var minCircleRadius = dip2px(context, 2.0).toFloat()
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath = Path()
    var mColors: MutableList<Int>? = null
    var startInterpolator: Interpolator = AccelerateInterpolator()
    var endInterpolator: Interpolator = DecelerateInterpolator()

    init {
        mPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(mLeftCircleX, height - yOffset - maxCircleRadius, mLeftCircleRadius, mPaint)
        canvas.drawCircle(mRightCircleX, height - yOffset - maxCircleRadius, mRightCircleRadius, mPaint)
        drawBezierCurve(canvas)
    }

    /**
     * 绘制贝塞尔曲线
     *
     * @param canvas
     */
    private fun drawBezierCurve(canvas: Canvas) {
        mPath.reset()
        val y = height - yOffset - maxCircleRadius
        mPath.moveTo(mRightCircleX, y)
        mPath.lineTo(mRightCircleX, y - mRightCircleRadius)
        mPath.quadTo(mRightCircleX + (mLeftCircleX - mRightCircleX) / 2.0f, y, mLeftCircleX, y - mLeftCircleRadius)
        mPath.lineTo(mLeftCircleX, y + mLeftCircleRadius)
        mPath.quadTo(mRightCircleX + (mLeftCircleX - mRightCircleX) / 2.0f, y, mRightCircleX, y + mRightCircleRadius)
        mPath.close() // 闭合
        canvas.drawPath(mPath, mPaint)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mPositionDataList.isEmpty()) {
            return
        }
        mColors?.let { color ->
            // 计算颜色
            if (color.isNotEmpty()) {
                val currentColor = color[abs(position) % color.size]
                val nextColor = color[abs(position + 1) % color.size]
                mPaint.color = eval(positionOffset, currentColor, nextColor)
            }
        }


        // 计算锚点位置
        val current: PositionData = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position)
        val next: PositionData = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position + 1)
        val leftX = current.mLeft + (current.mRight - current.mLeft) / 2.toFloat()
        val rightX = next.mLeft + (next.mRight - next.mLeft) / 2.toFloat()
        mLeftCircleX = leftX + (rightX - leftX) * startInterpolator.getInterpolation(positionOffset)
        mRightCircleX = leftX + (rightX - leftX) * endInterpolator.getInterpolation(positionOffset)
        mLeftCircleRadius = maxCircleRadius + (minCircleRadius - maxCircleRadius) * endInterpolator.getInterpolation(positionOffset)
        mRightCircleRadius = minCircleRadius + (maxCircleRadius - minCircleRadius) * startInterpolator.getInterpolation(positionOffset)
        invalidate()
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPositionDataProvide(dataList: MutableList<PositionData>) {
        mPositionDataList = dataList
    }

    fun setColors(vararg colors: Int?) {
        mColors = Arrays.asList(*colors)
    }
}