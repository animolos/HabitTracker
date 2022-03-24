package com.example.habittracker.models

import java.io.Serializable

data class HabitData(
    val id: Int,
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val periodicity: HabitPeriodicity,
    val color: Int
) : Serializable

enum class HabitPriority(val value: Int) {
    High(0),
    Medium(1),
    Low(2);

    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
    }
}

enum class HabitType(val value: Int) {
    Good(0),
    Bad(1);

    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
    }
}

data class HabitPeriodicity(val timesCount: Int, val frequency: Int) : Serializable
