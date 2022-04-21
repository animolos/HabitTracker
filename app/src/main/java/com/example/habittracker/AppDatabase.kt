package com.example.habittracker

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habittracker.models.Habit
import com.example.habittracker.models.HabitDao

@Database(entities = [Habit::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun HabitDao(): HabitDao
}