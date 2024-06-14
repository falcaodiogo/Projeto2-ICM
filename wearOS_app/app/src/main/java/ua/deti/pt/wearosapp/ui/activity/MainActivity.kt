/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package ua.deti.pt.wearosapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import ua.deti.pt.wearosapp.MainApplication
import ua.deti.pt.wearosapp.ui.WearOSApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars =
            true

        super.onCreate(savedInstanceState)

        val healthServicesRepository =
            (application as MainApplication).healthServicesRepository

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearOSApp(healthServicesRepository = healthServicesRepository)
        }
    }
}
