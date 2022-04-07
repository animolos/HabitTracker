package com.example.habittracker.ui.habits


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var model: HabitsViewModel

    private var _binding: FragmentBottomSheetBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireParentFragment()).get(HabitsViewModel::class.java)
        setupSort()
        binding.habitSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                model.filter.filter(newText)
                return false
            }
        })
    }

    private fun setupSort() {
        binding.sortSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    l: Long
                ) {
                    model.sortItems(position)
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
    }
}