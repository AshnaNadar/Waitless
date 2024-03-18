package org.example.userinterface.Login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.DarkGrey
import org.example.theme.GreyText
import org.example.theme.LightGreen
import org.example.theme.LightGrey
import org.example.theme.Typography
import org.example.userinterface.Home.rememberInfo

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class SignUpRequest(val email: String, val password: String)

// close button: https://www.composables.com/icons
@Composable
fun rememberClose(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "close",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 21.875f)
                lineToRelative(-8.5f, 8.5f)
                quadToRelative(-0.417f, 0.375f, -0.938f, 0.375f)
                quadToRelative(-0.52f, 0f, -0.937f, -0.375f)
                quadToRelative(-0.375f, -0.417f, -0.375f, -0.937f)
                quadToRelative(0f, -0.521f, 0.375f, -0.938f)
                lineToRelative(8.542f, -8.542f)
                lineToRelative(-8.542f, -8.5f)
                quadToRelative(-0.375f, -0.375f, -0.375f, -0.916f)
                quadToRelative(0f, -0.542f, 0.375f, -0.917f)
                quadToRelative(0.417f, -0.417f, 0.937f, -0.417f)
                quadToRelative(0.521f, 0f, 0.938f, 0.417f)
                lineToRelative(8.5f, 8.5f)
                lineToRelative(8.5f, -8.5f)
                quadToRelative(0.417f, -0.375f, 0.938f, -0.375f)
                quadToRelative(0.52f, 0f, 0.937f, 0.375f)
                quadToRelative(0.375f, 0.417f, 0.375f, 0.938f)
                quadToRelative(0f, 0.52f, -0.375f, 0.937f)
                lineTo(21.833f, 20f)
                lineToRelative(8.542f, 8.542f)
                quadToRelative(0.375f, 0.375f, 0.396f, 0.916f)
                quadToRelative(0.021f, 0.542f, -0.396f, 0.917f)
                quadToRelative(-0.375f, 0.375f, -0.917f, 0.375f)
                quadToRelative(-0.541f, 0f, -0.916f, -0.375f)
                close()
            }
        }.build()
    }
}

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

    Scaffold() { innerPadding ->
        /* Contains all items for this screen. */
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGreen)
                .padding(innerPadding)
        ) {

            Column( // TOP SECTION
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .heightIn(250.dp)
            ) {
                Text(
                    text = "Log In",
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
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
                    Spacer(modifier = Modifier.weight(1.0f))

                    /*
                    Close Button
                        - routes to opening page (NULL currently)
                    */
                    Button( // close button
                        onClick = onLoginButtonClicked, //  TEMP: for testing
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            imageVector = rememberClose(),
                            contentDescription = "close",
                            modifier = Modifier.size(30.dp),
                            tint = GreyText
                        )
                    }
                }

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
                        text = "Insert password",
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
                        onClick = { onLoginButtonClicked() }, // For Testing Purposes
                        colors = ButtonDefaults.buttonColors(DarkGreen),
                        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(70.dp)
                            .padding(0.dp, 10.dp)
                    ) {
                        Text(
                            text = "Log In",
                            style = Typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

//onClick = {
//                            coroutineScope.launch {
//                                val httpClient = HttpClient()
//                                try {
//                                    Log.d("TRY Block:", "${email}, ${password}")
//                                    val response: HttpResponse =
//                                        httpClient.post("http://10.0.2.2:8080/auth/signin") {
//                                            contentType(ContentType.Application.Json)
//                                            body =
//                                                Json.encodeToString(LoginRequest(email, password))
//                                        }
//                                    when (response.status.value) {
//                                        in 200..299 -> {
//                                            // Successful login
//                                            val responseBody: String = response.body()
//                                            Log.d("ResponseLogin:", responseBody)
//                                            onLoginButtonClicked()
//                                        }
//
//                                        else -> {
//                                            // Other error occurred
//                                            Log.d(
//                                                "ResponseLogin:",
//                                                "Unexpected response code: ${response.status.value}"
//                                            )
//                                            showErrorMessage = true
//                                            errorText = "Invalid Username/Password"
//                                        }
//                                    }
//                                } catch (e: Exception) {
//                                    println("Error: ${e.message}")
//                                    showErrorMessage = true
//                                    errorText = "Unable to connect to DB"
//                                } finally {
//                                    httpClient.close()
//                                }
//                            }
//                        },

