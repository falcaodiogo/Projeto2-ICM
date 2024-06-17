package ua.deti.pt.wearosapp.service

import android.util.Log

private const val TAG = "Exercise Logger"

interface ExerciseLogger {
    fun error(message: String, throwable: Throwable? = null)
    fun log(message: String)
}

class AndroidLogExerciseLogger : ExerciseLogger {
    override fun error(message: String, throwable: Throwable?) {
        Log.e(TAG, message, throwable)
    }

    override fun log(message: String) {
        Log.d(TAG, message)
    }
}