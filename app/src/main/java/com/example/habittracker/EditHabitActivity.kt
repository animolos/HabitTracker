package com.example.habittracker

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.databinding.EditHabitActivityBinding

class EditHabitActivity : AppCompatActivity(), OnColorSelectedListener {

    companion object {
        const val HABIT_ITEM = "habit_item"
    }

    private lateinit var binding: EditHabitActivityBinding
    private var color: Int = R.color.design_default_color_primary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditHabitActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var habitId = HabitRepository.size

        val value = intent.getSerializableExtra(HABIT_ITEM)
        if (value != null) {
            val habit = value as HabitData
            updateView(habit)
            habitId = habit.id
        }

        binding.btnCreateHabit.setOnClickListener { onClickSaveHabit(habitId) }

        binding.btnPickColor.setOnClickListener {
            ColorSelectionDialogFragment().show(supportFragmentManager, "Color Picker")
        }
    }

    private fun updateView(habit: HabitData) {
        binding.editTextHabitName.setText(habit.name)
        binding.editTextHabitDescription.setText(habit.description)

        val radioButton = when (habit.type) {
            HabitType.Good -> binding.radioButtonGood
            HabitType.Bad -> binding.radioButtonBad
        }

        binding.radioGroup.check(radioButton.id)

        binding.spinnerPriorities.setSelection(habit.priority.value)
        binding.editTextTimes.setText(habit.periodicity.timesCount.toString())
        binding.editTextFrequency.setText(habit.periodicity.frequency.toString())

        color = habit.color
        binding.btnPickColor.backgroundTintList = ColorStateList.valueOf(habit.color)
    }

    private fun checkIfInputFieldFilled(): Boolean {
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

    private fun onClickSaveHabit(habitId: Int) {
        if (!checkIfInputFieldFilled())
            return

        val habit = createHabit(habitId)
        HabitRepository.addOrUpdate(habit)

        val intent = Intent(
                this,
                MainActivity::class.java
        ).apply {
            putExtra(MainActivity.HABIT_POSITION, habitId)
        }

        setResult(RESULT_OK, intent)
        finish()
    }

    private fun createHabit(habitId: Int): HabitData {
        val habitType = if (binding.radioButtonGood.isChecked) HabitType.Good else HabitType.Bad

        return HabitData(
            habitId,
            binding.editTextHabitName.text.toString(),
            binding.editTextHabitDescription.text.toString(),
            HabitPriority.getByValue(binding.spinnerPriorities.selectedItemPosition)!!,
            habitType,
            HabitPeriodicity(
                binding.editTextTimes.text.toString().toInt(),
                binding.editTextFrequency.text.toString().toInt()
            ),
            color
        )
    }
    override fun onColorSelected(color: Int) {
        binding.btnPickColor.backgroundTintList = ColorStateList.valueOf(color)
        this.color = color
    }
}