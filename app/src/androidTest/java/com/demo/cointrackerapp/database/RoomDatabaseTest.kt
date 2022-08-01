package com.demo.cointrackerapp.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.cointrackerapp.data.local.AlertsDB
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class RoomDatabaseTest {
    protected lateinit var appDatabase: AlertsDB

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlertsDB::class.java)
            .allowMainThreadQueries()
            .build()
    }
    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }
}