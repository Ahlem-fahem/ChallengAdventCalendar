/**
 * Copyright (C) 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.adventcalendar2020.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.adventcalendar2020.R
import java.util.concurrent.Executors

@Database(entities = [Day::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dayDao(): DayDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "DATABASE_GIFT")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //pre-populate data
                        Executors.newSingleThreadExecutor().execute {
                            instance?.let {
                                it.dayDao().insertAllGift(DataGenerator.getUsers())
                            }
                        }
                    }
                }).allowMainThreadQueries()
                .build()
        }
    }
}
class DataGenerator {

    companion object {
        fun getUsers(): List<Day>{
            return listOf(
                Day(1, "03-12-2020",false,R.drawable.gift),
                Day(2, "04-12-2020",false,R.drawable.bangles),
                Day(3, "05-12-2020",false,R.drawable.candy_cane_icon),
                Day(4, "06-12-2020",false,R.drawable.christmas),
                Day(5, "07-12-2020",false,R.drawable.chocolate),
                Day(6, "08-12-2020",false,R.drawable.teddybear),
                Day(7, "09-12-2020",false,R.drawable.diamond),
                Day(8, "10-12-2020",false,R.drawable.bouquet),
                Day(9, "11-12-2020",false,R.drawable.chocolatebox),
                Day(10, "12-12-2020",false,R.drawable.candy),

                Day(11, "13-12-2020",false,R.drawable.eg),
                Day(12, "14-12-2020",false,R.drawable.socks),
                Day(13, "15-12-2020",false,R.drawable.cake),
                Day(14, "16-12-2020",false,R.drawable.start),
                Day(15, "17-12-2020",false,R.drawable.recipe_deserts),
                Day(16, "18-12-2020",false,R.drawable.kissclipart),
                Day(17, "19-12-2020",false,R.drawable.oreo),
                Day(18, "20-12-2020",false,R.drawable.egg),
                Day(19, "21-12-2020",false,R.drawable.mug),
                Day(20, "22-12-2020",false,R.drawable.love),
                Day(21, "23-12-2020",false,R.drawable.candyy),
                Day(22, "24-12-2020",false,R.drawable.beer)


            )
        }
    }

}