package luke.koz.pomidor.ui.utils.pom_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController


@Composable
fun rememberKeyboardFocus(): Pair<SoftwareKeyboardController?, FocusManager> {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    return keyboardController to focusManager
}

fun hideKeyboardAndFocus(keyboardController: SoftwareKeyboardController?, focusManager: FocusManager) {
    keyboardController?.hide()
    focusManager.clearFocus(true)
}