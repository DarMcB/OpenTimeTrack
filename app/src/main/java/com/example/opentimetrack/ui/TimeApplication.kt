package com.example.opentimetrack.ui

import android.app.Application
import com.example.opentimetrack.data.AppContainer
import com.example.opentimetrack.data.AppDataContainer

class TimeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}