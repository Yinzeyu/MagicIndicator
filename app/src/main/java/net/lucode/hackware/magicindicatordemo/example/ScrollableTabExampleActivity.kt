package net.lucode.hackware.magicindicatordemo.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_scrollable_indicator_example_layout.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.bind
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import net.lucode.hackware.magicindicator.dip2px
import net.lucode.hackware.magicindicator.getScreenWidth
import net.lucode.hackware.magicindicatordemo.R
import net.lucode.hackware.magicindicatordemo.ext.titles.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicatordemo.ext.titles.ScaleTransitionPagerTitleView

class ScrollableTabExampleActivity : AppCompatActivity() {
    private val mDataList: MutableList<String> = mutableListOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT")
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrollable_indicator_example_layout)
        view_pager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
        initMagicIndicator3()
        initMagicIndicator4()
        initMagicIndicator5()
        initMagicIndicator6()
        initMagicIndicator7()
        initMagicIndicator8()
        initMagicIndicator9()
    }

    private fun initMagicIndicator1() {
        magic_indicator1.setBackgroundColor(Color.parseColor("#d43d3d"))
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isSkimOver = true
        val padding: Int = getScreenWidth(this) / 2
        commonNavigator.rightPadding = padding
        commonNavigator.leftPadding = padding
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {


            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = mDataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#f2c4c4")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        })
        magic_indicator1.setNavigator(commonNavigator)
        bind(magic_indicator1, view_pager)
    }

    private fun initMagicIndicator2() {
        magic_indicator2.setBackgroundColor(Color.parseColor("#00c853"))
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {


            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#c8e6c9")
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.textSize = 12f
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.yOffset = dip2px((context), 3.0).toFloat()
                indicator.setColors(Color.parseColor("#ffffff"))
                return indicator
            }
        })
        magic_indicator2.setNavigator(commonNavigator)
        bind(magic_indicator2,view_pager)
    }

    private fun initMagicIndicator3() {
        magic_indicator3.setBackgroundColor(Color.BLACK)
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
                linePagerIndicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                linePagerIndicator.setColors(Color.WHITE)
                return linePagerIndicator
            }
        })
        magic_indicator3.setNavigator(commonNavigator)
        bind(magic_indicator3,view_pager)
    }

    private fun initMagicIndicator4() {
        magic_indicator4.setBackgroundColor(Color.parseColor("#455a64"))
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
        magic_indicator4.setNavigator(commonNavigator)
        bind(magic_indicator4,view_pager)
    }

    private fun initMagicIndicator5() {
        val magicIndicator: MagicIndicator = findViewById<View>(R.id.magic_indicator5) as MagicIndicator
        magicIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.8f
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
        })
        magicIndicator.setNavigator(commonNavigator)
        bind(magicIndicator,view_pager)
    }

    private fun initMagicIndicator6() {
        magic_indicator6.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.normalColor = Color.GRAY
                simplePagerTitleView.selectedColor = Color.BLACK
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = BezierPagerIndicator(context)
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"))
                return indicator
            }
        })
        magic_indicator6.setNavigator(commonNavigator)
        bind(magic_indicator6,view_pager)
    }

    private fun initMagicIndicator7() {
        magic_indicator7.setBackgroundColor(Color.parseColor("#fafafa"))
        val commonNavigator7 = CommonNavigator(this)
        commonNavigator7.scrollPivotX = 0.65f
        commonNavigator7.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int = mDataList.size
            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView: SimplePagerTitleView = ColorFlipPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#9e9e9e")
                simplePagerTitleView.selectedColor = Color.parseColor("#00c853")
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = dip2px((context), 6.0).toFloat()
                indicator.lineWidth = dip2px((context), 10.0).toFloat()
                indicator.roundRadius = dip2px((context), 3.0).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(Color.parseColor("#00c853"))
                return indicator
            }
        })
        magic_indicator7.setNavigator(commonNavigator7)
        bind(magic_indicator7,view_pager)
    }

    private fun initMagicIndicator8() {
        magic_indicator8.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.35f
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int = mDataList.size
            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#333333")
                simplePagerTitleView.selectedColor = Color.parseColor("#e94220")
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = WrapPagerIndicator(context)
                indicator.fillColor = Color.parseColor("#ebe4e3")
                return indicator
            }
        })
        magic_indicator8.setNavigator(commonNavigator)
        bind(magic_indicator8,view_pager)
    }

    private fun initMagicIndicator9() {
        val magicIndicator: MagicIndicator = findViewById<View>(R.id.magic_indicator9) as MagicIndicator
        magicIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.15f
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#333333")
                simplePagerTitleView.selectedColor = Color.parseColor("#e94220")
                simplePagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = TriangularPagerIndicator(context)
                indicator.lineColor = Color.parseColor("#e94220")
                return indicator
            }
        })
        magicIndicator.setNavigator(commonNavigator)
        bind(magicIndicator,view_pager)
    }

}