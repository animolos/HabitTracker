package com.example.habittracker.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.databinding.HabitItemBinding
import com.example.habittracker.models.HabitData

typealias MyHabitClickListener = (HabitData) -> Unit

class HabitsAdapter(
    private val data: MutableList<HabitData>,
    private val onClickListener: MyHabitClickListener
) : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = HabitItemBinding.inflate(inflater, parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit: HabitData = data[position]
        holder.bind(habit)
        holder.itemView.setOnClickListener {
            onClickListener(habit)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class HabitViewHolder(private val binding: HabitItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: HabitData) {
            binding.habitName.text = context.getString(R.string.habit_name_text)
                .format(habit.name)
            binding.habitDescription.text = context.getString(R.string.habit_description_text)
                .format(habit.description)
            binding.habitPriority.text = context.getString(R.string.habit_priority_text)
                .format(habit.priority)
            binding.habitPeriodicity.text = context.getString(R.string.habit_periodicity)
                .format(habit.periodicity.timesCount, habit.periodicity.frequency)

            val filter = PorterDuffColorFilter(habit.color, PorterDuff.Mode.SRC_ATOP)
            binding.itemLayout.background.colorFilter = filter
        }
    }
}
