package org.example.userinterface

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuBarOptions (
    val route: String,
    val title: String,
    val icon: ImageVector,
    val icon_focused: ImageVector
) {
    data object Login: MenuBarOptions(
        route = "login",
        title = "Login",
        icon = Icons.Default.ExitToApp,
        icon_focused = Icons.Default.ExitToApp
    )

    data object EquipmentInfo: MenuBarOptions(
        route = "equipment/info",
        title = "Equipment Info",
        icon = Icons.Default.ExitToApp,
        icon_focused = Icons.Default.ExitToApp
    )
    data object Home: MenuBarOptions(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home,
        icon_focused = Icons.Default.Home
    )

    data object Equipment: MenuBarOptions(
        route = "equipment",
        title = "Equipment",
        icon = Icons.Default.List,
        icon_focused = Icons.Default.Home
    )

    data object Saved: MenuBarOptions(
        route = "saved",
        title = "Saved",
        icon = Icons.Default.Favorite,
        icon_focused = Icons.Default.Home
    )

    data object Settings: MenuBarOptions(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings,
        icon_focused = Icons.Default.Home
    )
}

