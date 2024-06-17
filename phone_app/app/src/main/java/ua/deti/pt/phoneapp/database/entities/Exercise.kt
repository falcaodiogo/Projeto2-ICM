package ua.deti.pt.phoneapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EXERCISE")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long? = null,
    val exerciseType: ExercisesType,
    val dayOfWeek: DayOfWeek,
    val userAssociatedId: Long
)
