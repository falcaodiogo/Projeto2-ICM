package ua.deti.pt.wearosapp.ui.components.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings

sealed class NavItem {

    object Home :
        Item(
            path = NavPath.HOME.toString(),
            title = NavTitle.HOME,
            icon = Icons.Default.Home
        )

    object Notifications :
        Item(
            path = NavPath.NOTIFICATIONS.toString(),
            title = NavTitle.NOTIFICATIONS,
            icon = Icons.Default.Notifications
        )

    object Settings :
        Item(
            path = NavPath.SETTINGS.toString(),
            title = NavTitle.SETTINGS,
            icon = Icons.Default.Settings
        )

}
