package com.example.habittracker.ui.habits

import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.models.Habit
import com.example.habittracker.models.HabitType
import com.example.habittracker.repositories.HabitRepository

class HabitsViewModel(habitType: HabitType) : ViewModel(), Filterable {

    class Factory(private val habitType: HabitType) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HabitsViewModel(habitType) as T
        }
    }

    private  var habitRepository: HabitRepository = HabitRepository()

    private lateinit var initHabits: List<Habit>
    
    val habits: MutableLiveData<List<Habit>> = MutableLiveData<List<Habit>>()

    init {
        habitRepository.habits.observeForever {
            initHabits = it.filter { el -> el.type == habitType }
            habits.value = initHabits
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint.toString()
                val searchResult = FilterResults()

                searchResult.values =
                    if (searchString.isEmpty()) initHabits
                    else initHabits.filter { it.name.contains(searchString) }

                return searchResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                habits.value = results?.values as List<Habit>
            }
        }
    }

    fun sortItems(position: Int) {
        when (position) {
            0 -> habits.value = habits.value!!.sortedBy { e -> e.id }
            1 -> habits.value = habits.value!!.sortedBy { e -> e.name }
            2 -> habits.value = habits.value!!.sortedBy { e -> e.priority.value }
        }
    }
}