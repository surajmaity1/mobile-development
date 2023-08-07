package com.surajmaity1.testapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.surajmaity1.testapp.R

class RefineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refine)

        setUpActionBar()

        // Status
        val status = resources.getStringArray(R.array.status_drop_list)
        val arrayAdapter1 = ArrayAdapter(this, R.layout.drop_down_itm, status)
        val autoCompTv1 = findViewById<AutoCompleteTextView>(R.id.auto_comp_tv_stat)
        autoCompTv1.setAdapter(arrayAdapter1)



        // Broadcast
        val broadcastEdtTxt = findViewById<TextInputEditText>(R.id.broadcast_brief_msg_et)
        broadcastEdtTxt.setText(resources.getString(R.string.broad_et_txt))



    }
    private fun setUpActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_refine_activity)
        val mainTitleToolbar = findViewById<TextView>(R.id.main_title_toolbar_ref)
        val subTitleToolbar = findViewById<TextView>(R.id.sub_title_toolbar_ref)


        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Nav Drawer Feature coming soon", Toast.LENGTH_SHORT).show()
        }

        mainTitleToolbar.text = resources.getText(R.string.main_title_toolbar_ref)
        subTitleToolbar.text = resources.getText(R.string.sub_title_toolbar_ref)
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