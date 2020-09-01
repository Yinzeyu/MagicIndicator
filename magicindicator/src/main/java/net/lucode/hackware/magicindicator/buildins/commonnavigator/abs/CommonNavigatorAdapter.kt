package net.lucode.hackware.magicindicator.buildins.commonnavigator.abs

import android.content.Context
import android.database.DataSetObservable
import android.database.DataSetObserver

/**
 * CommonNavigator适配器，通过它可轻松切换不同的指示器样式
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
abstract class CommonNavigatorAdapter {
    private val mDataSetObservable = DataSetObservable()
    abstract val count: Int
    abstract fun getTitleView(context: Context?, index: Int): IPagerTitleView?
    abstract fun getIndicator(context: Context?): IPagerIndicator?
    open fun getTitleWeight(context: Context?, index: Int): Float {
        return 1F
    }

    fun registerDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.registerObserver(observer)
    }

    fun unregisterDataSetObserver(observer: DataSetObserver) {
        mDataSetObservable.unregisterObserver(observer)
    }

    fun notifyDataSetChanged() {
        mDataSetObservable.notifyChanged()
    }

    fun notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated()
    }
}