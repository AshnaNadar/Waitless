package org.example.userinterface.Saved
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import org.example.controller.UserController
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.*
import org.example.theme.LightGrey
import org.example.theme.Typography
import org.example.viewmodel.UserViewModel

// add button: https://www.composables.com/icons
@Composable
fun rememberAdd(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "add",
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
                moveTo(20f, 31.458f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.396f)
                quadToRelative(-0.375f, -0.395f, -0.375f, -0.937f)
                verticalLineToRelative(-8.833f)
                horizontalLineTo(9.875f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.375f)
                reflectiveQuadTo(8.542f, 20f)
                quadToRelative(0f, -0.583f, 0.375f, -0.958f)
                reflectiveQuadToRelative(0.958f, -0.375f)
                horizontalLineToRelative(8.833f)
                verticalLineTo(9.833f)
                quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                reflectiveQuadTo(20f, 8.542f)
                quadToRelative(0.542f, 0f, 0.938f, 0.375f)
                quadToRelative(0.395f, 0.375f, 0.395f, 0.916f)
                verticalLineToRelative(8.834f)
                horizontalLineToRelative(8.792f)
                quadToRelative(0.583f, 0f, 0.958f, 0.395f)
                quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                reflectiveQuadToRelative(-0.958f, 0.375f)
                horizontalLineToRelative(-8.792f)
                verticalLineToRelative(8.833f)
                quadToRelative(0f, 0.542f, -0.395f, 0.937f)
                quadToRelative(-0.396f, 0.396f, -0.938f, 0.396f)
                close()
            }
        }.build()
    }
}

// bookmark icon: https://www.composables.com/icons
@Composable
fun rememberBookmark(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "bookmark",
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
                moveTo(11.208f, 30.667f)
                lineTo(20f, 26.917f)
                lineToRelative(8.792f, 3.75f)
                verticalLineTo(7.75f)
                horizontalLineTo(11.208f)
                close()
                moveToRelative(-0.833f, 3.208f)
                quadToRelative(-0.667f, 0.292f, -1.25f, -0.083f)
                reflectiveQuadToRelative(-0.583f, -1.084f)
                verticalLineTo(7.75f)
                quadToRelative(0f, -1.083f, 0.791f, -1.854f)
                quadToRelative(0.792f, -0.771f, 1.875f, -0.771f)
                horizontalLineToRelative(17.584f)
                quadToRelative(1.083f, 0f, 1.875f, 0.771f)
                quadToRelative(0.791f, 0.771f, 0.791f, 1.854f)
                verticalLineToRelative(24.958f)
                quadToRelative(0f, 0.709f, -0.583f, 1.084f)
                quadToRelative(-0.583f, 0.375f, -1.25f, 0.083f)
                lineTo(20f, 29.792f)
                close()
                moveToRelative(0.833f, -26.125f)
                horizontalLineToRelative(17.584f)
                horizontalLineTo(20f)
                close()
            }
        }.build()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedView(
    userViewModel: UserViewModel,
    userController: UserController,
    onEditWorkoutClicked: () -> Unit = {},
    onCreateWorkoutClicked: () -> Unit = {}
) {
    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }

    Box() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(top = 80.dp, start = 20.dp, end = 20.dp)
        ) {
            if (!viewModel.savedWorkouts.value.isEmpty()) {
                LazyColumn(
                    // Scrollable column to go through saved workouts
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    userScrollEnabled = true,
                ) {

                    /* TOP SECTION */
                    stickyHeader {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            /* Page Title */
                            Text(
                                text = "Saved Workouts",
                                style = Typography.headlineMedium,
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            /*
                            Add Button
                                - creates empty Workout and adds to savedWorkouts
                                - routes to addable equipment list
                            */
                            Button(
                                onClick = {
                                    viewModel.addWorkout()
                                    onCreateWorkoutClicked()
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                modifier = Modifier.size(30.dp),
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                Icon(
                                    imageVector = rememberAdd(),
                                    contentDescription = "edit",
                                    modifier = Modifier.size(30.dp),
                                    tint = DarkGreen
                                )
                            }
                        }
                    }

                    /* BOTTOM SECTION */
                    items(viewModel.savedWorkouts.value) { workout ->
                        Row( // Workout display (box)
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                                .background(LightGrey)
                                .padding(10.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    viewModel.editWorkout(workout)
                                    onEditWorkoutClicked()
                                },
                                enabled = !viewModel.workoutOngoing.value // don't allow edits if workout ongoing
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Create,
                                    contentDescription = "edit workout",
                                    modifier = Modifier.size(20.dp),
                                    tint = if (!viewModel.workoutOngoing.value) DarkGreen else LightGreen
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Column( // Workout title
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = workout.name,
                                    style = Typography.bodyLarge
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

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
                }
            } else { // no saved workouts - empty page
                /* Page Title */
                Text(
                    text = "Saved Workouts",
                    style = Typography.headlineMedium
                )

                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(150.dp))

                    Icon(
                        modifier = Modifier.size(150.dp),
                        imageVector = rememberBookmark(),
                        contentDescription = "bookmark",
                        tint = GreyText
                    )

                    Text(
                        text = "You dont't have any saved workouts yet.",
                        color = GreyText,
                        style = Typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            viewModel.addWorkout()
                            onCreateWorkoutClicked()
                        },
                        colors = ButtonDefaults.buttonColors(DarkGreen),
                        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                        modifier = Modifier
                            .widthIn(240.dp)
                            .heightIn(70.dp)
                            .padding(10.dp),
                    ) {
                        Text(
                            text = "Create new",
                            style = Typography.bodyMedium
                        )
                    }
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

