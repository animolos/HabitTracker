package com.example.habittracker.ui.habits_editor

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitEditorBinding
import com.example.habittracker.models.Habit
import com.example.habittracker.models.HabitPeriodicity
import com.example.habittracker.models.HabitPriority
import com.example.habittracker.models.HabitType
import com.example.habittracker.ui.color_picker.ColorSelectionDialogFragment
import com.example.habittracker.ui.color_picker.OnColorSelectedListener

class HabitEditorFragment : Fragment(), OnColorSelectedListener {

    companion object {
        const val HABIT_ITEM = "habit_item"
    }

    private var _binding: FragmentHabitEditorBinding? = null

    private val binding get() = _binding!!

    private val model: HabitEditorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var habitId = model.getNewId()

        arguments?.getSerializable(HABIT_ITEM)
            ?.let { habit ->
                updateView(habit as Habit)
                habitId = habit.id
            }

        binding.btnCreateHabit.setOnClickListener { onClickSaveHabit(habitId) }

        binding.btnPickColor.setOnClickListener {
            ColorSelectionDialogFragment().show(childFragmentManager, "color_picker")
        }
    }

    private fun updateView(habit: Habit) {
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

        model.color = habit.color
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
        model.addOrUpdate(habit)

        findNavController().navigate(R.id.action_nav_habit_editor_to_nav_home)
    }

    private fun createHabit(habitId: Int): Habit {
        val habitType = if (binding.radioButtonGood.isChecked) HabitType.Good else HabitType.Bad

        return Habit(
            habitId,
            binding.editTextHabitName.text.toString(),
            binding.editTextHabitDescription.text.toString(),
            HabitPriority.getByValue(binding.spinnerPriorities.selectedItemPosition)!!,
            habitType,
            HabitPeriodicity(
                binding.editTextTimes.text.toString().toInt(),
                binding.editTextFrequency.text.toString().toInt()
            ),
            model.color
        )
    }

    override fun onColorSelected(color: Int) {
        binding.btnPickColor.backgroundTintList = ColorStateList.valueOf(color)
        model.color = color
    }
}