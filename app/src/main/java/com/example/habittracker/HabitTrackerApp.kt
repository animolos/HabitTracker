package com.example.habittracker

import android.app.Application
import androidx.room.Room

class HabitTrackerApp : Application() {

    companion object {
        lateinit var instance: HabitTrackerApp
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database"
        ).allowMainThreadQueries().build()
    }
}