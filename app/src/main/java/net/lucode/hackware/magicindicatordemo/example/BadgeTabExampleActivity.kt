package net.lucode.hackware.magicindicatordemo.example

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_badge_tab_example_layout.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.bind
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule
import net.lucode.hackware.magicindicator.dip2px
import net.lucode.hackware.magicindicatordemo.R
import net.lucode.hackware.magicindicatordemo.ext.titles.ScaleTransitionPagerTitleView
import java.util.*

class BadgeTabExampleActivity : AppCompatActivity() {
    private val mDataList :MutableList<String>  = mutableListOf("KITKAT", "NOUGAT", "DONUT")
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge_tab_example_layout)
        view_pager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
        initMagicIndicator3()
        initMagicIndicator4()
    }

    private fun initMagicIndicator1() {
        val magicIndicator = findViewById<View>(R.id.magic_indicator1) as MagicIndicator
        val commonNavigator = CommonNavigator(this)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int
                get() = mDataList.size

            @SuppressLint("SetTextI18n")
            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val simplePagerTitleView: SimplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#88ffffff")
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.setOnClickListener {
                    view_pager?.currentItem = index
                    badgePagerTitleView.badgeView = null // cancel badge when click tab
                }
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView)

                // setup badge
                if (index != 2) {
                    val badgeTextView = LayoutInflater.from(context).inflate(R.layout.simple_count_badge_layout, null) as TextView
                    badgeTextView.text = "" + (index + 1)
                    badgePagerTitleView.badgeView = badgeTextView
                } else {
                    val badgeImageView = LayoutInflater.from(context).inflate(R.layout.simple_red_dot_badge_layout, null) as ImageView
                    badgePagerTitleView.badgeView = badgeImageView
                }

                // set badge position
                if (index == 0) {
                    badgePagerTitleView.xBadgeRule = BadgeRule(BadgeAnchor.CONTENT_LEFT, -dip2px((context), 6.0))
                    badgePagerTitleView.yBadgeRule = BadgeRule(BadgeAnchor.CONTENT_TOP, 0)
                } else if (index == 1) {
                    badgePagerTitleView.xBadgeRule = BadgeRule(BadgeAnchor.CONTENT_RIGHT, -dip2px((context), 6.0))
                    badgePagerTitleView.yBadgeRule = BadgeRule(BadgeAnchor.CONTENT_TOP, 0)
                } else if (index == 2) {
                    badgePagerTitleView.xBadgeRule = BadgeRule(BadgeAnchor.CENTER_X, -dip2px((context), 3.0))
                    badgePagerTitleView.yBadgeRule = BadgeRule(BadgeAnchor.CONTENT_BOTTOM, dip2px((context), 2.0))
                }

                // don't cancel badge when tab selected
                badgePagerTitleView.isAutoCancelBadge = false
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.setColors(Color.parseColor("#40c4ff"))
                return indicator
            }
        })
        magicIndicator.setNavigator(commonNavigator)
        val titleContainer = commonNavigator.titleContainer // must after setNavigator
        titleContainer!!.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerPadding = dip2px(this, 15.0)
        titleContainer.dividerDrawable = ContextCompat.getDrawable(this, R.drawable.simple_splitter)
        bind(magicIndicator, view_pager)
    }

    private fun initMagicIndicator2() {
        val magicIndicator = findViewById<View>(R.id.magic_indicator2) as MagicIndicator
        magicIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int
                get() = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.normalColor = Color.parseColor("#616161")
                simplePagerTitleView.selectedColor = Color.parseColor("#f57c00")
                simplePagerTitleView.setOnClickListener { view_pager?.currentItem = index }
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView)

                // setup badge
                if (index == 1) {
                    val badgeImageView = LayoutInflater.from(context).inflate(R.layout.simple_red_dot_badge_layout, null) as ImageView
                    badgePagerTitleView.badgeView = badgeImageView
                    badgePagerTitleView.xBadgeRule = BadgeRule(BadgeAnchor.CENTER_X, -dip2px(context, 3.0))
                    badgePagerTitleView.yBadgeRule = BadgeRule(BadgeAnchor.CONTENT_BOTTOM, dip2px((context), 2.0))
                }

                // cancel badge when click tab, default true
                badgePagerTitleView.isAutoCancelBadge = true
                return badgePagerTitleView
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
        magicIndicator.setNavigator(commonNavigator)
        bind(magicIndicator, view_pager)
    }

    private fun initMagicIndicator3() {
        val magicIndicator = findViewById<View>(R.id.magic_indicator3) as MagicIndicator
        magicIndicator.setBackgroundResource(R.drawable.round_indicator_bg)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = mDataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#e94220")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener {view_pager.currentItem = index }
                badgePagerTitleView.setInnerPagerTitleView(clipPagerTitleView)
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight = context.resources.getDimension(R.dimen.common_navigator_height)
                val borderWidth = dip2px((context), 1.0).toFloat()
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.lineHeight = lineHeight
                indicator.roundRadius = lineHeight / 2
                indicator.yOffset = borderWidth
                indicator.setColors(Color.parseColor("#bc2a2a"))
                return indicator
            }
        })
        magicIndicator.setNavigator(commonNavigator)
        bind(magicIndicator,view_pager)
    }

    private fun initMagicIndicator4() {
        val magicIndicator = findViewById<View>(R.id.magic_indicator4) as MagicIndicator
        val commonNavigator = CommonNavigator(this)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {

            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val simplePagerTitleView: SimplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.normalColor = Color.GRAY
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setOnClickListener {view_pager.currentItem = index }
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView)
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.setColors(Color.WHITE)
                return linePagerIndicator
            }
        })
        magicIndicator.setNavigator(commonNavigator)
        val titleContainer = commonNavigator.titleContainer // must after setNavigator
        titleContainer?.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer?.dividerDrawable = object : ColorDrawable() {
            override fun getIntrinsicWidth(): Int {
                return dip2px(this@BadgeTabExampleActivity, 15.0)
            }
        }
        bind(magicIndicator, view_pager)
    }
}