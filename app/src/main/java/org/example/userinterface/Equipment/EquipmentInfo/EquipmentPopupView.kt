package org.example.userinterface.Equipment.EquipmentInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun EquipmentInfoView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
            .padding(top = 80.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "Equipment Info",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
