package org.example.userinterface

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.controller.UserController
import org.example.viewmodel.UserViewModel

// Timeout constants in seconds

//const val LAST_SET_COUNTDOWN = 2 * 60 // time allocated for last set
//const val FIRST_WARNING = 10 * 60 // time for first timeout popup display after current machine has started
//const val MOVE_TO_LAST_SET = 1 * 60 // time for auto kicking the user to last set countdown
//const val AUTO_DISMISS_POPUP_DELAY = 15 // time after which popups are auto dismissed
var timeRemainingForLastSet = mutableIntStateOf(0)
var timeElapsedForMachine = mutableIntStateOf(0)

// uncomment below values only for testing!!
const val LAST_SET_COUNTDOWN = 10 // time allocated for last set
const val FIRST_WARNING = 1 * 60 // time for first timeout popup display after current machine has started
const val MOVE_TO_LAST_SET = 2 * 60 // time for auto kicking the user to last set countdown
const val AUTO_DISMISS_POPUP_DELAY = 15 // time after which popups are auto dismissed

@Composable
fun LastSetCountdownTimer(viewModel: UserViewModel, controller: UserController) {
    LaunchedEffect(viewModel.lastSet.value) {
        while (true) {
            val timeElapsedSeconds = (System.currentTimeMillis() - viewModel.lastSetStartTime.longValue) / 1000
            timeRemainingForLastSet.intValue = LAST_SET_COUNTDOWN - timeElapsedSeconds.toInt()
            delay(1000)
            if (timeRemainingForLastSet.intValue <= 0 && viewModel.lastSet.value) {
                controller.moveToNextMachine()
            }
        }
    }
}

@Composable
fun OngoingWorkoutTimer(viewModel: UserViewModel, controller: UserController, navToHome: () -> Unit = {}) {
    val showFirstWarning = remember { mutableStateOf(false) }
    val showFinalWarning = remember { mutableStateOf(false) }
    val finalWarningInitiated = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        while (true) {
            val timeElapsedSeconds = (System.currentTimeMillis() - viewModel.machineStartTime.longValue) / 1000
            timeElapsedForMachine.intValue = timeElapsedSeconds.toInt()
            delay(1000)

            // Display popup after 10 minutes
            if (timeElapsedForMachine.intValue == FIRST_WARNING && viewModel.workoutOngoing.value && !viewModel.lastSet.value) {
                showFirstWarning.value = true

                // Auto-dismiss the popup after 15 seconds
                launch {
                    delay(AUTO_DISMISS_POPUP_DELAY.toLong() * 1000)
                    showFirstWarning.value = false
                }
            }

            // Automatically move to last set after 15 minutes
            if (timeElapsedForMachine.intValue == MOVE_TO_LAST_SET && viewModel.workoutOngoing.value && !viewModel.lastSet.value) {
                controller.lastSet()
            }
        }
    }

    LaunchedEffect(finalWarningInitiated.value) {
        delay(60 * 1000)
        if (finalWarningInitiated.value) {
            showFinalWarning.value = true
            finalWarningInitiated.value = false
            // Auto-dismiss the popup after 15 seconds
            launch {
                delay(AUTO_DISMISS_POPUP_DELAY.toLong() * 1000)
                showFinalWarning.value = false
            }
        }
    }

    if (showFirstWarning.value and !viewModel.waiting.value) {
        TimeoutPopup(
            onDismiss = { showFirstWarning.value = false },
            onOneMoreMinute = {
                showFirstWarning.value = false
                finalWarningInitiated.value = true
            },
            true,
            controller = controller,
            viewModel = viewModel
        )
    }

    if (showFinalWarning.value and !viewModel.waiting.value) {
        TimeoutPopup(
            onDismiss = { showFinalWarning.value = false },
            onOneMoreMinute = {},
            false,
            controller = controller,
            viewModel = viewModel
        )
    }

//    if (viewModel.showWorkoutSummary.value and !viewModel.workoutOngoing.value) {
//        WorkoutSummaryPopup(
//            onDismiss = { viewModel.showWorkoutSummary.value = false },
//            controller = controller,
//            viewModel = viewModel,
//            navToHome
//        )
//    }
}

@Composable
fun TimeoutPopup(onDismiss: () -> Unit, onOneMoreMinute: () -> Unit, isFirstWarning: Boolean, controller: UserController, viewModel: UserViewModel) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Are you still there?") },
        text = { Text(text = "You've been using the ${viewModel.currentMachine.value} for a while now.") },
        confirmButton = {
            if (isFirstWarning) {
                Button(
                    onClick = onOneMoreMinute
                ) {
                    Text(text = "1 more minute")
                }
            } else {
                Button(
                    onClick = {
                        onDismiss()
                        controller.endWorkout()
                    }
                ) {
                    Text(text = "End Workout")
                }
            }
            Button(
                onClick = {
                    onDismiss()
                    controller.lastSet()
                }
            ) {
                Text(text = "Last Set")
            }
        }
    )
}

@Composable
fun WorkoutSummaryPopup(onDismiss: () -> Unit, controller: UserController, viewModel: UserViewModel, navToHome: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Workout Summary") },
        text = {
            val timeElapsed = (System.currentTimeMillis() - viewModel.workoutStartTime.longValue) / 1000
            Column {
                Text(
                    text = "Total Time Elapsed: ${timeElapsed / 60}:${String.format("%02d", timeElapsed % 60)}\n",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Machines Used: ",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
//                val currentWorkoutID = viewModel.selectedWorkout.value.id
//                Text(
//                    text = "${
//                        viewModel.savedWorkouts.value.find { it.id == currentWorkoutID }
//                            ?.machines
//                            ?.joinToString(separator = "\n")
//                    }"
//                )
                Text(
                    text = viewModel.machinesCompleted.value
                        .joinToString(separator = "\n")
                )

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    navToHome()
                    onDismiss()
                }
            ) {
                Text(text = "End Workout")
            }
        }
    )
}

