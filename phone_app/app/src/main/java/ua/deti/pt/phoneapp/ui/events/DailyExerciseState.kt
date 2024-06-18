package ua.deti.pt.phoneapp.ui.events

import ua.deti.pt.phoneapp.database.entities.Exercise

data class DailyExerciseState(
    val exercises: List<Exercise> = emptyList(),
    val exerciseType: String = "",
    val dayOfWeek: String = "",
    val userAssociatedId: Long = 0,
    val isAddingNewExercise: Boolean = false,
) {
}