package org.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.example.theme.WaitlessTheme
import org.example.controller.UserController
import org.example.model.UserModel
import org.example.model.UserViewModel

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
