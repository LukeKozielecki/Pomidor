package luke.koz.pomidor.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.pomidor.ui.theme.PomidorTheme
import luke.koz.pomidor.ui.utils.pom_screen.ControlButtons
import luke.koz.pomidor.ui.utils.pom_screen.GraphicIndicators
import luke.koz.pomidor.ui.utils.pom_screen.TimeDisplay
import luke.koz.pomidor.ui.utils.pom_screen.TimeSetterComponent
import luke.koz.pomidor.ui.utils.pom_screen.hideKeyboardAndFocus
import luke.koz.pomidor.ui.utils.pom_screen.rememberKeyboardFocus
import luke.koz.pomidor.ui.utils.pom_screen.rememberPermissionLauncher
import luke.koz.pomidor.ui.utils.pom_screen.rememberPomState
import luke.koz.pomidor.viewmodel.PomViewModel

@Composable
fun PomPomScreen(modifier: Modifier = Modifier, viewModel: PomViewModel) {
    val context = LocalContext.current

    //State handling
    val (timeRemaining, isRunning, timeMinutes, timeSeconds, timeInitial, wasStarted) = rememberPomState(viewModel)

    //Permission handling
    val permissionLauncher = rememberPermissionLauncher(viewModel)

    //Keyboard and focus handling
    val (keyboardController, focusManager) = rememberKeyboardFocus()

    LaunchedEffect(Unit) {
        viewModel.showNotification(context)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null    // this gets rid of the ripple effect
            ) {
                hideKeyboardAndFocus(keyboardController, focusManager)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GraphicIndicators(
            isRunning = wasStarted,
            timeRemaining = timeRemaining.toFloat(),
            timeInitial = timeInitial.toFloat()
        )

        TimeDisplay(timeRemaining = timeRemaining)

        ControlButtons(
            viewModel = viewModel,
            permissionLauncher = permissionLauncher,
            context = context,
            isRunning = isRunning,
            onResetClick = {
                viewModel.resetTimer()
                hideKeyboardAndFocus(keyboardController, focusManager)
            }
        )

        TimeSetterComponent(viewModel, timeMinutes, timeSeconds)
    }
}

@Preview(showBackground = true)
@Composable
fun PomPomScreenPreview() {
    PomidorTheme {
        PomPomScreen(viewModel = PomViewModel())
    }
}