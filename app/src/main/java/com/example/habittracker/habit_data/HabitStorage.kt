package com.example.habittracker.habit_data

import java.util.*

object HabitStorage {

    private val habits = mutableMapOf<UUID, HabitData>()

    val size: Int get() = habits.size

    fun addOrUpdate(habit: HabitData) {
        habits[habit.id] = habit
    }

    fun getById(habitId: UUID): HabitData? {
        return habits[habitId]
    }

    fun getByIndex(index: Int): HabitData? {
        return habits[habits.keys.elementAt(index)]
    }

    fun getIndexById(habitId: UUID): Int {
        return habits.keys.indexOf(habitId)
    }

    fun getHabitsByType(habitType: HabitType): MutableList<HabitData> {
        return habits.values.filter {
            it.type == habitType
        }.toMutableList()
    }
}