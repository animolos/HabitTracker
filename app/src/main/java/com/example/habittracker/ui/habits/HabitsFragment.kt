package com.example.habittracker.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.R
import com.example.habittracker.adapters.HabitsAdapter
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.models.HabitData
import com.example.habittracker.models.HabitType
import com.example.habittracker.repositories.HabitRepository

class HabitsFragment : Fragment() {

    companion object {
        private const val HABIT_TYPE = "habit_type"

        fun newInstance(habitType: HabitType) =
            HabitsFragment().apply {
                arguments = bundleOf(HABIT_TYPE to habitType)
            }
    }

    private var _binding: FragmentHabitsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitType = arguments?.getSerializable(HABIT_TYPE) as HabitType

        val habitsAdapter = HabitsAdapter(
            HabitRepository.getByType(habitType),
            this::editHabit
        )

        binding.habitsRecycler.apply {
            adapter = habitsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.btnAddNewHabit.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_habit_editor)
        )
    }

    private fun editHabit(habit: HabitData) {
        val bundle = bundleOf(HabitEditorFragment.HABIT_ITEM to habit)
        findNavController().navigate(R.id.action_nav_home_to_nav_habit_editor, bundle)
    }
}