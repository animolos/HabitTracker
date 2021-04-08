package com.example.habittracker.ui.habits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.habit_data.HabitStorage
import com.example.habittracker.habit_data.HabitType
import com.example.habittracker.adapters.HabitsAdapter
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import java.util.*

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitType = arguments?.getSerializable(HABIT_TYPE) as HabitType

        val habitsAdapter = HabitsAdapter(
            HabitStorage.getHabitsByType(habitType),
            this::changeHabit
        )

        binding.habitsRecycler.apply {
            adapter = habitsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.btnAddNewHabit.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_nav_home_to_nav_habit_editor, null
            )
        )
    }

    private fun changeHabit(habitId: UUID) {
        val bundle = bundleOf(HabitEditorFragment.HABIT_ID to habitId)
        view?.findNavController()?.navigate(R.id.action_nav_home_to_nav_habit_editor, bundle)
    }

    companion object {
        private const val HABIT_TYPE = "habit_type"

        fun newInstance(habitType: HabitType) =
            HabitsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(HABIT_TYPE, habitType)
                }
            }
    }
}