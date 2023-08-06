package com.surajmaity1.testapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.surajmaity1.testapp.R
import com.surajmaity1.testapp.adapters.ViewPagerAdapter

class ExploreActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        tabLayout = findViewById(R.id.layout_tab)
        viewPager2 = findViewById(R.id.view_pager_2)
        viewPagerAdapter = ViewPagerAdapter(this)

        setUpActionBar()

        viewPager2.adapter = viewPagerAdapter
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }

    private fun setUpActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_explore_activity)
        val mainTitleToolbar = findViewById<TextView>(R.id.main_title_toolbar)
        val subTitleToolbar = findViewById<TextView>(R.id.sub_title_toolbar)


        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Nav Drawer Feature coming soon", Toast.LENGTH_SHORT).show()
        }

        mainTitleToolbar.text = resources.getText(R.string.main_title_toolbar)
        subTitleToolbar.text = resources.getText(R.string.sub_title_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.explore_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.notification_menu_item -> {
                Toast.makeText(this, "Notification Feature coming", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}