package com.example.habittracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.databinding.EditHabitActivityBinding
import java.util.*

class EditHabitActivity : AppCompatActivity() {

    companion object {
        const val HABIT_ID = "habit_id"
    }

    private lateinit var binding: EditHabitActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditHabitActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val habitId = intent.getSerializableExtra(HABIT_ID)
        if (habitId != null) {
            val id = habitId as UUID
            updateView(id)
            binding.btnCreateHabit.setOnClickListener { saveHabit(id) }
        }
        else {
            binding.btnCreateHabit.setOnClickListener { saveHabit() }
        }
    }

    private fun updateView(habitId: UUID) {
        val habit = HabitStorage.getById(habitId)!!
        binding.editTextHabitName.setText(habit.name)
        binding.editTextHabitDescription.setText(habit.description)

        val id = when (habit.type) {
            HabitType.Good -> R.id.radioButton1
            HabitType.Bad -> R.id.radioButton2
        }

        binding.radioGroup.check(id)

        binding.spinnerPriorities.setSelection(habit.priority.value)
        binding.editTextTimes.setText(habit.periodicity.timesCount.toString())
        binding.editTextFrequency.setText(habit.periodicity.frequency.toString())
    }

    private fun isInputFieldsFilled(): Boolean {
        var result = true

        if (binding.editTextHabitName.text.isBlank()) {
            binding.editTextHabitName.setHintTextColor(Color.RED)
            result = false
        } else {
            binding.editTextHabitName.setHintTextColor(Color.GRAY)
        }

        if (binding.radioGroup.checkedRadioButtonId == -1) {
            binding.textType.setTextColor(Color.RED)
            result = false
        } else {
            binding.textType.setTextColor(Color.GRAY)
        }

        if (binding.editTextTimes.text.isBlank() || binding.editTextFrequency.text.isBlank()) {
            binding.textPeriodicity.setTextColor(Color.RED)
            result = false
        } else {
            binding.textPeriodicity.setTextColor(Color.GRAY)
        }

        return result
    }

    private fun saveHabit(habitId: UUID = UUID.randomUUID()) {
        if (!isInputFieldsFilled())
            return

        val radioGroup = binding.radioGroup
        val radioButtonId = radioGroup.checkedRadioButtonId
        val typeValue = radioGroup.indexOfChild(findViewById(radioButtonId))

        val habit = HabitData(
                habitId,
                binding.editTextHabitName.text.toString(),
                binding.editTextHabitDescription.text.toString(),
                HabitPriority.getByValue(binding.spinnerPriorities.selectedItemPosition)!!,
                HabitType.getByValue(typeValue)!!,
                HabitPeriodicity(
                        binding.editTextTimes.text.toString().toInt(),
                        binding.editTextFrequency.text.toString().toInt()
                )
        )

        HabitStorage.addOrUpdate(habit)

        val intent = Intent(
                this,
                MainActivity::class.java
        ).apply {
            putExtra(HABIT_ID, habitId)
        }

        setResult(RESULT_OK, intent)
        finish()
    }
}