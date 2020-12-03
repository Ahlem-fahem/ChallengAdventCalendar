package com.example.adventcalendar2020.data

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Day(@PrimaryKey val id: Int, val date : String, var isOpened : Boolean, val giftDrawable : Int)