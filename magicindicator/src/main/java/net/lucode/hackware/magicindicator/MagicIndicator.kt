package net.lucode.hackware.magicindicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import net.lucode.hackware.magicindicator.abs.IPagerNavigator

/**
 * 整个框架的入口，核心
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class MagicIndicator @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defAttrStyle: Int = 0
) : FrameLayout(context, attributeSet, defAttrStyle) {
    private var navigator: IPagerNavigator? = null

    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        navigator?.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    fun onPageSelected(position: Int) {
        navigator?.onPageSelected(position)
    }

    fun onPageScrollStateChanged(state: Int) {
        navigator?.onPageScrollStateChanged(state)
    }

    fun setNavigator(navigator: IPagerNavigator) {
        if (this.navigator === navigator) {
            return
        }
        if (this.navigator != null) {
            navigator.onDetachFromMagicIndicator()
        }
        this.navigator = navigator
        removeAllViews()
        if (this.navigator is View) {
            val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            addView(this.navigator as View?, lp)
            navigator.onAttachToMagicIndicator()
        }
    }
}