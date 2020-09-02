package net.lucode.hackware.magicindicatordemo.example

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_custom_navigator_example_layout.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.bind
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator
import net.lucode.hackware.magicindicatordemo.R
import net.lucode.hackware.magicindicatordemo.ext.navigator.ScaleCircleNavigator

class CustomNavigatorExampleActivity  : AppCompatActivity() {
    private val mDataList: MutableList<String> = mutableListOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT")
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_navigator_example_layout)
        view_pager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
        initMagicIndicator3()
    }

    private fun initMagicIndicator1() {
        val circleNavigator = CircleNavigator(this)
        circleNavigator.circleCount = mDataList.size
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener = object : CircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                view_pager.currentItem = index
            }
        }
        magic_indicator1.setNavigator(circleNavigator)
        bind(magic_indicator1,view_pager)
    }

    private fun initMagicIndicator2() {
        val circleNavigator = CircleNavigator(this)
        circleNavigator.isFollowTouch = false
        circleNavigator.circleCount = mDataList.size
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener = object : CircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                view_pager.currentItem = index
            }
        }
        magic_indicator2.setNavigator(circleNavigator)
        bind(magic_indicator2, view_pager)
    }

    private fun initMagicIndicator3() {
        val scaleCircleNavigator = ScaleCircleNavigator(this)
        scaleCircleNavigator.setCircleCount(mDataList.size)
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY)
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY)
        scaleCircleNavigator.setCircleClickListener(object : ScaleCircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                view_pager.currentItem = index
            }
        })
        magic_indicator3.setNavigator(scaleCircleNavigator)
        bind(magic_indicator3,view_pager)
    }

}