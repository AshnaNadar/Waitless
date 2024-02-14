package org.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import org.example.theme.WaitlessTheme
import org.example.controller.UserController
import org.example.model.UserModel
import org.example.userinterface.UserView
import org.example.userinterface.UserViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.webkit.Profile

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userModel = UserModel()
        val userViewModel = UserViewModel(userModel)
        val userController = UserController(userModel)

        setContent {
            WaitlessTheme {
                WaitlessApp()
                //UserView(userViewModel, userController)
            }
        }
    }
}
