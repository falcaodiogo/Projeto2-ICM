package ua.deti.pt.wearosapp

import androidx.navigation.NavController
import ua.deti.pt.wearosapp.ui.state.SummaryScreenState

sealed class Screen(
    val route: String
) {
    data object Exercise : Screen("exercise")
    data object ExerciseNotAvailable : Screen("exerciseNotAvailable")
    data object PreparingExercise : Screen("preparingExercise")
    data object Summary : Screen("summaryScreen") {
        fun buildRoute(summary: SummaryScreenState): String {
            return "$route/${summary.averageHeartRate}/${summary.totalDistance}/${summary.totalCalories}/${summary.elapsedTime}"
        }

        const val averageHeartRateArg = "averageHeartRate"
        const val totalDistanceArg = "totalDistance"
        const val totalCaloriesArg = "totalCalories"
        const val elapsedTimeArg = "elapsedTime"
    }
}

fun NavController.navigateToTopLevel(screen: Screen, route: String = screen.route) {
    navigate(route) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}