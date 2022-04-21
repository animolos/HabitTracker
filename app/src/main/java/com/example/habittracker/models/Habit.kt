package com.example.habittracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity
@TypeConverters(Habit.TypeConverter::class, Habit.PriorityConverter::class)
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: HabitPriority,
    @ColumnInfo val type: HabitType,
    @ColumnInfo val periodicity: HabitPeriodicity,
    @ColumnInfo val color: Int
) : Serializable {

    class TypeConverter {
        @androidx.room.TypeConverter
        fun fromType(type: HabitType): Int = type.value

        @androidx.room.TypeConverter
        fun toType(value: Int): HabitType? = HabitType.getByValue(value)
    }

    class PriorityConverter {
        @androidx.room.TypeConverter
        fun fromPriority(priority: HabitPriority): Int = priority.value

        @androidx.room.TypeConverter
        fun toPriority(value: Int): HabitPriority? = HabitPriority.getByValue(value)
    }
}

enum class HabitPriority(val value: Int) {
    High(0),
    Medium(1),
    Low(2);

    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
    }
}

enum class HabitType(val value: Int) {
    Good(0),
    Bad(1);

    companion object {
        private val VALUES = values()
        fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
    }
}

data class HabitPeriodicity(val timesCount: Int, val frequency: Int) : Serializable
