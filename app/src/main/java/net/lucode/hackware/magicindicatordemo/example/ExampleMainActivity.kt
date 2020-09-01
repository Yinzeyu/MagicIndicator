package net.lucode.hackware.magicindicatordemo.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_example_main_layout.*
import net.lucode.hackware.magicindicatordemo.R

class ExampleMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_main_layout)
        scrollable_tab.setOnClickListener {
            startActivity(Intent(this, ScrollableTabExampleActivity::class.java))
        }
        fixed_tab.setOnClickListener {
            startActivity(Intent(this, FixedTabExampleActivity::class.java))
        }
        dynamic_tab.setOnClickListener {
            startActivity(Intent(this, DynamicTabExampleActivity::class.java))
        }
        no_tab_only_indicator.setOnClickListener {
            startActivity(Intent(this, NoTabOnlyIndicatorExampleActivity::class.java))
        }
        tab_with_badge_view.setOnClickListener {
            startActivity(Intent(this, BadgeTabExampleActivity::class.java))
        }


        work_with_fragment_container.setOnClickListener {
            startActivity(Intent(this, FragmentContainerExampleActivity::class.java))
        }

        load_custom_layout . setOnClickListener {
            startActivity(Intent(this, LoadCustomLayoutExampleActivity::class.java))
        }
        custom_navigator . setOnClickListener {
            startActivity(Intent(this, CustomNavigatorExampleActivity::class.java))
        }

    }

}