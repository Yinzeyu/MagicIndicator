package net.lucode.hackware.magicindicatordemo.example

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_custom_navigator_example_layout.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper.bind
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator
import net.lucode.hackware.magicindicatordemo.R
import net.lucode.hackware.magicindicatordemo.ext.navigator.ScaleCircleNavigator
import java.util.*

class CustomNavigatorExampleActivity constructor() : AppCompatActivity() {
    private val mDataList: List<String> = Arrays.asList(*CHANNELS)
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
        val magicIndicator: MagicIndicator = findViewById<View>(R.id.magic_indicator1) as MagicIndicator
        val circleNavigator: CircleNavigator = CircleNavigator(this)
        circleNavigator.circleCount = CHANNELS.size
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener = object : CircleNavigator.OnCircleClickListener {
            public override fun onClick(index: Int) {
                view_pager.setCurrentItem(index)
            }
        }
        magicIndicator.setNavigator(circleNavigator)
        bind(magicIndicator,view_pager)
    }

    private fun initMagicIndicator2() {
        val magicIndicator: MagicIndicator = findViewById<View>(R.id.magic_indicator2) as MagicIndicator
        val circleNavigator = CircleNavigator(this)
        circleNavigator.isFollowTouch = false
        circleNavigator.circleCount = CHANNELS.size
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener = object : CircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                view_pager.currentItem = index
            }
        }
        magicIndicator.setNavigator(circleNavigator)
        bind(magicIndicator, view_pager)
    }

    private fun initMagicIndicator3() {
        val magicIndicator: MagicIndicator = findViewById<View>(R.id.magic_indicator3) as MagicIndicator
        val scaleCircleNavigator = ScaleCircleNavigator(this)
        scaleCircleNavigator.setCircleCount(CHANNELS.size)
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY)
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY)
        scaleCircleNavigator.setCircleClickListener(object : ScaleCircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                view_pager.currentItem = index
            }
        })
        magicIndicator.setNavigator(scaleCircleNavigator)
        bind(magicIndicator,view_pager)
    }

    companion object {
        private val CHANNELS: Array<String> = arrayOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT")
    }
}