package com.example.habittracker

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.HabitItemBinding

class HabitViewHolder(private val binding: HabitItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(habit: HabitData) {
        binding.habitName.text = "${habit.name}"
        binding.habitPeriodicity.text =
            "${habit.periodicity.timesCount} times every ${habit.periodicity.frequency} days"

        val filter = PorterDuffColorFilter(habit.color, PorterDuff.Mode.SRC_ATOP)
        binding.itemLayout.background.colorFilter = filter
    }
}