    package compose.project.demo.presentation

    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import compose.project.demo.data.model.Employee
    import compose.project.demo.data.repo.EmployeeRepository
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.Job
    import kotlinx.coroutines.delay
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.update
    import kotlinx.coroutines.isActive
    import kotlinx.coroutines.launch

    class ShuffleViewModel() : ViewModel() {

        private var androidEmployees by mutableStateOf<List<Employee>>(emptyList())
        private var iosEmployees by mutableStateOf<List<Employee>>(emptyList())
        private var employeeRepository = EmployeeRepository
        init {
            fetchEmployees()
        }

        private fun fetchEmployees(){
            viewModelScope.launch {
                androidEmployees = employeeRepository.androidEmployees
                iosEmployees = employeeRepository.iosEmployees
            }
        }

        private val _uiState = MutableStateFlow(
            ShuffleUiState(
                employees = androidEmployees,
                officeA = emptyList(),
                officeB = emptyList(),
                isShuffling = false
            )
        )
        val uiState: StateFlow<ShuffleUiState> = _uiState.asStateFlow()

        private var shuffleJob: Job? = null

        fun onIntent(intent: ShuffleIntent) {
            when (intent) {
                is ShuffleIntent.StartShuffle -> startShuffling()
                is ShuffleIntent.StopShuffle -> stopShuffling()
                is ShuffleIntent.ShuffleNow -> shuffleEmployees()
            }
        }

        private fun startShuffling() {
            if (_uiState.value.isShuffling) return
            _uiState.update { it.copy(isShuffling = true) }

            shuffleJob = CoroutineScope(Dispatchers.Default).launch {
                while (isActive) {
                    shuffleEmployees()
                    delay(100)
                }
            }
        }

        private fun stopShuffling() {
            shuffleJob?.cancel()
            _uiState.update { it.copy(isShuffling = false) }
        }

        private fun shuffleEmployees() {
            val shuffled = _uiState.value.employees.shuffled()
            val officeA = shuffled.take(5)
            val officeB = shuffled.drop(5)

            _uiState.update {
                it.copy(
                    officeA = officeA,
                    officeB = officeB
                )
            }
        }
    }