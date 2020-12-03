package com.example.adventcalendar2020.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.adventcalendar2020.R
import com.example.adventcalendar2020.data.AppDatabase
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        showCalendarButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        handler.post(run)
        AppDatabase.getInstance(this).dayDao().getUserLiveData()
    }
    fun dateCountDown() : String {
        val SECONDS_IN_A_DAY = 24 * 60 * 60
        val thatDay: Calendar = Calendar.getInstance()
        thatDay.setTime(Date(0)) /* reset */
        thatDay.set(Calendar.DAY_OF_MONTH, 24)
        thatDay.set(Calendar.MONTH, 11) // 0-11 so 1 less

        thatDay.set(Calendar.YEAR, 2020)

        val today: Calendar = Calendar.getInstance()
        val diff: Long = thatDay.getTimeInMillis() - today.getTimeInMillis()
        val diffSec = diff / 1000

        val days = diffSec / SECONDS_IN_A_DAY
        val secondsDay = diffSec % SECONDS_IN_A_DAY
        val seconds = secondsDay % 60
        val minutes = secondsDay / 60 % 60
        val hours = secondsDay / 3600 // % 24 not needed

       return  "$days jours, $hours heures, $minutes minutes et $seconds seconds\n"

    }
    private val run: Runnable = object : Runnable {
        override fun run() {
            todayDateText.text = dateCountDown()
            handler.postDelayed(this, 1000)
        }
    }
}