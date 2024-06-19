package ua.deti.pt.phoneapp.data.health

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.deti.pt.phoneapp.Auth.GoogleAuthUiClient
import ua.deti.pt.phoneapp.database.daos.ExerciseDao
import ua.deti.pt.phoneapp.database.daos.UserDao
import ua.deti.pt.phoneapp.database.entities.Exercise

class DailyExerciseViewModel(
    private val exerciseDao: ExerciseDao,
    private val userDao: UserDao,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _uiState = MutableStateFlow<DailyExercisesUiState>(DailyExercisesUiState.Loading)
    val uiState: StateFlow<DailyExercisesUiState> = _uiState.asStateFlow()

    private val _showAddExerciseDialog = MutableStateFlow(false)
    val showAddExerciseDialog: StateFlow<Boolean> = _showAddExerciseDialog.asStateFlow()

    private val _showUpdateStateDialog = MutableStateFlow(false)
    val showUpdateStateDialog: StateFlow<Boolean> = _showUpdateStateDialog.asStateFlow()
    private var userId: Long? = null

    init {
        viewModelScope.launch {
            getExercisesByUserId()
        }
    }

    private suspend fun getExercisesByUserId() {
        withContext(Dispatchers.IO) {
            val userName = googleAuthUiClient.getSignedInUser()?.username
            userId = userName?.let { userDao.getUserByName(it).userId }

            viewModelScope.launch {
                userId?.let { id ->
                    exerciseDao.getExercisesByUserId(id)
                        .collect { exercises ->
                            _uiState.value = DailyExercisesUiState.Success(exercises)
                        }
                } ?: run {
                    _uiState.value = DailyExercisesUiState.Error("User not found")
                }
            }
        }
    }

    fun upsertExercise(exerciseType: String, dayOfWeek: String) {
        val exercise = userId?.let {
            Exercise(
                exerciseType = exerciseType,
                dayOfWeek = dayOfWeek,
                userAssociatedId = it
            )
        }

        if (exercise != null) {
            viewModelScope.launch {
                exerciseDao.upsertExercise(exercise)
                dismissAddExerciseDialog()
                Log.i("DailyExerciseViewModel", "Exercise upserted")
            }
        } else {
            Log.e("DailyExerciseViewModel", "Exercise not upserted")
        }
    }

    fun updateExerciseState(exercise: Exercise, state: Int) {
        val updatedExercise = exercise.copy(exerciseStage = state)
        viewModelScope.launch {
            exerciseDao.upsertExercise(updatedExercise)
            Log.i("DailyExerciseViewModel", "Exercise state updated")
        }
    }

    fun showAddExerciseDialog() {
        _showAddExerciseDialog.value = true
    }

    fun dismissAddExerciseDialog() {
        _showAddExerciseDialog.value = false
    }

    fun showUpdateStateDialog() {
        _showUpdateStateDialog.value = true
    }

    fun dismissUpdateStateDialog() {
        _showUpdateStateDialog.value = false
    }

}

sealed class DailyExercisesUiState {
    data object Loading: DailyExercisesUiState()
    data class Success(val exercises: List<Exercise>) : DailyExercisesUiState()
    data class Error(val message: String) : DailyExercisesUiState()
}