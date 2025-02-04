package luke.koz.pomidor.ui.utils.pom_screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.pomidor.ui.theme.PomidorTheme
import luke.koz.pomidor.ui.theme.Red80
import luke.koz.pomidor.ui.theme.Red80Dark
import luke.koz.pomidor.ui.theme.RedGrey80
import luke.koz.pomidor.ui.theme.RedGrey80Dark

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
        colors = if (!isSystemInDarkTheme()){
            TextFieldDefaults.colors(
                focusedContainerColor = Red80, unfocusedContainerColor = RedGrey80
            )
        } else {
            TextFieldDefaults.colors(
                focusedContainerColor = Red80Dark, unfocusedContainerColor = RedGrey80Dark
            )
        },
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
@Preview
@Composable
private fun TimeInputFieldPreview() {
    PomidorTheme {
        TimeInputField(
            label = "Pomodoro seconds",
            value = 10,
            focusManager = LocalFocusManager.current,
            isLastFiled = false,
            modifier = Modifier,
            onValueChange = {}
        )
    }
}