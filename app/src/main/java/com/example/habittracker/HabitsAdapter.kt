package com.example.habittracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.ItemHabitsBinding
import java.util.*

typealias MyHabitClickListener = (UUID) -> Unit

class HabitsAdapter(
        private val onClickListener: MyHabitClickListener
        )
    : RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHabitsBinding.inflate(inflater, parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit: HabitData = HabitStorage.getByIndex(position)!!
        holder.bind(habit)
        holder.itemView.setOnClickListener {
            onClickListener(habit.id)
        }
    }

    override fun getItemCount(): Int = HabitStorage.size
}
