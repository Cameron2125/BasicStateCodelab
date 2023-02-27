package com.codelabs.state

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cups")
data class WaterCups (
    @PrimaryKey(autoGenerate = false) val id: Int = 1,
    @ColumnInfo(name = "Num-Cups") val num_cups: Int,

)