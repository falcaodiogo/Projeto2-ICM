package ua.deti.pt.phoneapp.ui.components.navbar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.deti.pt.phoneapp.Auth.GoogleAuthUiClient
import ua.deti.pt.phoneapp.data.health.DailyExerciseViewModel
import ua.deti.pt.phoneapp.database.daos.ExerciseDao
import ua.deti.pt.phoneapp.database.daos.UserDao
import ua.deti.pt.phoneapp.ui.screens.HomeScreen
import ua.deti.pt.phoneapp.ui.screens.NotificationsScreen
import ua.deti.pt.phoneapp.ui.screens.PlannedExercises
import ua.deti.pt.phoneapp.ui.screens.ProfileScreen
import ua.deti.pt.phoneapp.ui.screens.SleepScreen
import ua.deti.pt.phoneapp.ui.screens.DailyExercisesScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavigationScreens(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    exerciseDao: ExerciseDao,
    userDao: UserDao
) {
    val viewModel = DailyExerciseViewModel(
        exerciseDao = exerciseDao,
        googleAuthUiClient = googleAuthUiClient,
        userDao = userDao
    )

    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) { googleAuthUiClient.getSignedInUser()
            ?.let { it1 -> HomeScreen(googleAuthUiClient, it1) } }
        composable(NavItem.Notifications.path) { NotificationsScreen(navController, context) }
        composable(NavItem.Sleep.path) { SleepScreen(context) }
        composable(NavItem.Settings.path) {
            val userData = googleAuthUiClient.getSignedInUser()
            ProfileScreen(userData, onSignOut, context, googleAuthUiClient)
        }
        composable(NavItem.Exercises.path) { PlannedExercises(navController) }
        composable(NavItem.Exercises.path + "/{day}") {backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")
            DailyExercisesScreen(day, viewModel = viewModel)
        }
    }
}
