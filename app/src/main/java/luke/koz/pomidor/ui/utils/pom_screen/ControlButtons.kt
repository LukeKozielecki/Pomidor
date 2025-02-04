package luke.koz.pomidor.ui.utils.pom_screen

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import luke.koz.pomidor.ui.theme.PomidorTheme
import luke.koz.pomidor.utils.PermissionUtils
import luke.koz.pomidor.viewmodel.PomViewModel


@Composable
fun ControlButtons(
    viewModel: PomViewModel,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    context: Context,
    isRunning: Boolean,
    onResetClick: () -> Unit
) {
    Button(onClick = { handleStartPauseClick(viewModel, permissionLauncher, context, isRunning) }) {
        Text(if (isRunning) "Pause" else "Start")
    }
    Button(onClick = onResetClick) {
        Text(text = "Reset")
    }
}

@Preview
@Composable
private fun ControlButtonsPreview() {
    val viewModel = PomViewModel()
    PomidorTheme {
        ControlButtons(viewModel ,rememberPermissionLauncher(viewModel), LocalContext.current,true,{})
    }
}

private fun handleStartPauseClick(
    viewModel: PomViewModel,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    context: Context,
    isRunning: Boolean
) {
    viewModel.recalculateInitialTime()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (PermissionUtils.isNotificationPermissionGranted(context)) {
            toggleTimer(viewModel, isRunning)
        } else {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    } else {
        toggleTimer(viewModel, isRunning)
    }
}

private fun toggleTimer(viewModel: PomViewModel, isRunning: Boolean) {
    if (isRunning) viewModel.pauseTimer() else viewModel.startTimer()
}