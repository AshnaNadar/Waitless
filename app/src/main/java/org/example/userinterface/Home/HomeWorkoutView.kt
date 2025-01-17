package org.example.userinterface.Home


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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.controller.UserController
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.LightGrey
import org.example.theme.Typography
import org.example.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import org.example.theme.LightGreen
import org.example.userinterface.TimeoutPopup
import org.example.userinterface.timeElapsedForMachine
import org.example.userinterface.timeRemainingForLastSet

@Composable
fun HomeWorkoutView(
    userViewModel: UserViewModel,
    userController: UserController,
    onStopWorkoutClicked: () -> Unit = {},
    onEquipmentInfoClicked: () -> Unit = {}
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
            horizontalAlignment = Alignment.Start
        ) {

            Row( // TITLE + STOP BUTTON
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                /* Page Title */
                if (viewModel.currentMachine.value == "") {
                    Text(
                        text = "Workout Summary",
                        style = TextStyle(fontWeight = FontWeight.Bold, color = DarkGreen, fontSize = 29.sp)
                    )
                } else {
                    Text(
                        text = "Current Machine",
                        style = TextStyle(fontWeight = FontWeight.Bold, color = DarkGreen, fontSize = 29.sp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (viewModel.currentMachine.value != "") {
                    /*
                End Workout Button
                    - routes back to home page
                */
                    TextButton(
                        onClick = {
                            controller.refetchQueueAPIdata()
                            controller.endWorkout()
                        },
                    ) {
                        Text(
                            text = "End Workout",
                            style = Typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            color = DarkGreen,
                        )
                    }
                }
            }

            // TOP SECTION (Current Machine)

            if (!viewModel.waiting.value && viewModel.currentMachine.value != "") {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .heightIn(120.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { // Selected workout
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
                                    text = viewModel.currentMachine.value,
                                    style = Typography.bodyLarge
                                )

                                /*
                                Info Button  // ??
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

                            if (viewModel.lastSet.value) { // Display countdown timer if in last set
                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Column( // Countdown Timer ??
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = "Last Set Countdown",
                                            style = Typography.bodyMedium,
                                            color = DarkGreen,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${timeRemainingForLastSet.intValue / 60}:${
                                                String.format(
                                                    "%02d",
                                                    timeRemainingForLastSet.intValue % 60
                                                )
                                            }",
                                            style = Typography.bodyLarge,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "mins left",
                                            style = Typography.bodyLarge
                                        )
                                    }
                                }
                            } else {
                                Column( // Time Elapsed
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = "${timeElapsedForMachine.intValue / 60}:${String.format("%02d", timeElapsedForMachine.intValue % 60)}",
                                        style = Typography.bodyLarge,
                                    )
                                    Text(
                                        text = "mins elapsed",
                                        style = Typography.bodyLarge
                                    )
                                }
                            }


                        }
                    }

                    if (viewModel.lastSet.value) { // Display countdown timer if in last set
                        Row (verticalAlignment = Alignment.CenterVertically) {

                            if (viewModel.selectedWorkout.value.machines.size > 1) { // Show Next Machine only if more than machines in workout
                                /*
                               Next Machine button
                               */
                                Button(
                                    onClick = {
                                        controller.moveToNextMachine()
                                    },
                                    colors = ButtonDefaults.buttonColors(DarkGreen),
                                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .heightIn(70.dp)
                                        .padding(0.dp, 10.dp)
                                        .padding(start = 4.dp)
                                ) {
                                    Text(
                                        text = "Next Machine",
                                        style = Typography.bodyMedium
                                    )
                                }
                            }
                        }
                    } else {
                        Row (verticalAlignment = Alignment.CenterVertically) {
                            /*
                            Last Set button
                            */
                            Button(
                                onClick = { controller.lastSet() },
                                colors = ButtonDefaults.buttonColors(DarkGreen),
                                shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .heightIn(70.dp)
                                    .padding(0.dp, 10.dp)
                                    .padding(end = 4.dp)
                            ) {
                                Text(
                                    text = "Last Set",
                                    style = Typography.bodyMedium
                                )
                            }

                            if (viewModel.selectedWorkout.value.machines.size > 1) { // Show Next Machine only if more than machines in workout
                                /*
                               Next Machine button
                               */
                                Button(
                                    onClick = {
                                        controller.moveToNextMachine()
                                    },
                                    colors = ButtonDefaults.buttonColors(DarkGreen),
                                    shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .heightIn(70.dp)
                                        .padding(0.dp, 10.dp)
                                        .padding(start = 4.dp)
                                ) {
                                    Text(
                                        text = "Next Machine",
                                        style = Typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                // BOTTOM SECTION (Upcoming machines)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                ) {
                    /* Upcoming Machines Heading */
                    Text(
                        text = "Upcoming Machines",
                        style = TextStyle(fontWeight = FontWeight.Bold, color = DarkGreen, fontSize = 29.sp)

                    )
                }

            } else if (viewModel.waiting.value) { // If waiting (in queue) for next machine
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .heightIn(120.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { // Selected workout
                        Row( // Machine display (box)
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                                .background(LightGrey)
                                .weight(1f)
                                .heightIn(80.dp)
                                .padding(12.dp)
                        ) {
                            Column( // Machine title
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "In queue for ${viewModel.currentMachine.value}",
                                    style = Typography.bodyLarge
                                )

                            }

                            /*
                            Refresh queue button  // ??
                             */
                            Button(
                                onClick = { controller.refreshQueueStatus() },
                                colors = ButtonDefaults.buttonColors(DarkGreen),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Refresh Queue",
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Refresh",
                                        style = Typography.bodyMedium,
                                        color = Color.White
                                    )
                                }
                            }



                        }
                    }
                }
                // BOTTOM SECTION (Upcoming machines)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                ) {
                    /* Upcoming Machines Heading */
                    Text(
                        text = "Upcoming Machines",
                        style = Typography.headlineSmall
                    )
                }
            } else {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 10.dp) // Add padding to the left
                        .background(color = LightGreen, shape = RoundedCornerShape(8.dp)) // Apply background color
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp) // Add padding inside the Box
                    ) {
                        val timeElapsed = (System.currentTimeMillis() - viewModel.workoutStartTime.longValue) / 1000
                        Text(
                            text = "Total Time Elapsed: ${timeElapsed / 60}:${String.format("%02d", timeElapsed % 60)} minutes\n",
                            style = TextStyle(fontWeight = FontWeight.Bold, color = DarkGreen, fontSize = 18.sp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Machines Used: ",
                            style = TextStyle(fontWeight = FontWeight.Bold, color = DarkGreen, fontSize = 18.sp),
                        )
                        Text(
                            text = viewModel.machinesCompleted.value
                                .joinToString(separator = "\n"),
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),

                            modifier = Modifier.padding(bottom = 8.dp) // Add padding to the bottom of the text
                        )
                    }
                }


                Row (verticalAlignment = Alignment.CenterVertically) {
                    /*
                  exit to home
                  */
                    Button(
                        onClick = {
                            onStopWorkoutClicked()
                        },
                        colors = ButtonDefaults.buttonColors(DarkGreen),
                        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(70.dp)
                            .padding(0.dp, 10.dp)
                            .padding(start = 4.dp)
                    ) {
                        Text(
                            text = "Exit",
                            style = Typography.bodyMedium
                        )
                    }
                }
                controller.endWorkout()
            }



            Column( // Scrollable column to go through upcoming machines
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {

                viewModel.selectedWorkout.value.machines.forEachIndexed { index, machine ->
                    if (index != 0) {
                        Row( // Workout display (box)
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
                                    style = Typography.bodyLarge,
                                )

                                Row (
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                ) {
                                    /*
                                Info Button
                                    - routes to equipment info page
                                */
                                    Button(
                                        onClick = {
                                            viewModel.selectMachine(machine)
                                            onEquipmentInfoClicked() },
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
                                    if (machine in viewModel.selectedWorkout.value.inQueue) {
                                        Box(
                                            modifier = Modifier
                                                .background(DarkGreen, RoundedCornerShape(4.dp))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Waiting in Queue",
                                                style = Typography.bodyMedium,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }

                            Column( // People in queue
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = viewModel.machineWaitTimes.value[machine].toString(),
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

//// Old stop workout button:
////                Button(
////                    onClick = onStopWorkoutClicked,
////                    shape = CircleShape,
////                    colors = ButtonDefaults.buttonColors(DarkGreen),
////                    modifier = Modifier.size(30.dp),
////                    contentPadding = PaddingValues(0.dp),
////                ) {
////                    Icon(
////                        imageVector = rememberStopCircle(),
////                        contentDescription = "stop",
////                        modifier = Modifier.size(20.dp),
////                        tint = DarkGreen
////                    )
////                }