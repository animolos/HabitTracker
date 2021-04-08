package com.example.habittracker.ui.habits

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.habittracker.*
import com.example.habittracker.color_picker.ColorPickerDialog
import com.example.habittracker.databinding.FragmentHabitEditorBinding
import com.example.habittracker.habit_data.*
import java.util.*

class HabitEditorFragment : Fragment(), ColorPickerDialog.OnInputListener {

    private var _binding: FragmentHabitEditorBinding? = null

    private val binding get() = _binding!!

    var color: Int = R.color.design_default_color_primary

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val serializableUUID = arguments?.getSerializable(HabitEditorFragment.HABIT_ID)
        if (serializableUUID == null)
            binding.btnCreateHabit.setOnClickListener { saveHabit() }
        else {
            val habitId = serializableUUID as UUID
            updateView(habitId)
            binding.btnCreateHabit.setOnClickListener { saveHabit(habitId) }
        }

        binding.btnPickColor.setOnClickListener {
            ColorPickerDialog().show(childFragmentManager, "color_picker")
        }
    }

    override fun sendColor(color: Int) {
        binding.btnPickColor.backgroundTintList = ColorStateList.valueOf(color)
        this.color = color
    }

    private fun saveHabit(habitId: UUID = UUID.randomUUID()) {
        if (!isInputFieldsFilled())
            return

        val radioGroup = binding.radioGroup
        val radioButtonId = radioGroup.checkedRadioButtonId
        val typeValue = radioGroup.indexOfChild(requireView().findViewById(radioButtonId))

        val habit = HabitData(
            habitId,
            binding.editTextHabitName.text.toString(),
            binding.editTextHabitDescription.text.toString(),
            HabitPriority.getByValue(binding.spinnerPriorities.selectedItemPosition)!!,
            HabitType.getByValue(typeValue)!!,
            HabitPeriodicity(
                binding.editTextTimes.text.toString().toInt(),
                binding.editTextFrequency.text.toString().toInt()
            ),
            color
        )

        HabitStorage.addOrUpdate(habit)

        view?.findNavController()?.navigate(R.id.action_nav_habit_editor_to_nav_home, null)
        //view?.findNavController()?.popBackStack()
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

        color = habit.color
        binding.btnPickColor.backgroundTintList = ColorStateList.valueOf(color)
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

    companion object {
        const val HABIT_ID = "habit_id"
    }
}