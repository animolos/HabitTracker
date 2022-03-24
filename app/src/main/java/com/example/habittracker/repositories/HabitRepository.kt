package com.example.habittracker.repositories

import com.example.habittracker.models.HabitData
import com.example.habittracker.models.HabitType

object HabitRepository {

    private val habits = mutableMapOf<Int, HabitData>()

    val size: Int get() = habits.size

    fun addOrUpdate(habit: HabitData) {
        habits[habit.id] = habit
    }

    fun getById(habitId: Int): HabitData? {
        return habits[habitId]
    }

    fun getHabitsByType(habitType: HabitType): MutableList<HabitData> {
        return habits.values.filter {
            it.type == habitType
        }.toMutableList()
    }
}