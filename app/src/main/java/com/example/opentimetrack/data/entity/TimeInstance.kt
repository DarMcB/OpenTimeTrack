package com.example.opentimetrack.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class TimeInstance(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val typeId: Int,
    val date: Long,
    val time: Int
)