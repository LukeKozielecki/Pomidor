package luke.koz.pomidor.ui.utils.pom_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.pomidor.ui.theme.PomidorTheme

@Composable
fun CircularIndicator(isRunning: Boolean, timeRemaining: Float, timeInitial: Float) {
    val currentProgress = (timeInitial - timeRemaining) / timeInitial

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isRunning) {
            CircularProgressIndicator(
                progress = { currentProgress.coerceIn(0f, 1f) },
                modifier = Modifier.size(150.dp),
                trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            )
        }
    }
}

@Preview
@Composable
private fun CircularIndicatorPreview() {
    PomidorTheme {
        CircularIndicator(true, 8f,10f)
    }
}