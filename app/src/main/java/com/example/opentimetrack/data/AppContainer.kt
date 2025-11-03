package com.example.opentimetrack.data

import android.content.Context
import com.example.opentimetrack.data.database.TimeDatabase
import com.example.opentimetrack.data.repository.TimeRepository

interface AppContainer {
    val typeRepository: TimeRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val typeRepository: TimeRepository by lazy {
        TimeRepository(TimeDatabase.getDatabase(context).timeDao())
    }
}