package com.example.habittracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val HABIT_POSITION = "habit_position"
    }

    private lateinit var habitsAdapter: HabitsAdapter

    private val startForCreateHabit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val habitId = data?.getIntExtra(HABIT_POSITION, 0)
            if (habitId != null) {
                habitsAdapter.notifyItemInserted(habitId)
            }
        }
    }

    private val startForEditHabit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val habitId = data?.getIntExtra(HABIT_POSITION, 0)
            if (habitId != null) {
                habitsAdapter.notifyItemChanged(habitId)
            }
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        habitsAdapter = HabitsAdapter(this::editHabit)

        binding.habitsRecycler.apply {
            adapter = habitsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.btnAddNewHabit.setOnClickListener { addHabit() }
    }

    private fun addHabit() {
        startForCreateHabit.launch(Intent(this, EditHabitActivity::class.java))
    }

    private fun editHabit(habit: HabitData) {
        val intent = Intent(this, EditHabitActivity::class.java)
            .apply {
            putExtra(EditHabitActivity.HABIT_ITEM, habit)
        }

        startForEditHabit.launch(intent)
    }
}