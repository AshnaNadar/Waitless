package org.example.userinterface.Equipment
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.example.controller.UserController
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.DarkGrey
import org.example.theme.GreyText
import org.example.theme.LightGrey
import org.example.theme.Typography
import org.example.userinterface.Home.rememberInfo
import org.example.userinterface.Login.rememberClose
import org.example.userinterface.Saved.rememberAdd
import org.example.viewmodel.UserViewModel


// checkmark/done button: https://www.composables.com/icons
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

// remove button: https://www.composables.com/icons
@Composable
fun rememberRemove(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "remove",
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
                moveTo(9.833f, 21.292f)
                quadToRelative(-0.541f, 0f, -0.916f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.959f)
                quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                reflectiveQuadToRelative(0.916f, -0.375f)
                horizontalLineToRelative(20.334f)
                quadToRelative(0.541f, 0f, 0.916f, 0.395f)
                quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                reflectiveQuadToRelative(-0.916f, 0.375f)
                close()
            }
        }.build()
    }
}

@Composable
fun EquipmentView(
    userViewModel: UserViewModel,
    userController: UserController,
    onEquipmentClicked: () -> Unit = {},
    onDoneSelectingClicked: () -> Unit = {}
) {
    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }
    var showNameWorkoutDialog by remember { mutableStateOf(false)}
    val showDialog = remember { mutableStateOf(false) }

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

                if (viewModel.creatingWorkout.value || viewModel.editingWorkout.value) { // if creating/editing workout

                    Spacer(modifier = Modifier.weight(1f))

                    /*
                    Checkmark/Done Button
                        - prompts user to name workout if creating new workout
                        - goes back to saved workout/home page
                    */
                    Button(
                        onClick = {
                            if (viewModel.creatingWorkout.value) {
                                if (viewModel.noMachinesAdded()) {
                                    viewModel.removeWorkout()
                                    onDoneSelectingClicked()
                                } else {
                                    showDialog.value = true
                                }
                            } else { // editing state, apply changes to selectedWorkout
                                viewModel.editWorkout()
                                onDoneSelectingClicked()
                            } },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier.size(30.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = rememberDone(),
                            contentDescription = "done",
                            modifier = Modifier.size(30.dp),
                            tint = DarkGrey
                        )
                    }

                    if (showDialog.value) {
                        NameWorkoutDialog(
                            onDismissRequest = { // cancel (don't add new workout)
                                viewModel.removeWorkout()
                                onDoneSelectingClicked()
                                showDialog.value = false },
                            onConfirmation = {
                                viewModel.addWorkout(it)
                                onDoneSelectingClicked()
                                showDialog.value = false },
                            workoutName = "",
                            setShowDialog = { showDialog.value = it }
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


                        if (viewModel.creatingWorkout.value || viewModel.editingWorkout.value) { // if creating/editing workout
                            /*
                            Add button
                                - adds machine to last Workout in savedWorkouts
                            */
                            var machineAdded by remember { mutableStateOf(
                                viewModel.selectedWorkout.value.machines.contains(machineName)) }
                            
                            IconButton(
                                onClick = {
                                    if (!machineAdded) {
                                        viewModel.addMachine(machineName)
                                    } else {
                                        viewModel.removeMachine(machineName)
                                    }
                                    machineAdded = !machineAdded },
                                colors = if (!machineAdded) {
                                    IconButtonDefaults.iconButtonColors(DarkGreen)
                                } else {
                                    IconButtonDefaults.iconButtonColors(DarkGrey)
                                },
                                modifier = Modifier
                                    .shadow(5.dp, CircleShape)
                                    .size(40.dp),
                            ) {
                                Image(
                                    imageVector = if (!machineAdded) {
                                        rememberAdd()
                                    } else {
                                        rememberRemove() },
                                    contentDescription = if (!machineAdded) {
                                        "add machine"
                                    } else {
                                        "remove machine" },
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier
                                        .size(30.dp))
                            }

                            Spacer(modifier = Modifier.width(10.dp))
                        }

                        Row( // machine display (box)
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {

                                }
                                .fillMaxWidth()
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
                                Info Button
                                    - routes to equipment info page
                                */
                                Button(
                                    onClick = {
                                        viewModel.selectMachine(machineName)
                                        onEquipmentClicked() },
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

                            Spacer(modifier = Modifier.weight(1f))
                            Column( // Number of people in queue
                                horizontalAlignment = Alignment.End,
                            ) {
                                Text(
                                    text = viewModel.machineWaitTimes.value[machineName].toString(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameWorkoutDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    setShowDialog: (Boolean) -> Unit,
    workoutName: String
) {
    val textField = remember { mutableStateOf(workoutName) }
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
                    Text(text = "Name Your Workout")

                    Spacer(modifier = Modifier.weight(1f))

                    /*
                    Close Button
                        - deletes workout being created
                        - goes back to prev page (home or saved workouts)
                    */
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
                        text = "Enter workout name",
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