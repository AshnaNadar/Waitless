package org.example.userinterface.Home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.controller.UserController
import org.example.theme.*
import org.example.viewmodel.UserViewModel
import java.time.format.TextStyle

// info button: https://www.composables.com/icons
@Composable
fun rememberInfo(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "info",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f,
            tintColor = DarkGreen
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
                moveTo(20.083f, 28.208f)
                quadToRelative(0.584f, 0f, 0.959f, -0.396f)
                quadToRelative(0.375f, -0.395f, 0.375f, -0.937f)
                verticalLineToRelative(-7.25f)
                quadToRelative(0f, -0.5f, -0.396f, -0.875f)
                reflectiveQuadToRelative(-0.896f, -0.375f)
                quadToRelative(-0.583f, 0f, -0.958f, 0.375f)
                reflectiveQuadToRelative(-0.375f, 0.917f)
                verticalLineToRelative(7.25f)
                quadToRelative(0f, 0.541f, 0.396f, 0.916f)
                quadToRelative(0.395f, 0.375f, 0.895f, 0.375f)
                close()
                moveTo(20f, 15.292f)
                quadToRelative(0.583f, 0f, 1f, -0.396f)
                quadToRelative(0.417f, -0.396f, 0.417f, -1.021f)
                quadToRelative(0f, -0.583f, -0.417f, -1f)
                quadToRelative(-0.417f, -0.417f, -1f, -0.417f)
                quadToRelative(-0.625f, 0f, -1.021f, 0.417f)
                quadToRelative(-0.396f, 0.417f, -0.396f, 1f)
                quadToRelative(0f, 0.625f, 0.417f, 1.021f)
                quadToRelative(0.417f, 0.396f, 1f, 0.396f)
                close()
                moveToRelative(0f, 21.083f)
                quadToRelative(-3.458f, 0f, -6.458f, -1.25f)
                reflectiveQuadToRelative(-5.209f, -3.458f)
                quadToRelative(-2.208f, -2.209f, -3.458f, -5.209f)
                quadToRelative(-1.25f, -3f, -1.25f, -6.458f)
                reflectiveQuadToRelative(1.25f, -6.437f)
                quadToRelative(1.25f, -2.98f, 3.458f, -5.188f)
                quadToRelative(2.209f, -2.208f, 5.209f, -3.479f)
                quadToRelative(3f, -1.271f, 6.458f, -1.271f)
                reflectiveQuadToRelative(6.438f, 1.271f)
                quadToRelative(2.979f, 1.271f, 5.187f, 3.479f)
                reflectiveQuadToRelative(3.479f, 5.188f)
                quadToRelative(1.271f, 2.979f, 1.271f, 6.437f)
                reflectiveQuadToRelative(-1.271f, 6.458f)
                quadToRelative(-1.271f, 3f, -3.479f, 5.209f)
                quadToRelative(-2.208f, 2.208f, -5.187f, 3.458f)
                quadToRelative(-2.98f, 1.25f, -6.438f, 1.25f)
                close()
                moveTo(20f, 20f)
                close()
                moveToRelative(0f, 13.75f)
                quadToRelative(5.667f, 0f, 9.708f, -4.042f)
                quadTo(33.75f, 25.667f, 33.75f, 20f)
                reflectiveQuadToRelative(-4.042f, -9.708f)
                quadTo(25.667f, 6.25f, 20f, 6.25f)
                reflectiveQuadToRelative(-9.708f, 4.042f)
                quadTo(6.25f, 14.333f, 6.25f, 20f)
                reflectiveQuadToRelative(4.042f, 9.708f)
                quadTo(14.333f, 33.75f, 20f, 33.75f)
                close()
            }
        }.build()
    }
}

// play button: https://www.composables.com/icons
@Composable
fun rememberPlayArrow(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "play_arrow",
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
                moveTo(15.542f, 30f)
                quadToRelative(-0.667f, 0.458f, -1.334f, 0.062f)
                quadToRelative(-0.666f, -0.395f, -0.666f, -1.187f)
                verticalLineTo(10.917f)
                quadToRelative(0f, -0.75f, 0.666f, -1.146f)
                quadToRelative(0.667f, -0.396f, 1.334f, 0.062f)
                lineToRelative(14.083f, 9f)
                quadToRelative(0.583f, 0.375f, 0.583f, 1.084f)
                quadToRelative(0f, 0.708f, -0.583f, 1.083f)
                close()
                moveToRelative(0.625f, -10.083f)
                close()
                moveToRelative(0f, 6.541f)
                lineToRelative(10.291f, -6.541f)
                lineToRelative(-10.291f, -6.542f)
                close()
            }
        }.build()
    }
}

// stop circle button: https://www.composables.com/icons
@Composable
fun rememberStopCircle(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "stop_circle",
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
                moveTo(20f, 36.375f)
                quadToRelative(-3.458f, 0f, -6.458f, -1.25f)
                reflectiveQuadToRelative(-5.209f, -3.458f)
                quadToRelative(-2.208f, -2.209f, -3.458f, -5.209f)
                quadToRelative(-1.25f, -3f, -1.25f, -6.458f)
                reflectiveQuadToRelative(1.25f, -6.437f)
                quadToRelative(1.25f, -2.98f, 3.458f, -5.188f)
                quadToRelative(2.209f, -2.208f, 5.209f, -3.479f)
                quadToRelative(3f, -1.271f, 6.458f, -1.271f)
                reflectiveQuadToRelative(6.438f, 1.271f)
                quadToRelative(2.979f, 1.271f, 5.187f, 3.479f)
                reflectiveQuadToRelative(3.479f, 5.188f)
                quadToRelative(1.271f, 2.979f, 1.271f, 6.437f)
                reflectiveQuadToRelative(-1.271f, 6.458f)
                quadToRelative(-1.271f, 3f, -3.479f, 5.209f)
                quadToRelative(-2.208f, 2.208f, -5.187f, 3.458f)
                quadToRelative(-2.98f, 1.25f, -6.438f, 1.25f)
                close()
                moveTo(20f, 20f)
                close()
                moveToRelative(0f, 13.75f)
                quadToRelative(5.833f, 0f, 9.792f, -3.958f)
                quadTo(33.75f, 25.833f, 33.75f, 20f)
                reflectiveQuadToRelative(-3.958f, -9.792f)
                quadTo(25.833f, 6.25f, 20f, 6.25f)
                reflectiveQuadToRelative(-9.792f, 3.958f)
                quadTo(6.25f, 14.167f, 6.25f, 20f)
                reflectiveQuadToRelative(3.958f, 9.792f)
                quadTo(14.167f, 33.75f, 20f, 33.75f)
                close()
                moveToRelative(-5f, -7.458f)
                horizontalLineToRelative(10.042f)
                quadToRelative(0.541f, 0f, 0.916f, -0.354f)
                quadToRelative(0.375f, -0.355f, 0.375f, -0.938f)
                verticalLineTo(14.958f)
                quadToRelative(0f, -0.541f, -0.375f, -0.916f)
                reflectiveQuadToRelative(-0.916f, -0.375f)
                horizontalLineTo(15f)
                quadToRelative(-0.583f, 0f, -0.938f, 0.375f)
                quadToRelative(-0.354f, 0.375f, -0.354f, 0.916f)
                verticalLineTo(25f)
                quadToRelative(0f, 0.583f, 0.354f, 0.938f)
                quadToRelative(0.355f, 0.354f, 0.938f, 0.354f)
                close()
            }
        }.build()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeView(
    userViewModel: UserViewModel,
    userController: UserController,
    onInfoClicked: () -> Unit = {},
    onStartClicked: () -> Unit = {},
) {

    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(top = 80.dp, start = 20.dp, end = 20.dp)
    ) {

        /* Contains all items for this screen. */
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row( // TITLE + INFO BUTTON
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                /* Page Title */
                Text(
                    text = "Today's Workout",
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = DarkGreen,
                        fontSize = 29.sp
                    )
                )

                if (viewModel.selectedWorkout.value.name == "") { // no workout selected

                    Spacer(modifier = Modifier.weight(1f))

                    /*
                    Info Button
                        - routes to equipment list of machines in workout
                    */
                    Button(
                        onClick = onInfoClicked,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier.size(30.dp),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            imageVector = rememberInfo(),
                            contentDescription = "edit",
                            modifier = Modifier.size(20.dp),
                            tint = DarkGreen
                        )
                    }
                }
            }

            Column( // TOP SECTION
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .heightIn(120.dp)
                    .padding(top = 12.dp)
            ) {
                if (viewModel.selectedWorkout.value.name == "") { // No workout selected
                    /* Label visible if no workout selected.*/
                    Text(
                        text = "No active workout - start one now!",
                        style = Typography.bodyMedium,
                        color = DarkGrey,
                        textAlign = TextAlign.Center
                    )

                    /*
                    Add machines button
                        - visible if no workout selected
                        - creates a saved workout
                    */
                    Button(
                        onClick = {
                            viewModel.addWorkout()
                            onInfoClicked() },
                        colors = ButtonDefaults.buttonColors(DarkGreen),
                        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                        modifier = Modifier
                            .widthIn(240.dp)
                            .heightIn(70.dp)
                            .padding(10.dp),
                    ) {
                        Text(
                            text = "Add Machines",
                            style = Typography.bodyMedium
                        )
                    }
                } else { // Workout selected
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { // Selected workout + play button
                        Row( // Workout display (box)
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                                .background(LightGrey)
                                .weight(1f)
                                .heightIn(80.dp)
                                .padding(10.dp)
                        ) {
                            Column( // Workout title
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = viewModel.selectedWorkout.value.name,
                                    style = Typography.bodyLarge
                                )
                            }

                            Column( // Number of machines
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = viewModel.selectedWorkout.value.machines.size.toString(),
                                    style = Typography.bodyLarge
                                )
                                Text(
                                    text = "machines",
                                    style = Typography.bodyLarge
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        /*
                        Play button
                            - starts the selected workout
                        */
                        Button(
                            onClick = {onStartClicked()
                                controller.startWorkout()},
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(DarkGreen),
                            modifier = Modifier
                                .size(60.dp),
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Icon(
                                imageVector = rememberPlayArrow(),
                                contentDescription = "start",
                                modifier = Modifier.size(30.dp),
                            )
                        }
                    }
                }
            }

            /* BOTTOM SECTION */
            Column (
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    /* Your Workouts Heading */
                    Text(
                        text = "Your Workouts",
                        style = androidx.compose.ui.text.TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen,
                            fontSize = 25.sp
                        )
                    )
                }

                Column( // Scrollable column to go through saved workouts
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f, false)
                ) {
                    viewModel.savedWorkouts.value.forEach { workout ->
                        Row( // Workout display (box)
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .clickable { controller.selectWorkout(workout) }
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                                .background(LightGrey)
                                .padding(10.dp)
                        ) {
                            Column( // Workout title
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = workout.name,
                                    style = Typography.bodyLarge
                                )
                            }

                            Column( // Number of machines
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = workout.machines.size.toString(),
                                    style = Typography.bodyLarge
                                )
                                Text(
                                    text = "machines",
                                    style = Typography.bodyLarge
                                )
                            }
                        }
                    }
                    // prevent nav bar from blocking bottom item
                    Spacer(modifier = Modifier.height(300.dp))
                }
            }
        }


        // gradient at bottom of screen
        val gradient = Brush.verticalGradient(
            colors = listOf(Background, Color.Transparent),
            startY = Float.POSITIVE_INFINITY,
            endY = 0.0f
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
                .background(gradient)
        )
    }
}

