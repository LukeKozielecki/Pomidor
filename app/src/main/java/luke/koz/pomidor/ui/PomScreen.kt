package luke.koz.pomidor.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import luke.koz.pomidor.R
import luke.koz.pomidor.ui.theme.PomidorTheme
import luke.koz.pomidor.utils.NotificationUtils
import luke.koz.pomidor.utils.PermissionUtils
import luke.koz.pomidor.viewmodel.PomViewModel
import java.util.Locale

@Composable
fun PomPomScreen(modifier: Modifier = Modifier, viewModel: PomViewModel) {
    val image = painterResource(id = R.drawable.pompom)

    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val timeMinutes by viewModel.timeMinutes.collectAsState()
    val timeSeconds by viewModel.timeSeconds.collectAsState()
    val context = LocalContext.current

    val formattedTime = remember(timeRemaining) {
        val minutes = (timeRemaining / 1000) / 60
        val seconds = (timeRemaining / 1000) % 60
        String.format(Locale.GERMANY,"%02d:%02d", minutes, seconds)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startTimer()
        } else {
            // Handle permission denial if needed
        }
    }

    LaunchedEffect(Unit) {
        viewModel.timerCompleted.collect {
            NotificationUtils.showNotification(
                context,
                "Timer Complete",
                "Congrats, Pomodoro session has ended!"
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = formattedTime,
            fontSize = 20.sp
        )
        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (PermissionUtils.isNotificationPermissionGranted(context)) {
                    viewModel.startTimer()
                } else {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                viewModel.startTimer()
            }
            if (isRunning) viewModel.pauseTimer() else viewModel.startTimer()
        }) {
            Text(if (isRunning) "Pause" else "Start")
        }
        Button(onClick = { viewModel.resetTimer() }) {
            Text(text = "Reset")
        }

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        TimeInputField("Minutes", timeMinutes, focusManager, false, Modifier.focusRequester(focusRequester)) {
            viewModel.timeSetter("timeMinutes", it)
        }
        TimeInputField("Seconds", timeSeconds, focusManager, true, Modifier.focusRequester(focusRequester)) {
            viewModel.timeSetter("timeSeconds", it)
        }
    }
}

@Composable
fun TimeInputField(
    label: String,
    value: Int,
    focusManager : FocusManager,
    isLastFiled : Boolean,
    modifier: Modifier,
    onValueChange: (Int) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value.toString())) }
    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(value) {
        if (!isEditing) {
            textFieldValue = TextFieldValue(value.toString())
        }
    }

    TextField(
        value = textFieldValue,
        onValueChange = { newText ->
            isEditing = true
            textFieldValue = newText
            newText.text.toIntOrNull()?.takeIf { it >= 0 }?.let {
                onValueChange(it)
            }
        },
        singleLine = true,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = if (!isLastFiled) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions =  KeyboardActions( onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        modifier = modifier
            .padding(8.dp)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    textFieldValue = textFieldValue.copy(
                        selection = TextRange(0, textFieldValue.text.length)
                    )
                } else {
                    isEditing = false
                    if (textFieldValue.text.isEmpty() || textFieldValue.text.toIntOrNull() == null) {
                        onValueChange(0)
                        textFieldValue = TextFieldValue("0")
                    }
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun PomPomScreenPreview() {
    PomidorTheme {
        PomPomScreen(viewModel = PomViewModel())
    }
}