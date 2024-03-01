package org.example.userinterface.Saved
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.theme.Background
import org.example.theme.DarkGreen
import org.example.theme.*
import org.example.theme.LightGrey
import org.example.theme.Typography

@Composable
fun SavedView(
    onEditWorkoutClicked: () -> Unit = {}
) {
    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(top = 80.dp, start = 20.dp, end = 20.dp)
        ) {
            /* Page Title */
            Text(
                text = "Saved Workouts",
                style = Typography.headlineMedium,
            )

            Column( // Scrollable column to go through saved workouts
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, false)
            ) {

                for (i in 1..7) { // TMP: to fill screen
                    Row( // Workout display (box)
                        verticalAlignment = Alignment.CenterVertically,
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
                                text = "Sample Workout",
                                style = Typography.bodyLarge
                            )
                            Button(
                                onClick = onEditWorkoutClicked,
                                colors = ButtonDefaults.buttonColors(LightGreen)
                            ) {
                                Text(
                                    text = "Edit",
                                    style = Typography.bodySmall,
                                    color = DarkGreen
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Column( // Number of machines
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

