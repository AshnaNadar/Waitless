package org.example.userinterface

import android.view.Menu
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MenuBarGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MenuBarOptions.Login.route,
    ) {
        composable(route = MenuBarOptions.Login.route) {
            LoginView(onLoginButtonClicked = { navController.navigate(MenuBarOptions.Home.route) })
        }
        composable(route = MenuBarOptions.Home.route) {
            HomeView()
        }
        composable(route = MenuBarOptions.Settings.route) {
            SettingsView()
        }
        composable(route = MenuBarOptions.Saved.route) {
            SavedView()
        }
        composable(route = MenuBarOptions.Equipment.route) {
            EquipmentView()
        }
    }
}