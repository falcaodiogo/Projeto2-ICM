/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package ua.deti.pt.wearosapp.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.deti.pt.wearosapp.Screen
import ua.deti.pt.wearosapp.ui.WearOSApp
import ua.deti.pt.wearosapp.ui.viewModels.ExerciseViewModel

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var navController: NavHostController

    private val exerciseViewModel by viewModels<ExerciseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splash = installSplashScreen()
        var pendingNavigation = true

        splash.setKeepOnScreenCondition { pendingNavigation }

        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberSwipeDismissableNavController()

            WearOSApp(
                navController = navController,
                onFinishActivity = { this.finish() }
            )

            LaunchedEffect(Unit) {
                prepareIfNoExercise()
                pendingNavigation = false
            }
        }
    }

    /**
     * Check if there's an active exercise. If true, set the destination as the
     * Exercise Screen. If false, route to preparing a new exercise.
     **/
    private suspend fun prepareIfNoExercise() {
        val isRegularLaunch = navController.currentDestination?.route == Screen.Exercise.route

        if (isRegularLaunch && !exerciseViewModel.isExerciseInProgress()) {
            navController.navigate(Screen.PreparingExercise.route)
        }
    }
}
