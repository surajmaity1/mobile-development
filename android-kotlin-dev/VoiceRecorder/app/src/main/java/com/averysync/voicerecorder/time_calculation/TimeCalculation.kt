package com.averysync.voicerecorder.time_calculation

import android.os.Handler
import android.os.Looper

class TimeCalculation(listener: ListenerTimerTick) {


    private var totalTime = 0L
    private var timeDelay = 100L

    interface ListenerTimerTick{
        fun timerTick(totalDuration: String)
    }

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable


    init{
        runnable = Runnable {
            totalTime += timeDelay
            handler.postDelayed(runnable, timeDelay)
            listener.timerTick(format())
        }
    }

    fun startTimer(){
        handler.postDelayed(runnable, timeDelay)
    }

    fun pauseTimer(){
        handler.removeCallbacks(runnable)
    }

    fun stopTimer(){
        handler.removeCallbacks(runnable)
        totalTime = 0L
    }

    fun format(): String{
        val millis = totalTime % 1000
        val seconds = (totalTime / 1000) % 60
        val mins = (totalTime / (1000 * 60)) % 60
        val hrs = (totalTime / (1000 * 60 * 60))

        return if(hrs > 0)
            "%02d:%02d:%02d.%02d".format(hrs, mins, seconds, millis/10)
        else
            "%02d:%02d.%02d".format(mins, seconds, millis/10)
    }
}