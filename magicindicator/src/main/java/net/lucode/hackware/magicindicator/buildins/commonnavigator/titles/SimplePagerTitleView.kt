package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles

import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
import net.lucode.hackware.magicindicator.dip2px

/**
 * 带文本的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
open class SimplePagerTitleView(context: Context) : AppCompatTextView(context, null), IMeasurablePagerTitleView {
    var selectedColor = 0
    var normalColor = 0
    init {
        gravity = Gravity.CENTER
        val padding = dip2px(context, 10.0)
        setPadding(padding, 0, padding, 0)
        isSingleLine = true
        ellipsize = TextUtils.TruncateAt.END
    }

    override fun onSelected(index: Int, totalCount: Int) {
        setTextColor(selectedColor)
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        setTextColor(normalColor)
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {}
    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {}
    override val contentLeft: Int
        get() {
            val bound = Rect()
            var longestString = ""
            if (text.toString().contains("\n")) {
                val brokenStrings = text.toString().split("\\n").toTypedArray()
                for (each in brokenStrings) {
                    if (each.length > longestString.length) longestString = each
                }
            } else {
                longestString = text.toString()
            }
            paint.getTextBounds(longestString, 0, longestString.length, bound)
            val contentWidth = bound.width()
            return left + width / 2 - contentWidth / 2
        }



    override val contentRight: Int
        get() {
            val bound = Rect()
            var longestString = ""
            if (text.toString().contains("\n")) {
                val brokenStrings = text.toString().split("\\n").toTypedArray()
                for (each in brokenStrings) {
                    if (each.length > longestString.length) longestString = each
                }
            } else {
                longestString = text.toString()
            }
            paint.getTextBounds(longestString, 0, longestString.length, bound)
            val contentWidth = bound.width()
            return left + width / 2 + contentWidth / 2
        }

    override val contentBottom: Int = (height / 2 + paint.fontMetrics.bottom - paint.fontMetrics.top / 2).toInt()

    override val contentTop: Int =(height / 2 -  paint.fontMetrics.bottom - paint.fontMetrics.top / 2).toInt()
}