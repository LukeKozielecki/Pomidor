package luke.koz.pomidor.ui.utils.pom_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import luke.koz.pomidor.model.PomState
import luke.koz.pomidor.viewmodel.PomViewModel


@Composable
fun rememberPomState(viewModel: PomViewModel): PomState {
    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val timeMinutes by viewModel.timeMinutes.collectAsState()
    val timeSeconds by viewModel.timeSeconds.collectAsState()
    val timeInitial by viewModel.timeInitial.collectAsState()
    val wasStarted by viewModel.wasStarted.collectAsState()

    return remember(timeRemaining, isRunning, timeMinutes, timeSeconds, timeInitial, wasStarted) {
        PomState(timeRemaining, isRunning, timeMinutes, timeSeconds, timeInitial, wasStarted)
    }
}
