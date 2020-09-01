package net.lucode.hackware.magicindicator

import android.util.SparseArray
import android.util.SparseBooleanArray

/**
 * 方便扩展IPagerNavigator的帮助类，将ViewPager的3个回调方法转换成
 * onSelected、onDeselected、onEnter等回调，方便扩展
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
class NavigatorHelper {
    private val mDeselectedItems = SparseBooleanArray()
    private val mLeavedPercents = SparseArray<Float>()
    private var mTotalCount = 0
    var currentIndex = 0
        private set
    private var mLastIndex = 0
    private var mLastPositionOffsetSum = 0f
    var scrollState = 0
        private set
    private var mSkimOver = false
    private var mNavigatorScrollListener: OnNavigatorScrollListener? = null
    fun onPageScrolled(position: Int, positionOffset: Float) {
        val currentPositionOffsetSum = position + positionOffset
        var leftToRight = false
        if (mLastPositionOffsetSum <= currentPositionOffsetSum) {
            leftToRight = true
        }
        if (scrollState != ScrollState.SCROLL_STATE_IDLE) {
            if (currentPositionOffsetSum == mLastPositionOffsetSum) {
                return
            }
            var nextPosition = position + 1
            var normalDispatch = true
            if (positionOffset == 0.0f) {
                if (leftToRight) {
                    nextPosition = position - 1
                    normalDispatch = false
                }
            }
            for (i in 0 until mTotalCount) {
                if (i == position || i == nextPosition) {
                    continue
                }
                val leavedPercent = mLeavedPercents[i, 0.0f]
                if (leavedPercent != 1.0f) {
                    dispatchOnLeave(i, 1.0f, leftToRight, true)
                }
            }
            if (normalDispatch) {
                if (leftToRight) {
                    dispatchOnLeave(position, positionOffset, leftToRight = true, force = false)
                    dispatchOnEnter(nextPosition, positionOffset, leftToRight = true, force = false)
                } else {
                    dispatchOnLeave(nextPosition, 1.0f - positionOffset, leftToRight = false, force = false)
                    dispatchOnEnter(position, 1.0f - positionOffset, leftToRight = false, force = false)
                }
            } else {
                dispatchOnLeave(nextPosition, 1.0f - positionOffset, leftToRight = true, force = false)
                dispatchOnEnter(position, 1.0f - positionOffset, leftToRight = true, force = false)
            }
        } else {
            for (i in 0 until mTotalCount) {
                if (i == currentIndex) {
                    continue
                }
                val deselected = mDeselectedItems[i]
                if (!deselected) {
                    dispatchOnDeselected(i)
                }
                val leavedPercent = mLeavedPercents[i, 0.0f]
                if (leavedPercent != 1.0f) {
                    dispatchOnLeave(i, 1.0f, leftToRight = false, force = true)
                }
            }
            dispatchOnEnter(currentIndex, 1.0f, leftToRight = false, force = true)
            dispatchOnSelected(currentIndex)
        }
        mLastPositionOffsetSum = currentPositionOffsetSum
    }

    private fun dispatchOnEnter(index: Int, enterPercent: Float, leftToRight: Boolean, force: Boolean) {
        if (mSkimOver || index == currentIndex || scrollState == ScrollState.SCROLL_STATE_DRAGGING || force) {
            mNavigatorScrollListener?.onEnter(index, mTotalCount, enterPercent, leftToRight)
            mLeavedPercents.put(index, 1.0f - enterPercent)
        }
    }

    private fun dispatchOnLeave(index: Int, leavePercent: Float, leftToRight: Boolean, force: Boolean) {
        if (mSkimOver || index == mLastIndex || scrollState == ScrollState.SCROLL_STATE_DRAGGING || (index == currentIndex - 1 || index == currentIndex + 1) && mLeavedPercents[index, 0.0f] != 1.0f || force) {
            mNavigatorScrollListener?.onLeave(index, mTotalCount, leavePercent, leftToRight)
            mLeavedPercents.put(index, leavePercent)
        }
    }

    private fun dispatchOnSelected(index: Int) {
        mNavigatorScrollListener?.onSelected(index, mTotalCount)
        mDeselectedItems.put(index, false)
    }

    private fun dispatchOnDeselected(index: Int) {
        mNavigatorScrollListener?.onDeselected(index, mTotalCount)
        mDeselectedItems.put(index, true)
    }

    fun onPageSelected(position: Int) {
        mLastIndex = currentIndex
        currentIndex = position
        dispatchOnSelected(currentIndex)
        for (i in 0 until mTotalCount) {
            if (i == currentIndex) {
                continue
            }
            val deselected = mDeselectedItems[i]
            if (!deselected) {
                dispatchOnDeselected(i)
            }
        }
    }

    fun onPageScrollStateChanged(state: Int) {
        scrollState = state
    }

    fun setNavigatorScrollListener(navigatorScrollListener: OnNavigatorScrollListener?) {
        mNavigatorScrollListener = navigatorScrollListener
    }

    fun setSkimOver(skimOver: Boolean) {
        mSkimOver = skimOver
    }

    var totalCount: Int
        get() = mTotalCount
        set(totalCount) {
            mTotalCount = totalCount
            mDeselectedItems.clear()
            mLeavedPercents.clear()
        }

    interface OnNavigatorScrollListener {
        fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean)
        fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean)
        fun onSelected(index: Int, totalCount: Int)
        fun onDeselected(index: Int, totalCount: Int)
    }
}