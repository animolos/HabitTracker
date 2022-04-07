package com.example.habittracker.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.R
import com.example.habittracker.adapters.HabitsAdapter
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.models.HabitData
import com.example.habittracker.models.HabitType
import com.example.habittracker.ui.habits_editor.HabitEditorFragment

class HabitsFragment : Fragment(), LifecycleOwner {

    companion object {
        private const val HABIT_TYPE = "habit_type"

        fun newInstance(habitType: HabitType) =
            HabitsFragment().apply {
                arguments = bundleOf(HABIT_TYPE to habitType)
            }
    }

    private var _binding: FragmentHabitsBinding? = null

    private val binding get() = _binding!!
    private lateinit var model: HabitsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val habitType = arguments?.getSerializable(HABIT_TYPE) as HabitType

        val factory = HabitsViewModel.Factory(habitType)
        model = ViewModelProvider(this, factory).get(HabitsViewModel::class.java)

        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitsAdapter = HabitsAdapter { habit -> editHabit(habit) }

        binding.habitsRecycler.apply {
            adapter = habitsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        model.habits.observe(viewLifecycleOwner) {
            habitsAdapter.updateHabits(it)
        }

        val bottomSheet = BottomSheetDialogFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.bottom_sheet, bottomSheet)
            .commit()

        binding.btnAddNewHabit.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_habit_editor)
        )
    }

    private fun editHabit(habit: HabitData) {
        val bundle = bundleOf(HabitEditorFragment.HABIT_ITEM to habit)
        findNavController().navigate(R.id.action_nav_home_to_nav_habit_editor, bundle)
    }
}