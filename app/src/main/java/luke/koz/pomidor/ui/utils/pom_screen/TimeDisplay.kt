package luke.koz.pomidor.ui.utils.pom_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun TimeDisplay(timeRemaining: Long) {
    val formattedTime = remember(timeRemaining) { formatTime(timeRemaining) }
    Text(
        text = formattedTime,
        fontSize = 20.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

private fun formatTime(timeRemaining: Long): String {
    val minutes = (timeRemaining / 1000) / 60
    val seconds = (timeRemaining / 1000) % 60
    return String.format(Locale.GERMANY, "%02d:%02d", minutes, seconds)
}