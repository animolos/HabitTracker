package com.example.habittracker.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habittracker.models.HabitType
import com.example.habittracker.ui.habits.HabitsFragment

class ViewPagerAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HabitsFragment.newInstance(HabitType.Good)
        1 -> HabitsFragment.newInstance(HabitType.Bad)
        else -> throw NotImplementedError()
    }
}