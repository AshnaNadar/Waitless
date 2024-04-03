package org.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.controller.UserController
import org.example.userinterface.Equipment.EquipmentInfo.EquipmentInfoView
import org.example.userinterface.Equipment.EquipmentView
import org.example.userinterface.Home.HomeView
import org.example.userinterface.Home.HomeWorkoutView
import org.example.userinterface.LastSetCountdownTimer
import org.example.userinterface.Login.LoginView
import org.example.userinterface.Login.SignUpView
import org.example.userinterface.OngoingWorkoutTimer
import org.example.userinterface.Saved.SavedView
import org.example.userinterface.Settings.SettingsView
import org.example.viewmodel.UserViewModel

@Composable
fun MenuBarGraph(userViewModel: UserViewModel, userController: UserController, navController: NavHostController) {
    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }
    LastSetCountdownTimer(viewModel, controller)
    OngoingWorkoutTimer(viewModel, controller, navToHome = { navController.navigate(MenuBarOptions.Home.route) })

    NavHost(
        navController = navController,
        startDestination = MenuBarOptions.Login.route,
    ) {
        composable(route = MenuBarOptions.Login.route) {
            LoginView(
                onLoginButtonClicked = { navController.navigate(MenuBarOptions.Home.route) },
                navToSignUp = { navController.navigate(MenuBarOptions.SignUp.route) }
            )
        }
        composable(route = MenuBarOptions.SignUp.route) {
            SignUpView (
                navToHome = { navController.navigate(MenuBarOptions.Home.route) },
                navToLogin = { navController.navigate(MenuBarOptions.Login.route) }
            )
        }
        composable(route = MenuBarOptions.Home.route) {
            controller.refetchQueueAPIdata()
            if (viewModel.workoutOngoing.value) {
                navController.navigate(MenuBarOptions.HomeWorkout.route)
            } else {
                HomeView(
                    onInfoClicked = { navController.navigate(MenuBarOptions.Equipment.route) },
                    onSeeAllClicked = { navController.navigate(MenuBarOptions.Saved.route) },
                    onStartClicked = { navController.navigate(MenuBarOptions.HomeWorkout.route) },
                    userViewModel = viewModel,
                    userController = controller
                )
            }
        }
        composable(route = MenuBarOptions.HomeWorkout.route) {
            controller.refetchQueueAPIdata()
            HomeWorkoutView(
                onStopWorkoutClicked = { navController.navigate(MenuBarOptions.Home.route) },
                onEquipmentInfoClicked = { navController.navigate(MenuBarOptions.EquipmentInfo.route) },
                userViewModel = viewModel,
                userController = controller
            )
        }
        composable(route = MenuBarOptions.Settings.route) {
            SettingsView(
                userViewModel = viewModel,
                onSignOutClicked = { navController.navigate(MenuBarOptions.Login.route) },
                userController = controller
            )
        }
        composable(route = MenuBarOptions.Saved.route) {
            controller.refetchQueueAPIdata()
            SavedView(
                onEditWorkoutClicked = { navController.navigate(MenuBarOptions.Equipment.route) },
                onCreateWorkoutClicked = { navController.navigate(MenuBarOptions.Equipment.route) },
                userViewModel = viewModel,
                userController = controller
            )
        }
        composable(route = MenuBarOptions.Equipment.route) {
            controller.refetchQueueAPIdata()
            EquipmentView(
                onEquipmentClicked = { navController.navigate(MenuBarOptions.EquipmentInfo.route) },
                onDoneSelectingClicked = { navController.popBackStack() },
                userViewModel = viewModel,
                userController = controller)
        }
        composable(route = MenuBarOptions.EquipmentInfo.route) {
            EquipmentInfoView(
                userViewModel = viewModel,
                userController = controller
            )
        }
    }
}

