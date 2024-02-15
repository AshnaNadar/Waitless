package org.example

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.userinterface.LoginView
import org.example.userinterface.UserView

/**
 * enum values that represent the screens in the app
 */
enum class WaitlessScreen(@StringRes val title: Int) {
    Login(title = R.string.app_name),
    Today(title = R.string.todays_workout),
    Saved(title = R.string.saved_workouts),
    Equipment(title = R.string.equipment_avail),
    Settings(title = R.string.settings)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitlessAppBar(
    currentScreen: WaitlessScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

/**
 * Composable that displays the bottomBar with menu buttons.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitlessMenuBar(
    onEquipmentButtonClicked: () -> Unit,
    onTodayButtonClicked: () -> Unit,
    onSavedButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar {
        Row (horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = onEquipmentButtonClicked) {
                Text("Equip")
            }
            Button(onClick = onTodayButtonClicked) {
                Text("Today")
            }
            Button(onClick = onSavedButtonClicked) {
                Text("Saved")
            }
            Button(onClick = onSettingsButtonClicked) {
                Text("Settings")
            }
        }
    }
}
@Preview
@Composable
fun WaitlessApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = WaitlessScreen.valueOf(
        backStackEntry?.destination?.route ?: WaitlessScreen.Today.name
    )

    Scaffold(
//        topBar = {
//            WaitlessAppBar(
//                currentScreen = currentScreen,
//                canNavigateBack = navController.previousBackStackEntry != null,
//                navigateUp = { navController.navigateUp() }
//            )
//        },
//        bottomBar = {
//            WaitlessMenuBar(
//                onEquipmentButtonClicked = { navController.navigate(WaitlessScreen.Equipment.name) },
//                onSavedButtonClicked = { navController.navigate(WaitlessScreen.Saved.name) },
//                onSettingsButtonClicked = { navController.navigate(WaitlessScreen.Settings.name) },
//                onTodayButtonClicked = { navController.navigate(WaitlessScreen.Today.name) })
//        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = WaitlessScreen.Login.name,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable(route = WaitlessScreen.Login.name) {
                LoginView( // routes to LoginView.kt
                    onLoginButtonClicked = { navController.navigate(WaitlessScreen.Today.name) },
//                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = WaitlessScreen.Today.name) {
                UserView( // routes to UserView.kt
                    onEquipmentButtonClicked = { navController.navigate(WaitlessScreen.Equipment.name) },
                    onTodayButtonClicked = { navController.navigate(WaitlessScreen.Today.name) },
                    onSavedButtonClicked = { navController.navigate(WaitlessScreen.Saved.name) },
                    onSettingsButtonClicked = { navController.navigate(WaitlessScreen.Settings.name) },
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigate(WaitlessScreen.Login.name) }
//                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}
