package com.example.habittracker

object HabitRepository {

    private val habits = mutableMapOf<Int, HabitData>()

    val size: Int get() = habits.size

    fun addOrUpdate(habit: HabitData) {
        habits[habit.id] = habit
    }

    fun getById(habitId: Int): HabitData? {
        return habits[habitId]
    }
}