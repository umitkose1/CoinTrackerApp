package com.demo.cointrackerapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts_table")
data class Alerts(
    @ColumnInfo(name = "item_name")
    val itemName: String,
    @ColumnInfo(name = "item_minValue")
    val itemMinValue: Float,
    @ColumnInfo(name = "item_maxValue")
    val itemMaxValue: Float
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    var id: Int = 0
}