package com.example.habittracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.habittracker.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_ADD_HABIT = 0
        const val REQUEST_CODE_CHANGE_HABIT = 1
    }

    private lateinit var habitsAdapter: HabitsAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        habitsAdapter = HabitsAdapter(this::changeHabit)

        binding.habitsRecycler.adapter = habitsAdapter

        // binding.habitsRecycler.layoutManager = LinearLayoutManager(this@MainActivity)

        binding.btnAddNewHabit.setOnClickListener { addHabit() }
    }

    private fun addHabit() {
        val intent = Intent(this, EditHabitActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_ADD_HABIT)
    }

    private fun changeHabit(habitId: UUID) {
        val intent = Intent(
                this,
                EditHabitActivity::class.java
        ).apply {
            putExtra(EditHabitActivity.HABIT_ID, habitId)
        }

        startActivityForResult(intent, REQUEST_CODE_CHANGE_HABIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val habitId = data?.getSerializableExtra(EditHabitActivity.HABIT_ID) as UUID
        val position = HabitStorage.getIndexById(habitId)

        if (resultCode != RESULT_OK)
            return
        when (requestCode) {
            REQUEST_CODE_ADD_HABIT -> {
                habitsAdapter.notifyItemInserted(position)
            }
            REQUEST_CODE_CHANGE_HABIT -> {
                habitsAdapter.notifyItemChanged(position)
            }
        }
    }
}