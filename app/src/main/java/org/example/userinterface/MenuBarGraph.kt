package org.example.userinterface

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.userinterface.Equipment.EquipmentInfo.EquipmentInfoView
import org.example.userinterface.Equipment.EquipmentView
import org.example.userinterface.Home.HomeView
import org.example.userinterface.Home.HomeWorkoutView
import org.example.userinterface.Login.LoginView
import org.example.userinterface.Saved.SavedView
import org.example.userinterface.Settings.SettingsView

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
            HomeView(
                onInfoClicked = { navController.navigate(MenuBarOptions.Equipment.route) },
                onSeeAllClicked = { navController.navigate(MenuBarOptions.Saved.route) },
                onStartClicked = { navController.navigate(MenuBarOptions.HomeWorkout.route) }
            )
        }
        composable(route = MenuBarOptions.HomeWorkout.route) {
            HomeWorkoutView(
                onLastSetClicked = { navController.navigate(MenuBarOptions.Home.route) },
                onEquipmentInfoClicked = { navController.navigate(MenuBarOptions.EquipmentInfo.route) }
            )
        }
        composable(route = MenuBarOptions.Settings.route) {
            SettingsView()
        }
        composable(route = MenuBarOptions.Saved.route) {
            SavedView(
                onEditWorkoutClicked = { navController.navigate(MenuBarOptions.HomeWorkout.route) }
            )
        }
        composable(route = MenuBarOptions.Equipment.route) {
            EquipmentView(onEquipmentClicked = { navController.navigate(MenuBarOptions.EquipmentInfo.route) })
        }
        composable(route = MenuBarOptions.EquipmentInfo.route) {
            EquipmentInfoView()
        }
    }
}