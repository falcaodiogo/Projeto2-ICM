package ua.deti.pt.wearosapp.components.navbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text

@Composable
fun NavigationButton(
    selected: Boolean,
    navController: NavController,
    item: Item
) {
    Button(onClick = {
        navController.navigate(item.path) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
    ) {
        Row (horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(item.icon, contentDescription = item.title)
            Text(text = item.title)
        }
    }
}