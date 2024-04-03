package org.example
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.navigation.MenuBarGraph
import org.example.navigation.MenuBarOptions
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import org.example.controller.UserController
import org.example.viewmodel.UserViewModel
import org.example.theme.* // import all colors and themes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WaitlessApp(userViewModel: UserViewModel, userController: UserController,
                navController: NavHostController = rememberNavController()) {

    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }

    var showNav by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    showNav = when (navBackStackEntry?.destination?.route) {
        // on this screen bottom bar should be hidden
        MenuBarOptions.SignUp.route -> false
        MenuBarOptions.Login.route -> false
        else -> true // in all other cases show bottom bar
    }
    Scaffold(
        topBar = {
            if (showNav)
                WaitlessTopBar(
                    currentScreen = navBackStackEntry?.destination?.route ?: MenuBarOptions.Home.route,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = {navController.navigate(MenuBarOptions.Login.route)}
                )
        },
        bottomBar = {
            if (showNav) WaitlessMenuBar(navController = navController)
        }
    )
    {
        MenuBarGraph(navController = navController, userViewModel = viewModel, userController = controller)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitlessTopBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.split("/").last().replaceFirstChar { it.uppercase() } )},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
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

@Composable
fun WaitlessMenuBar(navController: NavHostController) {
    val screens = listOf(
        MenuBarOptions.Home,
        MenuBarOptions.Equipment,
        MenuBarOptions.Saved,
        MenuBarOptions.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column( // to align stuff to the center of the page
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box( // so that the bg image can be added
            modifier = Modifier
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.menubar),
                contentDescription = null, // Provide a content description if needed
            )
            Row(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: MenuBarOptions,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    val selected = currentDestination?.hierarchy?.any {
        val test: String = it.route?: ""
        test.split("/")[0] == screen.route
    } == true

    val background = if (selected) {
        ImageBitmap.imageResource(id = R.drawable.kettleball)
    } else {
        ImageBitmap.imageResource(id = R.drawable.kettleball_transparent)
    }

    Box (
        modifier = Modifier
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = background,
            contentDescription = null,
            modifier = Modifier
                .height(110.dp)
                .padding(bottom = 15.dp, start = 4.dp, end = 4.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint = if (selected) DarkGreen else Color.White
            )
            Text(text = screen.title,
                fontSize = 12.sp,
                color = if (selected) DarkGreen else Color.White
            )
        }
    }
}
