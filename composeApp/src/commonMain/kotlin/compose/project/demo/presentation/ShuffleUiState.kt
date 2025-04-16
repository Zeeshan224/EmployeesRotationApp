package compose.project.demo.presentation

import compose.project.demo.data.model.Employee

data class ShuffleUiState(
    val employees: List<Employee> = emptyList(),
    val officeA: List<Employee> = emptyList(),
    val officeB: List<Employee> = emptyList(),
    val isShuffling: Boolean = false
)