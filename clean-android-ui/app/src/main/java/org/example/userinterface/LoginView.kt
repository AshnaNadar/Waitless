package org.example.userinterface

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.example.controller.UserController
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

/*
contains 2 text fields for username/password & login button that
routes to home page (UserView.kt)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    onLoginButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("Test") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Login Screen") })
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.SpaceEvenly) {
            TextField(
                text,
                label = {Text("Username: ")},
                onValueChange = { text = it })
            TextField(
                text,
                label = {Text("Password: ")},
                onValueChange = { text = it })

            Row (horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = onLoginButtonClicked) {
                    Text("Log In")
                }
            }
        }
    }
}