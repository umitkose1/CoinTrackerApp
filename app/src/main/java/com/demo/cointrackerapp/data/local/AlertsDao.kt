package com.demo.cointrackerapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlertsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alerts: Alerts)

    @Query("SELECT * FROM alerts_table ORDER BY item_id DESC")
    fun fetch(): LiveData<List<Alerts>>

    @Delete
    suspend fun delete(alerts: Alerts)
}