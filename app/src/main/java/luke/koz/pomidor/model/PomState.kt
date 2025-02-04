package luke.koz.pomidor.model

data class PomState(
    val timeRemaining: Long,
    val isRunning: Boolean,
    val timeMinutes: Int,
    val timeSeconds: Int,
    val timeInitial: Long,
    val wasStarted: Boolean
)