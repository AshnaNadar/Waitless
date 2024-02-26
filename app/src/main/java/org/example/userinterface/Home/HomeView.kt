package org.example.userinterface.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.theme.Pink40
import org.example.theme.Pink80
import org.example.theme.Purple40
import org.example.theme.PurpleGrey40
import org.example.theme.PurpleGrey80
import org.example.theme.Typography
import org.example.userinterface.Equipment.EquipmentView

@Preview
@Composable
fun HomeView(
    onInfoClicked: () -> Unit = {},
    onSeeAllClicked: () -> Unit = {},
    onEditWorkoutClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGrey80)
            .padding(top = 80.dp, start = 20.dp, end = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Today's Workout",
                    style = Typography.headlineMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onInfoClicked,
                    colors = ButtonDefaults.buttonColors(Pink40)
                ) {
                    Text(
                        text = "info",
                        style = Typography.bodySmall,
                        color = Color.Black)
                }
            }

            Text(
                text = "No active workout - start one now!",
                style = Typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onInfoClicked,
                colors = ButtonDefaults.buttonColors(PurpleGrey40)
            ) {
                Text(text = "Add Machines", style = Typography.bodyMedium)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Your Workouts",
                    style = Typography.headlineSmall
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onSeeAllClicked,
                    colors = ButtonDefaults.buttonColors(Pink40)
                ) {
                    Text(
                        text = "See All",
                        style = Typography.bodySmall,
                        color = Color.Black)
                }
            }

            // scrollable column to go through saved workouts
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                        .background(Pink40)
                        .padding(10.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Sample Workout",
                            style = Typography.bodyLarge
                        )
                        Button(
                            onClick = onEditWorkoutClicked,
                            colors = ButtonDefaults.buttonColors(PurpleGrey40)
                        ) {
                            Text(text = "edit", style = Typography.bodySmall)
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "X",
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
    }
}
