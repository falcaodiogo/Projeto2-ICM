package ua.deti.pt.phoneapp.data.viewmodels

import android.health.connect.datatypes.SleepSessionRecord
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SleepViewModel : ViewModel() {
    val sleepSessions = mutableStateListOf<SleepSessionRecord>()

    fun addSleepSession(session: SleepSessionRecord) {
        viewModelScope.launch {
            sleepSessions.add(session)
        }
    }
    init {
        viewModelScope.launch {
            sleepSessions.addAll(listOf())
        }
    }
}
