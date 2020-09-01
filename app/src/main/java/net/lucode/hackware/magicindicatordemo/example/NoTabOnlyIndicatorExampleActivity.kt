package net.lucode.hackware.magicindicatordemo.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_no_tab_only_indicator_example_layout.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper.bind
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView
import net.lucode.hackware.magicindicator.dip2px
import net.lucode.hackware.magicindicatordemo.R
import java.util.*

class NoTabOnlyIndicatorExampleActivity : AppCompatActivity() {
    private val mDataList: List<String>? = Arrays.asList(*CHANNELS)
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_tab_only_indicator_example_layout)
        view_pager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
    }

    private fun initMagicIndicator1() {
        val magicIndicator: MagicIndicator = findViewById<View>(R.id.magic_indicator1) as MagicIndicator
        magicIndicator.setBackgroundColor(Color.LTGRAY)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int = mDataList?.size ?: 0

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView? {
                return DummyPagerTitleView(context)
            }

            override fun getIndicator(context: Context?): IPagerIndicator? {
                val indicator = LinePagerIndicator((context)!!)
                val lineHeight: Float = context.resources.getDimension(R.dimen.small_navigator_height)
                indicator.lineHeight = lineHeight
                indicator.setColors(Color.parseColor("#40c4ff"))
                return indicator
            }
        })
        magicIndicator.setNavigator(commonNavigator)
        bind(magicIndicator, view_pager)
    }

    private fun initMagicIndicator2() {
        val magicIndicator: MagicIndicator = findViewById<View>(R.id.magic_indicator2) as MagicIndicator
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int = mDataList?.size ?: 0

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView? {
                return DummyPagerTitleView(context)
            }

            override fun getIndicator(context: Context?): IPagerIndicator? {
                val indicator = TriangularPagerIndicator((context)!!)
                indicator.isReverse = true
                val smallNavigatorHeight: Float = context.resources.getDimension(R.dimen.small_navigator_height)
                indicator.lineHeight = dip2px((context), 2.0)
                indicator.triangleHeight = smallNavigatorHeight.toInt()
                indicator.lineColor = Color.parseColor("#e94220")
                return indicator
            }
        })
        magicIndicator.setNavigator(commonNavigator)
        bind(magicIndicator, view_pager)
    }

    companion object {
        private val CHANNELS: Array<String> = arrayOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "NOUGAT", "DONUT")
    }
}