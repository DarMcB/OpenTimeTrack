package com.example.opentimetrack.data

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class Converters {
    @TypeConverter
    fun timestampToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }

    @TypeConverter
    fun dateToTimestamp(date: String): Long {
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.parse(date).time
    }
}