package ua.deti.pt.phoneapp.ui.components.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.AlignVerticalBottom
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings

sealed class NavItem {
    object Home :
        Item(path = NavPath.HOME.toString(), title = NavTitle.HOME, icon = Icons.Default.Map)

    object Notifications :
        Item(
            path = NavPath.NOTIFICATIONS.toString(),
            title = NavTitle.NOTIFICATIONS,
            icon = Icons.Default.AddAlert
        )

    object Sleep :
        Item(path = NavPath.SLEEP.toString(), title = NavTitle.SLEEP, icon = Icons.Default.Bedtime)

    object Settings :
        Item(
            path = NavPath.SETTINGS.toString(),
            title = NavTitle.SETTINGS,
            icon = Icons.Default.Settings
        )

    object Exercises :
        Item(path = NavPath.EXERCISES.toString(), title = NavTitle.EXERCISES, icon = Icons.Default.AlignVerticalBottom)
}