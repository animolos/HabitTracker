package com.example.habittracker.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
@TypeConverters(
    Habit.TypeConverter::class,
    Habit.PriorityConverter::class,
    Habit.PeriodicityConverter::class)
interface HabitDao {
    @Query("SELECT * FROM Habit")
    fun getAll(): LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdate(habit: Habit)
}