package ua.deti.pt.phoneapp.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ua.deti.pt.phoneapp.Auth.GoogleAuthUiClient
import ua.deti.pt.phoneapp.database.daos.ExerciseDao
import ua.deti.pt.phoneapp.database.daos.UserDao
import ua.deti.pt.phoneapp.ui.components.navbar.BottomNavigationBar
import ua.deti.pt.phoneapp.ui.components.navbar.NavigationScreens

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    userDao: UserDao,
    exerciseDao: ExerciseDao
) {
    Scaffold(bottomBar = {
        BottomAppBar(
            containerColor = Color.Black,
            modifier = Modifier.height(120.dp)
        ) { BottomNavigationBar(navController = navController) }
    }) { NavigationScreens(navController = navController, onSignOut, googleAuthUiClient, context, exerciseDao, userDao) }
}