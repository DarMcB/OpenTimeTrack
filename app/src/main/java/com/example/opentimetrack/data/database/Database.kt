package com.example.opentimetrack.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.opentimetrack.data.dao.TimeDao
import com.example.opentimetrack.data.entity.TimeInstance
import com.example.opentimetrack.data.entity.Type

@Database(
    entities = [Type::class, TimeInstance::class],
    version = 1
)
abstract class TimeDatabase : RoomDatabase() {
    abstract fun timeDao(): TimeDao

    companion object {
        @Volatile
        private var Instance: TimeDatabase? = null

        fun getDatabase(context: Context): TimeDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TimeDatabase::class.java, "time_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }

    }
}