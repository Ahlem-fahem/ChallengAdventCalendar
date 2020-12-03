package com.example.adventcalendar2020.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.FtsOptions.Order


@Dao
interface DayDao {
    @Query("SELECT * FROM Day")
    fun getUserLiveData(): List<Day>
    @Query("SELECT * FROM Day ORDER BY id DESC LIMIT 1")
    fun getUserData(): Day
    @Query("DELETE FROM Day")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGift(gifts: List<Day>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Day)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: Day)

    @Transaction
    fun insertOrUpdate(user: Day) {
        update(user)
    }
}
