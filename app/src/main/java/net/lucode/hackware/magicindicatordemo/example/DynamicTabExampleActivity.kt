package net.lucode.hackware.magicindicatordemo.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dynamic_tab_example_layout.*
import net.lucode.hackware.magicindicator.bind
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import net.lucode.hackware.magicindicatordemo.R
import net.lucode.hackware.magicindicatordemo.ext.click
import java.util.*
import kotlin.math.abs

class DynamicTabExampleActivity : AppCompatActivity() {
    private val mDataList: MutableList<String> = mutableListOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT")
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    private var mCommonNavigator: CommonNavigator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_tab_example_layout)
        view_pager.adapter = mExamplePagerAdapter
        magic_indicator1.setBackgroundColor(Color.parseColor("#d43d3d"))
        mCommonNavigator = CommonNavigator(this)
        mCommonNavigator?.isSkimOver = true
        mCommonNavigator?.setAdapter(object : CommonNavigatorAdapter() {
            /*这个东西必须写get 方法  因为下面的randomPage 刷新之后上一次的size 值没有更新上去*/
            override val count: Int get() =   mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = mDataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#f2c4c4")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.click { view_pager.currentItem = index }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        })
        magic_indicator1.setNavigator(mCommonNavigator!!)
        bind(magic_indicator1, view_pager)
        btnrandom_page.click { randomPage() }
    }

    fun randomPage() {
        val list = mutableListOf<String>()
        list.addAll(mDataList)
        mDataList.clear()
        val total: Int = abs( Random().nextInt(list.size))
        for (i in 0..total) {
            mDataList.add(list[i])
        }
        mCommonNavigator?.notifyDataSetChanged() // must call firstly
        mExamplePagerAdapter.notifyDataSetChanged()
        Toast.makeText(this, "" + mDataList.size + " page", Toast.LENGTH_SHORT).show()
    }

}