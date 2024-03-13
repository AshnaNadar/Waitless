package org.example.userinterface.Equipment
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.controller.UserController
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.DarkGrey
import org.example.theme.LightGreen
import org.example.theme.LightGrey
import org.example.theme.Typography
import org.example.userinterface.Home.rememberPlayArrow
import org.example.userinterface.MenuBarOptions
import org.example.userinterface.Saved.rememberAdd
import org.example.userinterface.UserViewModel

@Composable
fun rememberDone(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "done",
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
                moveTo(15.833f, 29.125f)
                quadToRelative(-0.25f, 0f, -0.479f, -0.083f)
                quadToRelative(-0.229f, -0.084f, -0.437f, -0.292f)
                lineToRelative(-7.334f, -7.333f)
                quadToRelative(-0.375f, -0.375f, -0.375f, -0.938f)
                quadToRelative(0f, -0.562f, 0.375f, -0.979f)
                quadToRelative(0.375f, -0.375f, 0.938f, -0.375f)
                quadToRelative(0.562f, 0f, 0.937f, 0.375f)
                lineToRelative(6.375f, 6.375f)
                lineTo(30.5f, 11.208f)
                quadToRelative(0.375f, -0.375f, 0.938f, -0.375f)
                quadToRelative(0.562f, 0f, 0.979f, 0.375f)
                quadToRelative(0.375f, 0.375f, 0.375f, 0.938f)
                quadToRelative(0f, 0.562f, -0.375f, 0.937f)
                lineTo(16.75f, 28.75f)
                quadToRelative(-0.208f, 0.208f, -0.438f, 0.292f)
                quadToRelative(-0.229f, 0.083f, -0.479f, 0.083f)
                close()
            }
        }.build()
    }
}

@Composable
fun EquipmentView(
    userViewModel: UserViewModel,
    userController: UserController,
    onEquipmentClicked: () -> Unit = {}
) {
    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }

    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(top = 80.dp, start = 20.dp, end = 20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                /* Page Title */
                Text(
                    text = "Equipment",
                    style = Typography.headlineMedium,
                )

                if (viewModel.creatingWorkout.value) { // if creating workout

                    Spacer(modifier = Modifier.weight(1f))

                    /*
                    Done Button
                        - prompts user to name workout
                        - goes back to saved workout/home page
                    */
                    Button(
                        onClick = { },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier.size(30.dp),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            imageVector = rememberDone(),
                            contentDescription = "edit",
                            modifier = Modifier.size(20.dp),
                            tint = DarkGrey
                        )
                    }
                }
            }

            Column( // Scrollable column to go through equipment
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {
                viewModel.allMachineNames.value.forEach { machineName ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {


                        if (viewModel.creatingWorkout.value) { // if creating workout
                            /*
                            Add button
                                - adds machine to last Workout in savedWorkouts
                            */
                            Button(
                                onClick = { },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(DarkGreen),
                                modifier = Modifier
                                    .shadow(5.dp, CircleShape)
                                    .size(40.dp),
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                Icon(
                                    imageVector = rememberAdd(),
                                    contentDescription = "add machine",
                                    modifier = Modifier.size(30.dp),
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))
                        }

                        Row( // machine display (box)
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {

                                }
                                .fillMaxWidth()
                                .heightIn(40.dp)
                                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                                .background(LightGrey)
                                .padding(10.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Text( // machine name
                                    text = machineName,
                                    style = Typography.bodyLarge
                                )

                                /*
                                View Info Button
                                - routes to saved workouts page
                                */
                                TextButton(
                                    onClick = onEquipmentClicked,
                                    // colors = ButtonDefaults.buttonColors(LightGreen)
                                ) {
                                    Text(
                                        modifier = Modifier.drawBehind {
                                            val strokeWidthPx = 1.dp.toPx()
                                            val verticalOffset = size.height - 2.sp.toPx()
                                            drawLine(
                                                color = DarkGrey,
                                                strokeWidth = strokeWidthPx,
                                                start = Offset(0f, verticalOffset),
                                                end = Offset(size.width, verticalOffset)
                                            )
                                        },
                                        text = "View Info",
                                        style = Typography.bodyMedium,
                                        color = DarkGrey
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Column( // Number of people in queue
                                horizontalAlignment = Alignment.End,
                            ) {
                                Text(
                                    text = "X",
                                    style = Typography.bodyLarge
                                )
                                Text(
                                    text = "in queue",
                                    style = Typography.bodyLarge
                                )
                            }
                        }
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

