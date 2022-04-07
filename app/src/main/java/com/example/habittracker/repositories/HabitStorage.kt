package com.example.habittracker.repositories

import androidx.lifecycle.MutableLiveData
import com.example.habittracker.models.HabitData
import com.example.habittracker.models.HabitType

object HabitStorage {

    private val habits: MutableLiveData<MutableMap<Int, HabitData>> by lazy {
        MutableLiveData<MutableMap<Int, HabitData>>().apply {
            value = mutableMapOf()
        }
    }

    val size: Int get() = this.habits.value!!.size

    fun addOrUpdate(habit: HabitData) {
        this.habits.value!![habit.id] = habit
    }

    fun getByType(habitType: HabitType): MutableList<HabitData> {
        return this.habits.value!!.values.filter {
            it.type == habitType
        }.toMutableList()
    }
}