package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
import net.lucode.hackware.magicindicator.dip2px
import kotlin.math.min

/**
 * 类似今日头条切换效果的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class ClipPagerTitleView(context: Context) : View(context), IMeasurablePagerTitleView {
    private var mText: String? = null
    private var mTextColor = 0
    private var mClipColor = 0
    private var mLeftToRight = false
    private var mClipPercent = 0f
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextBounds = Rect()
    init {
        val textSize = dip2px(context, 16.0)
        mPaint.textSize = textSize.toFloat()
        val padding = dip2px(context, 10.0)
        setPadding(padding, 0, padding, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureTextBounds()
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        var result = size
        when (mode) {
            MeasureSpec.AT_MOST -> {
                val width = mTextBounds.width() + paddingLeft + paddingRight
                result = min(width, size)
            }
            MeasureSpec.UNSPECIFIED -> result = mTextBounds.width() + paddingLeft + paddingRight
            else -> {
            }
        }
        return result
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val size = MeasureSpec.getSize(heightMeasureSpec)
        var result = size
        when (mode) {
            MeasureSpec.AT_MOST -> {
                val height = mTextBounds.height() + paddingTop + paddingBottom
                result = min(height, size)
            }
            MeasureSpec.UNSPECIFIED -> result = mTextBounds.height() + paddingTop + paddingBottom
            else -> {
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        val x = (width - mTextBounds.width()) / 2
        val fontMetrics = mPaint.fontMetrics
        val y = ((height - fontMetrics.bottom - fontMetrics.top) / 2).toInt()

        // 画底层
        mPaint.color = mTextColor
        canvas.drawText(mText!!, x.toFloat(), y.toFloat(), mPaint)

        // 画clip层
        canvas.save()
        if (mLeftToRight) {
            canvas.clipRect(0f, 0f, width * mClipPercent, height.toFloat())
        } else {
            canvas.clipRect(width * (1 - mClipPercent), 0f, width.toFloat(), height.toFloat())
        }
        mPaint.color = mClipColor
        canvas.drawText(mText!!, x.toFloat(), y.toFloat(), mPaint)
        canvas.restore()
    }

    override fun onSelected(index: Int, totalCount: Int) {}
    override fun onDeselected(index: Int, totalCount: Int) {}
    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        mLeftToRight = !leftToRight
        mClipPercent = 1.0f - leavePercent
        invalidate()
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        mLeftToRight = leftToRight
        mClipPercent = enterPercent
        invalidate()
    }

    private fun measureTextBounds() {
        mPaint.getTextBounds(mText, 0, if (mText == null) 0 else mText!!.length, mTextBounds)
    }

    var text: String?
        get() = mText
        set(text) {
            mText = text
            requestLayout()
        }
    var textSize: Float
        get() = mPaint.textSize
        set(textSize) {
            mPaint.textSize = textSize
            requestLayout()
        }
    var textColor: Int
        get() = mTextColor
        set(textColor) {
            mTextColor = textColor
            invalidate()
        }
    var clipColor: Int
        get() = mClipColor
        set(clipColor) {
            mClipColor = clipColor
            invalidate()
        }
    override val contentLeft: Int =  left + width / 2 -  mTextBounds.width() / 2

    override val contentTop: Int  =  (height / 2 -  mPaint.fontMetrics.bottom - mPaint.fontMetrics.top / 2).toInt()

    override val contentRight: Int =  left + width / 2 + mTextBounds.width() / 2

    override val contentBottom: Int  =  (height / 2 +  mPaint.fontMetrics.bottom - mPaint.fontMetrics.top / 2).toInt()
}