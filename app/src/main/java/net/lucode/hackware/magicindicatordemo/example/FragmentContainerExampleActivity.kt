package net.lucode.hackware.magicindicatordemo.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_fragment_container_example_layout.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import net.lucode.hackware.magicindicator.dip2px
import net.lucode.hackware.magicindicatordemo.R
import java.util.*

class FragmentContainerExampleActivity : AppCompatActivity() {
    private val mFragments: MutableList<Fragment> = ArrayList()
    private val mFragmentContainerHelper: FragmentContainerHelper = FragmentContainerHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container_example_layout)
        initFragments()
        initMagicIndicator1()
        mFragmentContainerHelper.handlePageSelected(1, false)
        switchPages(1)
    }

    private fun switchPages(index: Int) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        var fragment: Fragment
        var i = 0
        val j: Int = mFragments.size
        while (i < j) {
            if (i == index) {
                i++
                continue
            }
            fragment = mFragments[i]
            if (fragment.isAdded) {
                fragmentTransaction.hide(fragment)
            }
            i++
        }
        fragment = mFragments[index]
        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun initFragments() {
        for (i in CHANNELS.indices) {
            val testFragment = TestFragment()
            val bundle = Bundle()
            bundle.putString(TestFragment.EXTRA_TEXT, CHANNELS.get(i))
            testFragment.arguments = bundle
            mFragments.add(testFragment)
        }
    }

    private fun initMagicIndicator1() {
        magic_indicator1.setBackgroundResource(R.drawable.round_indicator_bg)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {


            override val count: Int=CHANNELS.size

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView? {
                val clipPagerTitleView = ClipPagerTitleView((context)!!)
                clipPagerTitleView.text = CHANNELS[index]
                clipPagerTitleView.textColor = Color.parseColor("#e94220")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener {
                    mFragmentContainerHelper.handlePageSelected(index)
                    switchPages(index)
                }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context?): IPagerIndicator? {
                val indicator = LinePagerIndicator((context)!!)
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
        magic_indicator1.setNavigator(commonNavigator)
        mFragmentContainerHelper.attachMagicIndicator(magic_indicator1)
    }

    companion object {
        private val CHANNELS: Array<String> = arrayOf("KITKAT", "NOUGAT", "DONUT")
    }
}