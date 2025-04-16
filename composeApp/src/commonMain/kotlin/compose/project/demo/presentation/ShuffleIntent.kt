package compose.project.demo.presentation

sealed class ShuffleIntent {
    data object StartShuffle : ShuffleIntent()
    data object StopShuffle : ShuffleIntent()
    data object ShuffleNow : ShuffleIntent()

}