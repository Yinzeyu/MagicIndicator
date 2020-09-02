package net.lucode.hackware.magicindicatordemo.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_load_custom_layout_example.*
import net.lucode.hackware.magicindicator.bind
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
import net.lucode.hackware.magicindicatordemo.R
import java.util.*

class LoadCustomLayoutExampleActivity : AppCompatActivity() {
    private val mDataList: List<String> = Arrays.asList(*CHANNELS)
    private val mExamplePagerAdapter: ExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_custom_layout_example)
        view_pager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
    }

    private fun initMagicIndicator1() {
        magic_indicator1.setBackgroundColor(Color.BLACK)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override val count: Int = mDataList.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val commonPagerTitleView = CommonPagerTitleView(context)

                // load custom layout
                val customLayout: View = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null)
                val titleImg: ImageView = customLayout.findViewById<View>(R.id.title_img) as ImageView
                val titleText: TextView = customLayout.findViewById<View>(R.id.title_text) as TextView
                titleImg.setImageResource(R.mipmap.ic_launcher)
                titleText.text = mDataList[index]
                commonPagerTitleView.setContentView(customLayout)
                commonPagerTitleView.onPagerTitleChangeListener = object : OnPagerTitleChangeListener {
                    override fun onSelected(index: Int, totalCount: Int) {
                        titleText.setTextColor(Color.WHITE)
                    }

                    override fun onDeselected(index: Int, totalCount: Int) {
                        titleText.setTextColor(Color.LTGRAY)
                    }

                    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
                        titleImg.scaleX = 1.3f + (0.8f - 1.3f) * leavePercent
                        titleImg.scaleY = 1.3f + (0.8f - 1.3f) * leavePercent
                    }

                    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
                        titleImg.scaleX = 0.8f + (1.3f - 0.8f) * enterPercent
                        titleImg.scaleY = 0.8f + (1.3f - 0.8f) * enterPercent
                    }
                }
                commonPagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return commonPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        })
        magic_indicator1.setNavigator(commonNavigator)
        bind(magic_indicator1, view_pager)
    }

    companion object {
        private val CHANNELS: Array<String> = arrayOf("NOUGAT", "DONUT", "ECLAIR", "KITKAT")
    }
}