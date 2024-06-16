package ua.deti.pt.wearosapp.ui.components.navbar

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text

@Composable
fun NavigationButton(
    selected: Boolean,
    navController: NavController,
    item: Item
) {
    Chip(
        modifier = Modifier.width(156.dp),
        onClick = {
            navController.navigate(item.path) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        },
        label = { Text(text = item.title) },
        icon = { Icon(item.icon, contentDescription = item.title) },
    )
}