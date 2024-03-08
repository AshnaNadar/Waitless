package org.example.userinterface.Login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class LoginRequest(val email: String, val password: String)

@Preview
@OptIn(ExperimentalMaterial3Api::class, InternalAPI::class)
@Composable
fun LoginView(
    onLoginButtonClicked: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var showErrorMessage by remember { mutableStateOf(false) }
    var errorText = ""
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Login Screen") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxWidth()
            .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            TextField(
                email,
                label = {Text("Username: ")},
                onValueChange = { email = it })
            TextField(
                password,
                label = {Text("Password: ")},
                onValueChange = { password = it },
                visualTransformation =  PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))

            if (showErrorMessage) {
                Text(
                    text = errorText,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            Row (horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = {
                    coroutineScope.launch {
                        val httpClient = HttpClient()
                        try {
                            Log.d("TRY Block:", "${email}, ${password}")
                            val response: HttpResponse = httpClient.post("http://10.0.2.2:8080/auth/signin") {
                                contentType(ContentType.Application.Json)
                                body = Json.encodeToString(LoginRequest(email, password))
                            }
                            when (response.status.value) {
                                in 200..299 -> {
                                    // Successful login
                                    val responseBody: String = response.body()
                                    Log.d("ResponseLogin:", responseBody)
                                    onLoginButtonClicked()
                                }
                                else -> {
                                    // Other error occurred
                                    Log.d("ResponseLogin:", "Unexpected response code: ${response.status.value}")
                                    showErrorMessage = true
                                    errorText = "Invalid Username/Password"
                                }
                            }
                        } catch (e: Exception) {
                            println("Error: ${e.message}")
                            showErrorMessage = true
                            errorText = "Unable to connect to DB"
                        } finally {
                            httpClient.close()
                        }
                    }
                }) {
                    Text("Log In")
                }
            }
        }
    }
}


