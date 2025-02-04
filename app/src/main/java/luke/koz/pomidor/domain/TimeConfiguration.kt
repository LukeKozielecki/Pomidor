package luke.koz.pomidor.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimeConfiguration {
    private val _timeMinutes = MutableStateFlow(25)
    private val _timeSeconds = MutableStateFlow(0)
    private val _timeRemaining = MutableStateFlow(calculateSetTime())
    private val _timeInitial = MutableStateFlow(0L)

    val timeMinutes: StateFlow<Int> = _timeMinutes
    val timeSeconds: StateFlow<Int> = _timeSeconds
    val timeRemaining: StateFlow<Long> = _timeRemaining
    val timeInitial: StateFlow<Long> = _timeInitial

    fun updateMinutes(value: Int) {
        _timeMinutes.value = value
        recalculateTime()
    }

    fun updateSeconds(value: Int) {
        _timeSeconds.value = value
        recalculateTime()
    }

    fun decrementRemainingTime() {
        _timeRemaining.value -= 1000L
    }

    fun resetTime() {
        _timeRemaining.value = calculateSetTime()
    }

    private fun recalculateTime() {
        _timeRemaining.value = calculateSetTime()
    }

    fun recalculateInitialTime() {
        _timeInitial.value = calculateSetTime()
    }

    private fun calculateSetTime(): Long {
        return (_timeMinutes.value * 60L + _timeSeconds.value) * 1000L
    }
}