package ua.deti.pt.phoneapp.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.deti.pt.phoneapp.Auth.GoogleAuthUiClient
import ua.deti.pt.phoneapp.database.daos.ExerciseDao
import ua.deti.pt.phoneapp.database.daos.UserDao
import ua.deti.pt.phoneapp.database.entities.Exercise

class DailyExercisesViewModel(
    private val exerciseDao: ExerciseDao,
    private val userDao: UserDao,
    googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _username = googleAuthUiClient.getSignedInUser()?.username
    private val userId = _username?.let { userDao.getUserByName(it).userId }

    private val _state = MutableStateFlow(DailyExerciseState())
    private val _exercises = userId?.let {
        exerciseDao.getExercisesByUserIdAndDay(it, "").stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )
    }

    val state = combine(_state, _exercises) { state, exercises ->
        state.copy(exercises = exercises)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DailyExerciseState()
    )

    fun onEvent(event: DailyExercisesEvent) {
        when (event) {
            is DailyExercisesEvent.SaveDailyExercise -> {
                val exerciseType = _state.value.exerciseType
                val dayOfWeek = _state.value.dayOfWeek
                val userAssociatedId = userId

                if (exerciseType.isEmpty() || dayOfWeek.isEmpty()) {
                    return
                }

                val exercise = userAssociatedId?.let {
                    Exercise(
                        exerciseType = exerciseType,
                        dayOfWeek = dayOfWeek,
                        userAssociatedId = it
                    )
                }
                viewModelScope.launch {
                    if (exercise != null) {
                        exerciseDao.upsertExercise(exercise)
                    }
                }

                _state.update { it.copy(
                    exerciseType = "",
                    dayOfWeek = "",
                    userAssociatedId = 0,
                ) }
            }

            is DailyExercisesEvent.SetExerciseType -> {
                _state.update { it.copy(
                    exerciseType = event.exerciseType
                ) }
            }

            is DailyExercisesEvent.SetDayOfWeek -> {
                _state.update { it.copy(
                    dayOfWeek = event.dayOfWeek
                ) }
            }

            is DailyExercisesEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingNewExercise = true
                ) }
            }

            is DailyExercisesEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingNewExercise = false
                ) }
            }

            is DailyExercisesEvent.RemoveExercise -> {
                val possibleExercise = exerciseDao.getExerciseById(event.exerciseId)
                if (possibleExercise.isNotNull()) {
                    viewModelScope.launch {
                        exerciseDao.deleteExercise(possibleExercise)
                    }
                }
            }
        }
    }
}