package org.example.userinterface.Login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.R
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.DarkGrey
import org.example.theme.GreyText
import org.example.theme.Typography

@Serializable
data class SignUpRequest(val email: String, val password: String)

@OptIn(ExperimentalMaterial3Api::class, InternalAPI::class)
@Composable
fun SignUpView(
    navToHome: () -> Unit,
    navToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var showErrorMessage by remember { mutableStateOf(false) }
    var errorText = ""
    val coroutineScope = rememberCoroutineScope()

    Scaffold() { innerPadding ->
        /* Contains all items for this screen. */
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGreen)
                .padding(innerPadding)
        ) {

            // TOP SECTION
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .heightIn(250.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxSize()
                )
                Text(
                    text = "Welcome!",
                    style = Typography.displaySmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Column( // MAIN SECTION
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                    .background(Background)
                    .padding(20.dp),
            ) {
                Row() {
                    Spacer(modifier = Modifier.weight(0.5f))

                    Text(
                        text = "Back to Login",
                        color = DarkGreen,
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { navToLogin() }
                    )
                }
                Spacer(modifier = Modifier.weight(0.5f))
//
//                    /*
//                    Close Button
//                        - routes to opening page (NULL currently)
//                    */
//                    Button( // close button
//                        onClick = {}, //  TEMP: for testing
//                        shape = CircleShape,
//                        colors = ButtonDefaults.buttonColors(Color.Transparent),
//                        modifier = Modifier.size(40.dp),
//                        contentPadding = PaddingValues(0.dp),
//                    ) {
//                        Icon(
//                            imageVector = rememberClose(),
//                            contentDescription = "close",
//                            modifier = Modifier.size(30.dp),
//                            tint = GreyText
//                        )
//                    }
//                }

                Text(
                    text = "Username",
                    color = DarkGrey
                )
                OutlinedTextField(
                    email,
                    label = {Text(
                        text = "Enter your username",
                        color = GreyText)},
                    onValueChange = { username = it },
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                    textStyle = TextStyle(color = DarkGrey),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = DarkGrey,
                        unfocusedBorderColor = GreyText),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Email",
                    color = DarkGrey
                )
                OutlinedTextField(
                    email,
                    label = {Text(
                        text = "Enter your email address",
                        color = GreyText)},
                    onValueChange = { email = it },
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                    textStyle = TextStyle(color = DarkGrey),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = DarkGrey,
                        unfocusedBorderColor = GreyText),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Password",
                    color = DarkGrey
                )
                OutlinedTextField(
                    password,
                    label = {Text(
                        text = "Enter your password",
                        color = GreyText)},
                    onValueChange = { password = it },
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                    visualTransformation =  PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(color = DarkGrey),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = DarkGrey,
                        unfocusedBorderColor = GreyText),
                    supportingText = {
                        if (showErrorMessage) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorText,
                                color = Color.Red
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1.0f))

                Row (horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            // Add Logic for Sign Up here!!
                            coroutineScope.launch {
                                val httpClient = HttpClient()
                                try {
                                    Log.d("TRY Block:", "${email}, ${password}")
                                    val response: HttpResponse =
                                        httpClient.post("https://cs346-server-d1175eb4edfc.herokuapp.com/auth/signin") {
                                            contentType(ContentType.Application.Json)
                                            body =
                                                Json.encodeToString(LoginRequest(email, password))
                                        }
                                    when (response.status.value) {
                                        in 200..299 -> {
                                            // Successful login
                                            val responseBody: String = response.body()
                                            Log.d("ResponseLogin:", responseBody)
                                            navToHome()
                                        }

                                        else -> {
                                            // Other error occurred
                                            Log.d(
                                                "ResponseLogin:",
                                                "Unexpected response code: ${response.status.value}"
                                            )
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
                        }, // For Testing Purposes
                        colors = ButtonDefaults.buttonColors(DarkGreen),
                        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(70.dp)
                            .padding(0.dp, 10.dp)
                    ) {
                        Text(
                            text = "Sign Up",
                            style = Typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
