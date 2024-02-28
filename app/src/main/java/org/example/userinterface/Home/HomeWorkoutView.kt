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
fun HomeWorkoutView(
    onLastSetClicked: () -> Unit = {},
    onEquipmentInfoClicked: () -> Unit = {}
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
                    text = "Current Machine",
                    style = Typography.headlineMedium
                )
            }

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
                        text = "Sample Machine",
                        style = Typography.bodyLarge
                    )
                    Button(
                        onClick = onEquipmentInfoClicked,
                        colors = ButtonDefaults.buttonColors(PurpleGrey40)
                    ) {
                        Text(text = "info", style = Typography.bodySmall)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "X:XX",
                        style = Typography.bodyLarge
                    )
                    Text(
                        text = "minutes elapsed",
                        style = Typography.bodyLarge
                    )
                }
            }

            Button(
                onClick = onLastSetClicked,
                colors = ButtonDefaults.buttonColors(PurpleGrey40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(text = "Last Set", style = Typography.bodyMedium)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Upcoming Machines",
                    style = Typography.headlineSmall
                )
            }

            // scrollable column to go through upcoming machines
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
                            text = "Sample Machine",
                            style = Typography.bodyLarge
                        )
                        Button(
                            onClick = onEquipmentInfoClicked,
                            colors = ButtonDefaults.buttonColors(PurpleGrey40)
                        ) {
                            Text(text = "info", style = Typography.bodySmall)
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
                            text = "people waiting",
                            style = Typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
