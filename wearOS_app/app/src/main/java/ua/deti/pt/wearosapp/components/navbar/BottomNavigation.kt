package ua.deti.pt.wearosapp.components.navbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.navigation.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems = listOf(NavItem.Home, NavItem.Notifications, NavItem.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(contentColor = Color.Black) {
        LazyColumn(modifier = Modifier.padding(vertical = 5.dp), verticalArrangement = Arrangement.SpaceEvenly) {
            item {
                navItems.forEachIndexed{ _, item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.path,
                        onClick = { navController.navigate(item.path) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        } },
                        icon = { item.icon },
                        label = { item.title }
                    )
                }
            }
        }
    }


}