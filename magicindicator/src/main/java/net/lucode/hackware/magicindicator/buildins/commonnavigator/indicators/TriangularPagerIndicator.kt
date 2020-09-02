package net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData
import net.lucode.hackware.magicindicator.dip2px

/**
 * 带有小尖角的直线指示器
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class TriangularPagerIndicator(context: Context) : View(context), IPagerIndicator {
    private var mPositionDataList: List<PositionData> = mutableListOf()
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var lineHeight = dip2px(context, 3.0)
    var lineColor = 0
    var triangleHeight = dip2px(context, 8.0)
    var triangleWidth = dip2px(context, 14.0)
    var startInterpolator: Interpolator = LinearInterpolator()
    var isReverse = false
    var yOffset = 0f
    private val mPath = Path()

    private var mAnchorX = 0f

    init {
        mPaint.style = Paint.Style.FILL
    }


    override fun onDraw(canvas: Canvas) {
        mPaint.color = lineColor
        if (isReverse) {
            canvas.drawRect(0f, height - yOffset - triangleHeight, width.toFloat(), height - yOffset - triangleHeight + lineHeight, mPaint)
        } else {
            canvas.drawRect(0f, height - lineHeight - yOffset, width.toFloat(), height - yOffset, mPaint)
        }
        mPath.reset()
        if (isReverse) {
            mPath.moveTo(mAnchorX - triangleWidth / 2, height - yOffset - triangleHeight)
            mPath.lineTo(mAnchorX, height - yOffset)
            mPath.lineTo(mAnchorX + triangleWidth / 2, height - yOffset - triangleHeight)
        } else {
            mPath.moveTo(mAnchorX - triangleWidth / 2, height - yOffset)
            mPath.lineTo(mAnchorX, height - triangleHeight - yOffset)
            mPath.lineTo(mAnchorX + triangleWidth / 2, height - yOffset)
        }
        mPath.close()
        canvas.drawPath(mPath, mPaint)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mPositionDataList?.let {
            if (it.isEmpty()) {
                return
            }
            // 计算锚点位置
            val current: PositionData = FragmentContainerHelper.getImitativePositionData(it, position)
            val next: PositionData = FragmentContainerHelper.getImitativePositionData(it, position + 1)
            val leftX = current.mLeft + (current.mRight - current.mLeft) / 2.toFloat()
            val rightX = next.mLeft + (next.mRight - next.mLeft) / 2.toFloat()
            mAnchorX = leftX + (rightX - leftX) * startInterpolator.getInterpolation(positionOffset)
            invalidate()
        }
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPositionDataProvide(dataList: MutableList<PositionData>) {
        mPositionDataList = dataList
    }

}