package luke.koz.pomidor.ui.utils.pom_screen

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import luke.koz.pomidor.viewmodel.PomViewModel


@Composable
fun rememberPermissionLauncher(viewModel: PomViewModel): ManagedActivityResultLauncher<String, Boolean> {
    return rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            viewModel.startTimer()
        } else {
            // Handle permission denial if needed
        }
    }
}


