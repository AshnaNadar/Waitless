package org.example.userinterface.Equipment.EquipmentInfo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import org.example.R
import org.example.controller.UserController
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.DarkGrey
import org.example.theme.GreyText
import org.example.theme.LightGreen
import org.example.theme.LightGrey
import org.example.theme.Typography
import org.example.userinterface.Login.rememberClose
import org.example.viewmodel.UserViewModel

@Composable
fun EquipmentInfoView(
    userViewModel: UserViewModel,
    userController: UserController,
    tabs: List<String> = listOf<String>("Information", "Form"),
    onClick: (index: Int) -> Unit = {}
) {
    val viewModel by remember { mutableStateOf(userViewModel) }
    val controller by remember { mutableStateOf(userController) }
    val cornerRadius = 10.dp
    val (selectedIndex, onIndexSelected) = remember { mutableStateOf<Int?>(0) } // initially on Info tab

    Box()
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(top = 80.dp, start = 20.dp, end = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(0.dp, 5.dp)
            ) {
                tabs.forEachIndexed { index, tabName ->
                    OutlinedButton(
                        modifier = when (index) {
                            0 ->
                                Modifier
                                    .offset(0.dp, 0.dp)
                                    .zIndex(if (selectedIndex == index) 1f else 0f)
                                    .fillMaxHeight()
                                    .fillMaxWidth(fraction = 0.5f)

                            else ->
                                Modifier
                                    .offset((-1 * index).dp, 0.dp)
                                    .zIndex(if (selectedIndex == index) 1f else 0f)
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                        },
                        onClick = {
                            onIndexSelected(index)
                            onClick(index)
                        },
                        shape = when (index) {
                            // left button
                            0 -> RoundedCornerShape(
                                topStart = cornerRadius,
                                topEnd = 0.dp,
                                bottomStart = cornerRadius,
                                bottomEnd = 0.dp
                            )
                            // right button
                            else -> RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = cornerRadius,
                                bottomStart = 0.dp,
                                bottomEnd = cornerRadius
                            )
                        },
                        border = BorderStroke( 2.dp, DarkGreen ),
                        colors = if (selectedIndex == index) {
                            // selected colors
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = DarkGreen,
                                contentColor = DarkGreen
                            )
                        } else {
                            // not selected colors
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = Background,
                                contentColor = Background
                            )
                        },
                    ) {
                        Text(
                            text = tabName,
                            color = if (selectedIndex == index) {
                                Background
                            } else {
                                DarkGreen
                            },
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            }

            // Info tab: display image, target muscle group, number of machines
            if (selectedIndex == 0) {
                Image(
                        modifier = Modifier
                            .height(300.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = viewModel.selectedMachine.value.visual), /* EDIT ME: retrieve from server */
                        contentDescription = stringResource(id = R.string.machine_image)
                )

                Row( // Target Muscle Group
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(LightGrey)
                        .padding(10.dp, 0.dp)
                ) {
                    Text(
                        text = "Target Muscle Group",
                        color = DarkGrey,
                        style = Typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                        text = viewModel.selectedMachine.value.targetMuscleGroup, /* EDIT ME: retrieve from server */
                        textAlign = TextAlign.End,
                        color = Color.Gray,
                        style = Typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row( // Number of Machines
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(LightGrey)
                        .padding(10.dp, 0.dp)
                ) {
                    Text(
                        text = "Number of Machines",
                        color = DarkGrey,
                        style = Typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = viewModel.selectedMachine.value.numberOfAvailableMachines.toString(), /* EDIT ME: retrieve from server */
                        color = Color.Gray,
                        style = Typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row( // Machine Status
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(LightGrey)
                        .padding(10.dp, 0.dp)
                ) {
                    Text(
                        text = "Status",
                        color = DarkGrey,
                        style = Typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = if (viewModel.selectedMachine.value.workingStatus) {
                                   "In Service"
                               } else {
                                   "Out of Order"
                               }, /* EDIT ME: retrieve from server */
                        color = Color.Gray,
                        style = Typography.bodyLarge
                    )
                }

            } else { // Form tab: display image, text description of form
                Image(
                    modifier = Modifier
                        .height(300.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = viewModel.selectedMachine.value.formVisual), /* EDIT ME: retrieve from server */
                    contentDescription = stringResource(id = R.string.machine_image)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(LightGrey)
                        .padding(10.dp)
                ) {
                    Text( /* EDIT ME: retrieve from server */
                        text = viewModel.selectedMachine.value.formDescription, /* EDIT ME: retrieve from server */
                        color = DarkGrey,
                        style = Typography.bodyLarge
                    )
                }


            }
        }
    }
}
