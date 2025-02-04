package luke.koz.pomidor.ui.utils.pom_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.pomidor.R
import luke.koz.pomidor.ui.theme.PomidorTheme


@Composable
fun GraphicIndicators(isRunning : Boolean, timeRemaining: Float, timeInitial: Float) {
    val painter = painterResource(id = R.drawable.pompom)
    Box (contentAlignment = Alignment.Center, modifier = Modifier.defaultMinSize(150.dp)){
        CircularIndicator(
            isRunning,
            timeRemaining,
            timeInitial
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(128.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun GraphicIndicatorsPrev() {
    PomidorTheme {
        GraphicIndicators(
            isRunning = true,
            timeRemaining = 8f,
            timeInitial = 10f
        )
    }
}

