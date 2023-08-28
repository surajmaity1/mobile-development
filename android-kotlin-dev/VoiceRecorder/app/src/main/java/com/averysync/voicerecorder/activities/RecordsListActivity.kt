package com.averysync.voicerecorder.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.averysync.voicerecorder.R
import com.averysync.voicerecorder.adapters.RecordListAdapter
import com.averysync.voicerecorder.database.MainAppDB
import com.averysync.voicerecorder.database.VoiceRecordDB
import com.averysync.voicerecorder.listeners.OnRecordItemClickListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordsListActivity : AppCompatActivity(), OnRecordItemClickListener {

    private lateinit var arrayListOfRecords : ArrayList<VoiceRecordDB>
    private lateinit var mRecordListAdapter : RecordListAdapter
    private lateinit var database : MainAppDB
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records_list)


        setUpActionBar()

        arrayListOfRecords = ArrayList()
        recyclerView = findViewById(R.id.rcd_lst_rec_view)

        database = Room.databaseBuilder(
            this,
            MainAppDB::class.java,
            "VoiceRecordDB"
        ).build()

        mRecordListAdapter = RecordListAdapter(arrayListOfRecords, this)

        recyclerView.apply {
            adapter = mRecordListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        fetchRecords()
    }

    private fun setUpActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.rec_lst_toolbar)
        setSupportActionBar(toolbar)

        val supportActionBar = supportActionBar
        val toolbarTitle = findViewById<TextView>(R.id.rec_lst_toolbar_title)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_btn)
        toolbarTitle.text = resources.getString(R.string.rec_lst)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchRecords(){
        GlobalScope.launch {
            arrayListOfRecords.clear()
            val queryResult = database.voiceRecordDao().getAll()
            arrayListOfRecords.addAll(queryResult)

            mRecordListAdapter.notifyDataSetChanged()
        }
    }

    override fun onRecordItemClickListener(position: Int) {
        val voiceRecord = arrayListOfRecords[position]

        val intent = Intent(this, PlayerActivity::class.java)

        intent.putExtra("filename", voiceRecord.filename)
        intent.putExtra("filepath", voiceRecord.filePath)
        startActivity(intent)
    }
}