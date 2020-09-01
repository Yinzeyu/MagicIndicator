package net.lucode.hackware.magicindicatordemo.ext.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import net.lucode.hackware.magicindicator.FragmentContainerHelper.Companion.getImitativePositionData
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData

/**
 * 通用的indicator，支持外面设置Drawable
 * Created by hackware on 2016/11/14.
 */
class CommonPagerIndicator(context: Context?) : View(context), IPagerIndicator {
    private var mMode // 默认为MODE_MATCH_EDGE模式
            = 0
    var indicatorDrawable: Drawable? = null

    // 控制动画
    var startInterpolator: Interpolator = LinearInterpolator()
    var endInterpolator: Interpolator = LinearInterpolator()
    var drawableHeight = 0f
    var drawableWidth = 0f
    var yOffset = 0f
    var xOffset = 0f
    private var mPositionDataList: List<PositionData>? = null
    private val mDrawableRect = Rect()
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (indicatorDrawable == null) {
            return
        }
        mPositionDataList?.let {
            if (it.isEmpty()) {
                return
            }
            // 计算锚点位置
            val current = getImitativePositionData(it, position)
            val next = getImitativePositionData(it, position + 1)
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
                    mDrawableRect.top = yOffset.toInt()
                    mDrawableRect.bottom = (height - yOffset).toInt()
                }
                MODE_WRAP_CONTENT -> {
                    leftX = current.mContentLeft + xOffset
                    nextLeftX = next.mContentLeft + xOffset
                    rightX = current.mContentRight - xOffset
                    nextRightX = next.mContentRight - xOffset
                    mDrawableRect.top = (current.mContentTop - yOffset).toInt()
                    mDrawableRect.bottom = (current.mContentBottom + yOffset).toInt()
                }
                else -> {    // MODE_EXACTLY
                    leftX = current.mLeft + (current.width() - drawableWidth) / 2
                    nextLeftX = next.mLeft + (next.width() - drawableWidth) / 2
                    rightX = current.mLeft + (current.width() + drawableWidth) / 2
                    nextRightX = next.mLeft + (next.width() + drawableWidth) / 2
                    mDrawableRect.top = (height - drawableHeight - yOffset).toInt()
                    mDrawableRect.bottom = (height - yOffset).toInt()
                }
            }
            mDrawableRect.left = (leftX + (nextLeftX - leftX) * startInterpolator.getInterpolation(positionOffset)).toInt()
            mDrawableRect.right = (rightX + (nextRightX - rightX) * endInterpolator.getInterpolation(positionOffset)).toInt()
            indicatorDrawable!!.bounds = mDrawableRect
            invalidate()
        }

    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onDraw(canvas: Canvas) {
        if (indicatorDrawable != null) {
            indicatorDrawable!!.draw(canvas)
        }
    }

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

    companion object {
        const val MODE_MATCH_EDGE = 0 // drawable宽度 == title宽度 - 2 * mXOffset
        const val MODE_WRAP_CONTENT = 1 // drawable宽度 == title内容宽度 - 2 * mXOffset
        const val MODE_EXACTLY = 2
    }
}