package com.example.habittracker.ui.habits_editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.R
import com.example.habittracker.models.Habit
import com.example.habittracker.repositories.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HabitEditorViewModel : ViewModel() {

    private val habitRepository = HabitRepository()

    var color: Int = R.color.design_default_color_primary

    fun getNewId(): Int = habitRepository.getSize()

    fun addOrUpdate(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.addOrUpdate(habit)
        }
    }
}