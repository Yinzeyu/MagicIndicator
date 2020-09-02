package net.lucode.hackware.magicindicatordemo.example

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import kotlinx.android.synthetic.main.activity_fixed_tab_example_layout.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.bind
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import net.lucode.hackware.magicindicator.dip2px
import net.lucode.hackware.magicindicatordemo.R
import net.lucode.hackware.magicindicatordemo.ext.titles.ScaleTransitionPagerTitleView
import java.util.*

class FixedTabExampleActivity : AppCompatActivity() {
    private val mDataList: MutableList<String> = mutableListOf("KITKAT", "NOUGAT", "DONUT")
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixed_tab_example_layout)
        view_pager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
        initMagicIndicator3()
        initMagicIndicator4()
    }

    private fun initMagicIndicator1() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {


            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#88ffffff")
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.setColors(Color.parseColor("#40c4ff"))
                return indicator
            }
        })
        magic_indicator1.setNavigator(commonNavigator)
        val titleContainer: LinearLayout? = commonNavigator.titleContainer // must after setNavigator
        titleContainer!!.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerPadding = dip2px(this, 15.0)
        titleContainer.dividerDrawable = ContextCompat.getDrawable(this, R.drawable.simple_splitter)
        bind(magic_indicator1, view_pager)
    }

    private fun initMagicIndicator2() {
        magic_indicator2.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {


            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.normalColor = Color.parseColor("#616161")
                simplePagerTitleView.selectedColor = Color.parseColor("#f57c00")
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(1.6f)
                indicator.yOffset = dip2px((context), 39.0).toFloat()
                indicator.lineHeight = dip2px((context), 1.0).toFloat()
                indicator.setColors(Color.parseColor("#f57c00"))
                return indicator
            }

            override fun getTitleWeight(context: Context, index: Int): Float {
                return when (index) {
                    0 -> {
                        2.0f
                    }
                    1 -> {
                        1.2f
                    }
                    else -> {
                        1.0f
                    }
                }
            }
        })
        magic_indicator2.setNavigator(commonNavigator)
        bind(magic_indicator2, view_pager)
    }

    private fun initMagicIndicator3() {
        magic_indicator3.setBackgroundResource(R.drawable.round_indicator_bg)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {


            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = mDataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#e94220")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight: Float = context.resources.getDimension(R.dimen.common_navigator_height)
                val borderWidth: Float = dip2px((context), 1.0).toFloat()
                val lineHeight: Float = navigatorHeight - 2 * borderWidth
                indicator.lineHeight = lineHeight
                indicator.roundRadius = lineHeight / 2
                indicator.yOffset = borderWidth
                indicator.setColors(Color.parseColor("#bc2a2a"))
                return indicator
            }
        })
        magic_indicator3.setNavigator(commonNavigator)
        bind(magic_indicator3, view_pager)
    }

    private fun initMagicIndicator4() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {


            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.normalColor = Color.GRAY
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.mode = LinePagerIndicator.MODE_EXACTLY
                linePagerIndicator.lineWidth = dip2px((context), 10.0).toFloat()
                linePagerIndicator.setColors(Color.WHITE)
                return linePagerIndicator
            }
        })
        magic_indicator4.setNavigator(commonNavigator)
        val titleContainer: LinearLayout? = commonNavigator.titleContainer // must after setNavigator
        titleContainer?.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer?.dividerDrawable = object : ColorDrawable() {
            override fun getIntrinsicWidth(): Int {
                return dip2px(this@FixedTabExampleActivity, 15.0)
            }
        }
        val fragmentContainerHelper = FragmentContainerHelper(magic_indicator4)
        fragmentContainerHelper.setInterpolator(OvershootInterpolator(2.0f))
        fragmentContainerHelper.setDuration(300)
        view_pager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                fragmentContainerHelper.handlePageSelected(position)
            }
        })
    }


}