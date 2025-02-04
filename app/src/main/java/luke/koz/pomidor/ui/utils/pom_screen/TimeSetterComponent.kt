package luke.koz.pomidor.ui.utils.pom_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import luke.koz.pomidor.viewmodel.PomViewModel
import luke.koz.pomidor.viewmodel.TimeType


@Composable
fun TimeSetterComponent(viewModel: PomViewModel, timeMinutes : Int, timeSeconds : Int) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TimeInputField("Minutes", timeMinutes, focusManager, false, Modifier.focusRequester(focusRequester)) {
        viewModel.updateTime(TimeType.MINUTES, it)
        viewModel.pauseTimer()
    }
    TimeInputField("Seconds", timeSeconds, focusManager, true, Modifier.focusRequester(focusRequester)) {
        viewModel.updateTime(TimeType.SECONDS, it)
        viewModel.pauseTimer()
    }
}