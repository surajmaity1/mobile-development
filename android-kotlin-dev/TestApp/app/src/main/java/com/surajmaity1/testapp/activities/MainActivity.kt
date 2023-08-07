package com.surajmaity1.testapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.surajmaity1.testapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpActionBar()

        val refine = findViewById<Button>(R.id.refine_screen_activity)
        val explore = findViewById<Button>(R.id.explore_screen_activity)

        refine.setOnClickListener {
            val intent = Intent(this@MainActivity, RefineActivity::class.java)
            startActivity(intent)
        }

        explore.setOnClickListener {
            val intent = Intent(this@MainActivity, ExploreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main_activity)
        val mainTitleToolbar = findViewById<TextView>(R.id.main_title_toolbar_main)


        setSupportActionBar(toolbar)

        mainTitleToolbar.text = resources.getText(R.string.main_activity)
    }


}