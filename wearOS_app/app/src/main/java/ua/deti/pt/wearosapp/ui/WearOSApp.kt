package ua.deti.pt.wearosapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import com.google.android.horologist.compose.ambient.AmbientAware
import com.google.android.horologist.compose.layout.AppScaffold
import ua.deti.pt.wearosapp.Screen.Exercise
import ua.deti.pt.wearosapp.Screen.ExerciseNotAvailable
import ua.deti.pt.wearosapp.Screen.PreparingExercise
import ua.deti.pt.wearosapp.Screen.Summary
import ua.deti.pt.wearosapp.navigateToTopLevel
import ua.deti.pt.wearosapp.ui.screens.ExerciseRoute
import ua.deti.pt.wearosapp.ui.screens.PreparingExerciseRoute
import ua.deti.pt.wearosapp.ui.screens.SummaryRoute

/**
 * Navigation for the WearOS App
 */
@Composable
fun WearOSApp(
    navController: NavHostController,
    onFinishActivity: () -> Unit
) {

    val currentScreen by navController.currentBackStackEntryAsState()

    val isAlwaysOnScreen = currentScreen?.destination?.route in AlwaysOnRoutes

    AmbientAware(
        isAlwaysOnScreen = isAlwaysOnScreen
    ) { ambientStateUpdate ->

        AppScaffold {

            SwipeDismissableNavHost(
                navController = navController,
                startDestination = Exercise.route
            ) {
                composable(PreparingExercise.route) {
                    PreparingExerciseRoute(
                        ambientState = ambientStateUpdate.ambientState,
                        onStart = {
                            navController.navigate(Exercise.route) {
                                popUpTo(navController.graph.id) {
                                    inclusive = false
                                }
                            }
                        },
                        onFinishActivity = onFinishActivity,
                        onNoExerciseCapabilities = {
                            navController.navigate(ExerciseNotAvailable.route) {
                                popUpTo(navController.graph.id) {
                                    inclusive = false
                                }
                            }
                        }
                    )
                }

                composable(Exercise.route) {
                    ExerciseRoute(
                        ambientState = ambientStateUpdate.ambientState,
                        onSummary = {
                            navController.navigateToTopLevel(
                                Summary,
                                Summary.buildRoute(it)
                            )
                        },
                        onRestart = {
                            navController.navigateToTopLevel(PreparingExercise)
                        },
                        onFinishActivity = onFinishActivity
                    )
                }

                composable(ExerciseNotAvailable.route) {
                    ExerciseNotAvailable
                }

                composable(
                    Summary.route + "/{averageHeartRate}/{totalDistance}/{totalCalories}/{elapsedTime}",
                    arguments = listOf(
                        navArgument(Summary.averageHeartRateArg) { type = NavType.FloatType },
                        navArgument(Summary.totalDistanceArg) { type = NavType.FloatType },
                        navArgument(Summary.totalCaloriesArg) { type = NavType.FloatType },
                        navArgument(Summary.elapsedTimeArg) { type = NavType.StringType }
                    )
                ) {
                    SummaryRoute(
                        onRestartClick = {
                            navController.navigateToTopLevel(PreparingExercise)
                        }
                    )
                }
            }
        }
    }
}

val AlwaysOnRoutes = listOf(PreparingExercise.route, Exercise.route)