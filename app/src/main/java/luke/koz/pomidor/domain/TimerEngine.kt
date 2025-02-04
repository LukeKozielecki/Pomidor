package luke.koz.pomidor.domain

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import luke.koz.pomidor.utils.NotificationUtils

class TimerEngine(
    private val timeConfig: TimeConfiguration,
    private val coroutineScope: CoroutineScope
) {
    private val _isRunning = MutableStateFlow(false)
    private val _timerCompleted = MutableSharedFlow<Unit>()
    private val _wasStarted = MutableStateFlow(false)

    val isRunning: StateFlow<Boolean> = _isRunning
    private val timerCompleted: SharedFlow<Unit> = _timerCompleted.asSharedFlow()
    val wasStarted: StateFlow<Boolean> = _wasStarted

    private var timerJob: Job? = null

    fun start() {
        if (_isRunning.value) return

        _isRunning.value = true
        _wasStarted.value = true
        if (timeConfig.timeRemaining.value <= 0) {
            timeConfig.resetTime()
        }

        timerJob = coroutineScope.launch {
            while (timeConfig.timeRemaining.value > 0 && _isRunning.value) {
                delay(1000L)
                if (_isRunning.value) {
                    timeConfig.decrementRemainingTime()
                    checkCompletion()
                }
            }
        }
    }

    fun pause() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    fun reset() {
        timeConfig.resetTime()
        _isRunning.value = false
        _wasStarted.value = false
        timerJob?.cancel()
    }

    private fun checkCompletion() {
        if (timeConfig.timeRemaining.value <= 0) {
            _isRunning.value = false
            _wasStarted.value = false
            coroutineScope.launch {
                _timerCompleted.emit(Unit)
            }
        }
    }

    suspend fun showNotification(context: Context) {
        timerCompleted.collect {
            NotificationUtils.showNotification(
                context,
                "Timer Complete",
                "Congrats, Pomodoro session has ended!"
            )
        }
    }
}