package luke.koz.pomidor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PomViewModel : ViewModel(){

    val timeMinutes = MutableStateFlow(25)
    val timeSeconds = MutableStateFlow(0)

    fun timeSetter(type: String, value: Int) {
        when(type) {
            "timeMinutes" -> {
                timeMinutes.value = value
                _timeRemaining.value = calculateInitialTime()
            }
            "timeSeconds" -> {
                timeSeconds.value = value
                _timeRemaining.value = calculateInitialTime()
            }
        }
    }

    private val _timeRemaining = MutableStateFlow(calculateInitialTime())
    val timeRemaining: StateFlow<Long> = _timeRemaining

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _timerCompleted = MutableSharedFlow<Unit>()
    val timerCompleted: SharedFlow<Unit> = _timerCompleted.asSharedFlow()

    private var timerJob: Job? = null

    private fun calculateInitialTime(): Long {
        return (timeMinutes.value * 60 + timeSeconds.value) * 1000L
    }

    fun startTimer() {
        if (_isRunning.value) return

        _isRunning.value = true
        if (_timeRemaining.value <= 0) {
            _timeRemaining.value = (timeMinutes.value * 60 + timeSeconds.value) * 1000L
        }
        timerJob = viewModelScope.launch {
            while (_timeRemaining.value > 0 && _isRunning.value) {
                delay(1000L)
                if(_isRunning.value) {
                    _timeRemaining.value -= 1000L
                }
                if (_timeRemaining.value <= 0) {
                    _isRunning.value = false
                    _timerCompleted.emit(Unit)
                }
            }
            _isRunning.value = false
        }
    }
    fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }
    fun resetTimer() {
        _timeRemaining.value = calculateInitialTime()
        _isRunning.value = false
        timerJob?.cancel()
    }
}

class PomViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PomViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}