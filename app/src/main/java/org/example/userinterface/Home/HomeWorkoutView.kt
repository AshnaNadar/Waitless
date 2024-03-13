package org.example.userinterface.Home


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
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.controller.UserController
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.GreyText
import org.example.theme.LightGreen
import org.example.theme.LightGrey
import org.example.theme.Pink40
import org.example.theme.Pink80
import org.example.theme.Purple40
import org.example.theme.PurpleGrey40
import org.example.theme.PurpleGrey80
import org.example.theme.Typography
import org.example.userinterface.Equipment.EquipmentView
import org.example.userinterface.UserViewModel

@Composable
fun HomeWorkoutView(
    userViewModel: UserViewModel,
    userController: UserController,
    onStopWorkoutClicked: () -> Unit = {},
    onLastSetClicked: () -> Unit = {},
    onEquipmentInfoClicked: () -> Unit = {}
) {
    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }

    var currentWorkoutList by remember { mutableStateOf(listOf<String>()) }
    var upcomingMachines by remember { mutableStateOf(listOf<String>()) }
    var currentMachine by remember { mutableStateOf("") }

    currentWorkoutList = viewModel.savedWorkouts.value[viewModel.selectedWorkout.value] ?: emptyList()
    upcomingMachines = currentWorkoutList.toMutableList()
    if (upcomingMachines.isNotEmpty()) {
        currentMachine = upcomingMachines.first()
        upcomingMachines = upcomingMachines.drop(1)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(top = 80.dp, start = 20.dp, end = 20.dp)
    ) {

        /* Contains all items for this screen. */
        Column(
            horizontalAlignment = Alignment.Start
        ) {

            Row( // TITLE + STOP BUTTON
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                /* Page Title */
                Text(
                    text = "Current Machine",
                    style = Typography.headlineMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                /*
                Stop Button
                    - routes back to home page
                */
                Button(
                    onClick = onStopWorkoutClicked,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    modifier = Modifier.size(30.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Icon(
                        imageVector = rememberStopCircle(),
                        contentDescription = "stop",
                        modifier = Modifier.size(20.dp),
                        tint = DarkGreen
                    )
                }

            }

            Column( // TOP SECTION
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .heightIn(120.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { // Selected workout + play button
                    Row( // Machine display (box)
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                            .background(LightGrey)
                            .weight(1f)
                            .heightIn(80.dp)
                            .padding(10.dp)
                    ) {
                        Column( // Machine title
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = currentMachine,
                                style = Typography.bodyLarge
                            )

                            /*
                            Info Button
                                - routes to equipment info page
                            */
                            Button(
                                onClick = onEquipmentInfoClicked,
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

                        Column( // Time Elapsed
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "X:XX",
                                style = Typography.bodyLarge
                            )
                            Text(
                                text = "mins elapsed",
                                style = Typography.bodyLarge
                            )
                        }
                    }
                }

                /*
                Last Set button
                    - does nothing rn
                */
                Button(
                    onClick = onLastSetClicked,
                    colors = ButtonDefaults.buttonColors(DarkGreen),
                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(70.dp)
                        .padding(0.dp, 10.dp)
                ) {
                    Text(
                        text = "Last Set",
                        style = Typography.bodyMedium
                    )
                }
            }

            Row( // BOTTOM SECTION
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                /* Upcoming Machines Heading */
                Text(
                    text = "Upcoming Machines",
                    style = Typography.headlineSmall
                )

                /*
                See All Button
                    - routes to equipment info page (tmp)
                */
                TextButton(
                    onClick = onEquipmentInfoClicked,
                ) {
                    Text(
                        text = "See All",
                        style = Typography.bodySmall,
                        color = DarkGreen)
                }
            }

            Column( // Scrollable column to go through upcoming machines
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {

                upcomingMachines.forEach { machine ->
                    Row ( // Workout display (box)
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                            .background(LightGrey)
                            .padding(10.dp)
                    ) {
                        Column( // Workout title
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = machine,
                                style = Typography.bodyLarge
                            )

                            /*
                            Info Button
                                - routes to equipment info page
                            */
                            Button(
                                onClick = onEquipmentInfoClicked,
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                modifier = Modifier.size(30.dp),
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                Icon(
                                    imageVector = rememberInfo(),
                                    contentDescription = "info",
                                    modifier = Modifier.size(20.dp),
                                    tint = DarkGreen
                                )
                            }
                        }

                        Column( // People in queue
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "X",
                                style = Typography.bodyLarge
                            )
                            Text(
                                text = "people waiting",
                                style = Typography.bodyLarge
                            )
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
