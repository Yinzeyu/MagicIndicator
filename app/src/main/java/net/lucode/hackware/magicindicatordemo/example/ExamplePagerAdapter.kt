package net.lucode.hackware.magicindicatordemo.example

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

/**
 * Created by hackware on 2016/9/10.
 */
class ExamplePagerAdapter(private val mDataList: List<String>?) : PagerAdapter() {
    override fun getCount(): Int {
        return mDataList?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val textView = TextView(container.context)
        textView.text = mDataList!![position]
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.BLACK)
        textView.textSize = 24f
        container.addView(textView)
        return textView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        val textView = `object` as TextView
        val text = textView.text.toString()
        val index = mDataList!!.indexOf(text)
        return if (index >= 0) {
            index
        } else POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mDataList!![position]
    }
}