package com.averysync.voicerecorder.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.averysync.voicerecorder.R
import com.averysync.voicerecorder.database.MainAppDB
import com.averysync.voicerecorder.database.VoiceRecordDB
import com.averysync.voicerecorder.time_calculation.TimeCalculation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashBoardActivity : AppCompatActivity(), TimeCalculation.ListenerTimerTick {


    private val AUDIO_REQUEST_PERMISSION_CODE = 1

    private lateinit var recordBtn: ImageButton
    private lateinit var doneBtn: ImageButton
    private lateinit var cancelBtn: ImageButton


    private val externalStoragePermission =
        Manifest.permission.RECORD_AUDIO


    private lateinit var mediaRecorder: MediaRecorder
    private var dirPath = ""
    private var vcRecordFileName = ""
    private var isRecording = false
    private var isPaused = false
    private var duration = ""

    private lateinit var timeCalculation: TimeCalculation
    private lateinit var timeCalTxtView: TextView
    private lateinit var btmSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var btmSheetLayout: LinearLayout
    private lateinit var btmSheet: View
    private lateinit var fileInpTxt: TextInputEditText
    private lateinit var database : MainAppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        recordBtn = findViewById(R.id.record_btn)
        doneBtn = findViewById(R.id.done_btn)
        cancelBtn = findViewById(R.id.cancel_btn)
        timeCalTxtView = findViewById(R.id.tim_cal_txt_view)
        btmSheetLayout = findViewById(R.id.bottom_sheet_layout)
        btmSheet = findViewById(R.id.btm_sheet_dash_brd)
        fileInpTxt = findViewById(R.id.file_name_inp_txt)
        database = Room.databaseBuilder(
            this,
            MainAppDB::class.java,
            "VoiceRecordDB"
        ).build()


        timeCalculation = TimeCalculation(this)

        setUpActionBar()


        btmSheetBehavior = BottomSheetBehavior.from(btmSheetLayout)
        btmSheetBehavior.peekHeight = 0
        btmSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        recordBtn.setOnClickListener {
            if (
                ContextCompat.checkSelfPermission(this, externalStoragePermission)
                == PackageManager.PERMISSION_GRANTED
            ) {

                when {
                    isPaused -> voiceRecordResume()
                    isRecording -> voiceRecordPause()
                    else -> voiceRecordStart()
                }

                //Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show()
            } else {
                audioPermissionRequest()
            }
        }




        doneBtn.setOnClickListener {
            if (isRecording) {
                voiceRecordStop()

                btmSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED


                btmSheet.visibility = View.VISIBLE
                fileInpTxt.setText(vcRecordFileName)
            }
        }


        val btmSheetSavBtn = findViewById<MaterialButton>(R.id.sav_btn)
        val btmSheetExtBtn = findViewById<MaterialButton>(R.id.ext_btn)

        btmSheetSavBtn.setOnClickListener {
            saveToDb()
            dismiss()
        }
        btmSheetExtBtn.setOnClickListener {
            File("$dirPath$vcRecordFileName.mp3").delete()
            dismiss()
        }

        btmSheet.setOnClickListener {
            File("$dirPath$vcRecordFileName.mp3").delete()
            dismiss()
        }



        cancelBtn.setOnClickListener {
            voiceRecordStop()
            File("$dirPath$vcRecordFileName.mp3").delete()
            Toast.makeText(this, "Recorded audio deleted", Toast.LENGTH_SHORT).show()
        }
        cancelBtn.isClickable = false


    }

    private fun dismiss(){

        btmSheet.visibility = View.GONE
        hideKeyboard(fileInpTxt)

        Handler(Looper.getMainLooper()).postDelayed({
            btmSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }, 100)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveToDb(){
        val newFilename = fileInpTxt.text.toString()
        if(newFilename != vcRecordFileName){
            val newFile = File("$dirPath$newFilename.mp3")
            File("$dirPath$vcRecordFileName.mp3").renameTo(newFile)
        }

        val filePath = "$dirPath$newFilename.mp3"
        val timestamp = Date().time


        val record = VoiceRecordDB(newFilename, filePath, timestamp, duration)

        GlobalScope.launch {
            database.voiceRecordDao().insert(record)
        }
    }

    private fun hideKeyboard(view: View){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun timerTick(totalDuration: String) {
        timeCalTxtView.text = totalDuration
        this.duration = duration.dropLast(3)
    }

    private fun audioPermissionRequest() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, externalStoragePermission)) {
            AlertDialog.Builder(this)
                .setTitle("Audio Permission Needed")
                .setMessage("To record audio, voice recorder permission is needed.")
                .setPositiveButton("Ok") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(externalStoragePermission),
                        AUDIO_REQUEST_PERMISSION_CODE
                    )
                }
                .setNegativeButton("Cancel") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(externalStoragePermission),
                AUDIO_REQUEST_PERMISSION_CODE
            )
        }
    }

    private fun voiceRecordStart() {

        mediaRecorder = MediaRecorder()
        dirPath = "${externalCacheDir?.absolutePath}/"

        val simpleDateFormat = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss", Locale.US)
        val date = simpleDateFormat.format(Date())
        vcRecordFileName = "vc_rcd_$date"

        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPath$vcRecordFileName.mp3")

            try {
                prepare()
            } catch (exception: Exception) {
                Log.e("Recorder: ", "error: $exception")
            }
            start()
        }

        recordBtn.setImageResource(R.drawable.icon_ps_btn)
        isRecording = true
        isPaused = false
        timeCalculation.startTimer()

        cancelBtn.isClickable = true
        doneBtn.isClickable = true
    }


    private fun voiceRecordResume() {
        mediaRecorder.resume()
        isPaused = false

        recordBtn.setImageResource(R.drawable.icon_ps_btn)
        timeCalculation.startTimer()
    }

    private fun voiceRecordPause() {
        mediaRecorder.pause()
        isPaused = true

        recordBtn.setImageResource(R.drawable.icon_pl_btn)
        timeCalculation.pauseTimer()
    }

    private fun voiceRecordStop() {
        timeCalculation.stopTimer()

        mediaRecorder.apply {
            stop()
            release()
        }

        isPaused = false
        isRecording = false


        doneBtn.isClickable = false
        cancelBtn.isClickable = false

        recordBtn.setImageResource(R.drawable.icon_pl_btn)

        timeCalTxtView.text = "00:00.00"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AUDIO_REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                when {
                    isRecording -> voiceRecordPause()
                    isPaused -> voiceRecordResume()
                    else -> voiceRecordStart()
                }
                Toast.makeText(this, "Permission allowed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Permission denied.\nAllow microphone permission from app info.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUpActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.dashboard_toolbar)
        setSupportActionBar(toolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.rocorded_files_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rec_file_menu_item -> {
                startActivity(Intent(this, RecordsListActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}