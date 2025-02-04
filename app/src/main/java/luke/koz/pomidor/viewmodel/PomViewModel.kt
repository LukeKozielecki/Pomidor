package luke.koz.pomidor.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import luke.koz.pomidor.domain.TimeConfiguration
import luke.koz.pomidor.domain.TimerEngine

enum class TimeType {
    MINUTES,
    SECONDS
}

class PomViewModel : ViewModel() {
    private val timeConfig = TimeConfiguration()
    private val timerEngine = TimerEngine(timeConfig, viewModelScope)

    val timeInitial : StateFlow<Long> = timeConfig.timeInitial
    val timeMinutes = timeConfig.timeMinutes
    val timeSeconds = timeConfig.timeSeconds
    val timeRemaining = timeConfig.timeRemaining
    val isRunning = timerEngine.isRunning
    val wasStarted = timerEngine.wasStarted

    fun recalculateInitialTime() = timeConfig.recalculateInitialTime()
    fun updateTime(type: TimeType, value: Int) {
        when (type) {
            TimeType.MINUTES -> timeConfig.updateMinutes(value)
            TimeType.SECONDS -> timeConfig.updateSeconds(value)
        }
    }

    fun startTimer() = timerEngine.start()
    fun pauseTimer() = timerEngine.pause()
    fun resetTimer() = timerEngine.reset()

    suspend fun showNotification(context: Context) = timerEngine.showNotification(context)

}

class PomViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PomViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}