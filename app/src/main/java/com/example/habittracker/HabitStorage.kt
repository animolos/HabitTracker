package com.example.habittracker

import java.util.*

object HabitStorage {

    private val habits = mutableMapOf<UUID, HabitData>()
    private val keys = mutableListOf<UUID>()

    val size: Int get() = keys.size

    fun addOrUpdate(habit: HabitData) {
        if (!habits.containsKey(habit.id)) {
            keys.add(habit.id)
        }
        habits[habit.id] = habit
    }

    fun getById(habitId: UUID): HabitData? {
        return habits[habitId]
    }

    fun getByIndex(index: Int): HabitData? {
        return habits[keys[index]]
    }

    fun getIndexById(habitId: UUID): Int {
        return keys.indexOf(habitId)
    }
}