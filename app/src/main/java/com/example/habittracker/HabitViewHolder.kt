package com.example.habittracker


import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.ItemHabitsBinding

class HabitViewHolder(private val binding: ItemHabitsBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(habit: HabitData) {
        binding.habitName.text = "Habit: ${habit.name}"
        binding.habitDescription.text = "Description: ${habit.description}"
        binding.habitPriority.text = "Priority: ${habit.priority}"
        binding.habitType.text = "Type: ${habit.type}"
        binding.habitPeriodicity.text =
                "${habit.periodicity.timesCount} times every ${habit.periodicity.frequency} days"
    }
}