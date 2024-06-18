package ua.deti.pt.phoneapp.ui.events

sealed interface DailyExercisesEvent {
    object SaveDailyExercise: DailyExercisesEvent
    data class SetExerciseType(val exerciseType: String): DailyExercisesEvent
    data class SetDayOfWeek(val dayOfWeek: String): DailyExercisesEvent
    object ShowDialog: DailyExercisesEvent
    object HideDialog: DailyExercisesEvent
    data class RemoveExercise(val exerciseId: Long): DailyExercisesEvent
}