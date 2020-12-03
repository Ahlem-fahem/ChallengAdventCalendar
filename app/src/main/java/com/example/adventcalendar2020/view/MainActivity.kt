package com.example.adventcalendar2020.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adventcalendar2020.R
import com.example.adventcalendar2020.data.AppDatabase
import com.example.adventcalendar2020.view.adapter.GiftRvAdapter
import kotlinx.android.synthetic.main.activity_calendar.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val gridLayoutManager = GridLayoutManager(this, 3)
        daysDetailsRv.layoutManager = gridLayoutManager
        AppDatabase.getInstance(this).dayDao().getUserLiveData().let {
            it?.let {
                daysDetailsRv.adapter = GiftRvAdapter()
                (daysDetailsRv.adapter as GiftRvAdapter).setList(it)
                daysDetailsRv.adapter?.notifyDataSetChanged()
            }
        }

    }

}