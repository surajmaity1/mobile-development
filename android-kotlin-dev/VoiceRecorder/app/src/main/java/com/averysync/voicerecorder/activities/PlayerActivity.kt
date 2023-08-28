package com.averysync.voicerecorder.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.averysync.voicerecorder.R

class PlayerActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var playButtonPlayer : ImageButton
    private lateinit var seekBar : SeekBar

    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private var delay = 1000L
    private var jumpValue = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        setUpActionBar()

        playButtonPlayer = findViewById(R.id.pl_ps_btn_player)
        seekBar = findViewById(R.id.seek_bar_player)



        val filePath = intent.getStringExtra("filepath")
        var fileName = intent.getStringExtra("filename")

        mediaPlayer = MediaPlayer()
        mediaPlayer.apply {
            setDataSource(filePath)
            prepare()
        }
        mediaPlayer.setOnCompletionListener {
            playButtonPlayer.setImageResource(R.drawable.icon_pl_btn)
            handler.removeCallbacks(runnable)
        }

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, delay)
        }

        playButtonPlayer.setOnClickListener {
            mediaPlayer()
        }

        mediaPlayer()
        seekBar.max = mediaPlayer.duration

    }
    private fun mediaPlayer(){
        if(!mediaPlayer.isPlaying){
            mediaPlayer.start()
            playButtonPlayer.setImageResource(R.drawable.icon_ps_btn)
            handler.postDelayed(runnable, 0)
        }else{
            mediaPlayer.pause()
            playButtonPlayer.setImageResource(R.drawable.icon_pl_btn)
            handler.removeCallbacks(runnable)
        }
    }

    private fun setUpActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.player_toolbar)
        setSupportActionBar(toolbar)

        val supportActionBar = supportActionBar
        val toolbarTitle = findViewById<TextView>(R.id.player_toolbar_title)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_btn)
        toolbarTitle.text = resources.getString(R.string.media_player)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}