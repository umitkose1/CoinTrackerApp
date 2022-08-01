package com.demo.cointrackerapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alerts::class], version = 1, exportSchema = false)
abstract class AlertsDB : RoomDatabase() {

    abstract val alertsDao: AlertsDao

    companion object {

        @Volatile
        private var INSTANCE: AlertsDB? = null

        fun getInstance(context: Context): AlertsDB {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AlertsDB::class.java,
                        "alerts_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}