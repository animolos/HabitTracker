package com.example.habittracker.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.habittracker.HabitTrackerApp
import com.example.habittracker.models.Habit
import com.example.habittracker.models.HabitType

class HabitRepository: Application() {

    val habits: LiveData<List<Habit>> = HabitTrackerApp.db.HabitDao().getAll()

    fun addOrUpdate(habit: Habit) {
        HabitTrackerApp.db.HabitDao().addOrUpdate(habit)
    }

    fun getByType(habitType: HabitType): MutableList<Habit> {
        return this.habits.value?.filter {
            it.type == habitType
        }?.toMutableList() ?: mutableListOf()
    }

    fun getSize(): Int = this.habits.value?.size ?: 0
}