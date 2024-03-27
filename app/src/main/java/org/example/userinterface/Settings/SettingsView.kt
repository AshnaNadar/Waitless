package org.example.userinterface.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.compose.ui.window.Dialog
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.DarkGrey
import org.example.theme.GreyText
import org.example.theme.LightGrey
import org.example.theme.Typography
import org.example.userinterface.Login.rememberClose
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.controller.UserController
import org.example.model.UserUpdate
import org.example.viewmodel.UserViewModel

// forward arrow icon: https://www.composables.com/icons
@Composable
fun rememberKeyboardArrowRight(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "keyboard_arrow_right",
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
                moveTo(14.75f, 28.875f)
                quadToRelative(-0.375f, -0.417f, -0.396f, -0.937f)
                quadToRelative(-0.021f, -0.521f, 0.396f, -0.938f)
                lineToRelative(7.042f, -7.042f)
                lineToRelative(-7.084f, -7.041f)
                quadToRelative(-0.375f, -0.375f, -0.354f, -0.938f)
                quadToRelative(0.021f, -0.562f, 0.396f, -0.937f)
                quadToRelative(0.417f, -0.417f, 0.938f, -0.417f)
                quadToRelative(0.52f, 0f, 0.895f, 0.417f)
                lineToRelative(8.042f, 8f)
                quadToRelative(0.208f, 0.208f, 0.292f, 0.437f)
                quadToRelative(0.083f, 0.229f, 0.083f, 0.479f)
                quadToRelative(0f, 0.292f, -0.083f, 0.5f)
                quadToRelative(-0.084f, 0.209f, -0.292f, 0.417f)
                lineToRelative(-8f, 8f)
                quadToRelative(-0.417f, 0.417f, -0.937f, 0.396f)
                quadToRelative(-0.521f, -0.021f, -0.938f, -0.396f)
                close()
            }
        }.build()
    }
}

// log out icon: https://www.composables.com/icons
@Composable
fun rememberLogout(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "logout",
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
                moveTo(26.417f, 26.5f)
                quadToRelative(-0.375f, -0.417f, -0.375f, -0.958f)
                quadToRelative(0f, -0.542f, 0.375f, -0.917f)
                lineToRelative(3.25f, -3.25f)
                horizontalLineTo(16.833f)
                quadToRelative(-0.541f, 0f, -0.937f, -0.375f)
                reflectiveQuadToRelative(-0.396f, -0.917f)
                quadToRelative(0f, -0.583f, 0.396f, -0.958f)
                reflectiveQuadToRelative(0.937f, -0.375f)
                horizontalLineToRelative(12.792f)
                lineToRelative(-3.292f, -3.292f)
                quadToRelative(-0.375f, -0.333f, -0.354f, -0.875f)
                quadToRelative(0.021f, -0.541f, 0.396f, -0.958f)
                quadToRelative(0.375f, -0.375f, 0.937f, -0.375f)
                quadToRelative(0.563f, 0f, 0.98f, 0.375f)
                lineToRelative(5.5f, 5.542f)
                quadToRelative(0.208f, 0.208f, 0.312f, 0.437f)
                quadToRelative(0.104f, 0.229f, 0.104f, 0.479f)
                quadToRelative(0f, 0.292f, -0.104f, 0.5f)
                quadToRelative(-0.104f, 0.209f, -0.312f, 0.417f)
                lineToRelative(-5.5f, 5.542f)
                quadToRelative(-0.375f, 0.375f, -0.917f, 0.354f)
                quadToRelative(-0.542f, -0.021f, -0.958f, -0.396f)
                close()
                moveToRelative(-18.5f, 8.375f)
                quadToRelative(-1.084f, 0f, -1.855f, -0.792f)
                quadToRelative(-0.77f, -0.791f, -0.77f, -1.875f)
                verticalLineTo(7.917f)
                quadToRelative(0f, -1.084f, 0.77f, -1.854f)
                quadToRelative(0.771f, -0.771f, 1.855f, -0.771f)
                horizontalLineToRelative(10.541f)
                quadToRelative(0.542f, 0f, 0.938f, 0.375f)
                quadToRelative(0.396f, 0.375f, 0.396f, 0.916f)
                quadToRelative(0f, 0.584f, -0.396f, 0.959f)
                reflectiveQuadToRelative(-0.938f, 0.375f)
                horizontalLineTo(7.917f)
                verticalLineToRelative(24.291f)
                horizontalLineToRelative(10.541f)
                quadToRelative(0.542f, 0f, 0.938f, 0.396f)
                quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                quadToRelative(0f, 0.541f, -0.396f, 0.937f)
                reflectiveQuadToRelative(-0.938f, 0.396f)
                close()
            }
        }.build()
    }
}

@Composable
fun SettingsView(
    userViewModel: UserViewModel,
    onSignOutClicked: () -> Unit = {},
    userController: UserController
) {
    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }
    var showDialog by remember { mutableStateOf(false)}
    var dialogType by remember { mutableStateOf("")}

    Scaffold() { innerPadding ->
        /* Contains all items for this screen. */
        Column (modifier = Modifier.background(Background)) {
            Column(  // TOP SECTION
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(175.dp)
                    .clip(shape = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
                    .background(DarkGreen)
                    .padding(innerPadding)
            ) {

                Text(
                    text = viewModel.name.value, /* CHANGE ME */
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                )
            }

            Column( // MAIN SECTION
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text( // Section Heading
                    text = "Personal Details",
                    color = DarkGrey,
                    style = Typography.headlineSmall,
                    fontSize = 20.sp
                )

                Row( // Display Name
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(LightGrey)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Display Name",
                        color = DarkGrey,
                        style = Typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = viewModel.name.value, /* EDIT ME */
                        color = Color.Gray,
                        style = Typography.bodyLarge
                    )

                    IconButton(
                        onClick = {
                            dialogType = "Name"
                            showDialog = true
                        },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Image(
                            imageVector = rememberKeyboardArrowRight(),
                            contentDescription = "forward arrow",
                            colorFilter = ColorFilter.tint(DarkGrey),
                            modifier = Modifier
                                .size(30.dp))
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row( // Email
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(LightGrey)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Email",
                        color = DarkGrey,
                        style = Typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = viewModel.email.value, /* EDIT ME */
                        color = Color.Gray,
                        style = Typography.bodyLarge
                    )

                    IconButton(
                        onClick = {
                            dialogType = "Email"
                            showDialog = true
                        },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Image(
                            imageVector = rememberKeyboardArrowRight(),
                            contentDescription = "forward arrow",
                            colorFilter = ColorFilter.tint(DarkGrey),
                            modifier = Modifier
                                .size(30.dp))
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row( // Change Password
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(LightGrey)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Change Your Password",
                        color = DarkGrey,
                        style = Typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            dialogType = "Password"
                            showDialog = true
                        },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Image(
                            imageVector = rememberKeyboardArrowRight(),
                            contentDescription = "forward arrow",
                            colorFilter = ColorFilter.tint(DarkGrey),
                            modifier = Modifier
                                .size(30.dp))
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row( // Your Gym
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(LightGrey)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Your Gym",
                        color = DarkGrey,
                        style = Typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "PAC",
                        color = Color.Gray,
                        style = Typography.bodyLarge
                    )

                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(30.dp),
                        enabled = false // disabled; limit to PAC gym for now
                    ) {
                        Image(
                            imageVector = rememberKeyboardArrowRight(),
                            contentDescription = "forward arrow",
                            colorFilter = ColorFilter.tint(DarkGrey),
                            modifier = Modifier
                                .size(30.dp))
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text( // Section Heading
                    text = "Notification Settings",
                    color = DarkGrey,
                    style = Typography.headlineSmall,
                    fontSize = 20.sp
                )

                Button( // Enable Notifications
                    onClick = {
                        /* EDIT ME: enable notifications */
                    },
                    colors = ButtonDefaults.buttonColors(LightGrey),
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(70.dp)
                        .padding(0.dp, 10.dp)
                ) {
                    Text(
                        text = "Enable Urgent Notifications",
                        color = Color.Gray,
                        style = Typography.bodyLarge
                    )
                }

                Row( // Sign out
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            /* EDIT ME: sign out functionality */
                            viewModel.viewModelScope.launch {
                                viewModel.signOut()
                                onSignOutClicked()
                            }
                        },
                        modifier = Modifier.size(30.dp),
                    ) {
                        Image(
                            imageVector = rememberLogout(),
                            contentDescription = "forward arrow",
                            colorFilter = ColorFilter.tint(DarkGreen),
                            modifier = Modifier
                                .size(30.dp))
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Sign Out",
                        color = DarkGreen,
                        style = Typography.bodyMedium
                    )
                }

                if (showDialog) {
                    EditDialog(
                        onDismissRequest = { showDialog = false },
                        onConfirmation = {
                            val body = UserUpdate(
                                name = "",
                                email = "",
                                password = ""
                            )
                            if (dialogType === "Name") {
                                body.name = it
                            } else if (dialogType === "Email") {
                                body.email
                            } else if (dialogType === "Password") {
                                body.password
                            }
                            println(body)
                            controller.updateUserInfo(body)

                            /* EDIT ME: functionality based on dialogType */
                            showDialog = false },
                        newValue = "",
                        category = dialogType,
                        setShowDialog = { showDialog = it }
                    )
                }
            }
        }
    }
}

// Popup dialog box to edit values
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    setShowDialog: (Boolean) -> Unit,
    newValue: String,
    category: String
) {
    val textField = remember { mutableStateOf(newValue) }
    var showErrorMessage by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {

        Surface (
            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
            color = LightGrey
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Row {
                    Text(text = "Edit Your $category")

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        // close button
                        onClick = { onDismissRequest() },
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

                OutlinedTextField(
                    textField.value,
                    label = {Text(
                        text = "Enter new $category",
                        color = GreyText)},
                    onValueChange = { textField.value = it },
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                    textStyle = TextStyle(color = DarkGrey),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = DarkGrey,
                        unfocusedBorderColor = GreyText,
                    ),
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
                        .heightIn(70.dp)
                        .padding(0.dp, 10.dp)
                )

                Button(
                    onClick = {
                        if (textField.value.isEmpty()) {
                            showErrorMessage = true
                            errorText = "Field can not be empty"
                            return@Button
                        }
                        onConfirmation(textField.value)
                        setShowDialog(false)
                    },
                    colors = ButtonDefaults.buttonColors(DarkGreen),
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(70.dp)
                        .padding(0.dp, 10.dp)
                ) {
                    Text(
                        text = "Done",
                        style = Typography.bodyMedium
                    )
                }

            }
        }

    }
}

