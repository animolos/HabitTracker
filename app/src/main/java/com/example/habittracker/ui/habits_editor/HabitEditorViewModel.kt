package com.example.habittracker.ui.habits_editor

import androidx.lifecycle.ViewModel
import com.example.habittracker.R
import com.example.habittracker.models.HabitData
import com.example.habittracker.repositories.HabitStorage

class HabitEditorViewModel : ViewModel() {

    var color: Int = R.color.design_default_color_primary

    fun getNewId(): Int {
        return HabitStorage.size
    }

    fun addOrUpdate(habit: HabitData) {
        HabitStorage.addOrUpdate(habit)
    }
}