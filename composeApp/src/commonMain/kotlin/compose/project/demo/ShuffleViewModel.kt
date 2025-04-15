package compose.project.demo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShuffleViewModel {
    private val employees = listOf(
        "Mubashir Murtaza","Zohaib Islam","Umer Siddique",
        "Abdullah Shah","Muhammad Muzammil","Usama Javaid",
        "Waleed Shoukat","Muhammad Ammad","Haris Arshad",
        "Muhammad Jawad","Malaika Akhtar","Muhammad Zeeshan",
        "Zain Shakoor","Iqra Maqbool","Hira Ahmed","Irshad Khan",
        "Hanzala Arif","Abdullah Zahid"
    )

    private val _allEmployees = MutableStateFlow(employees)

    private val _officeA = MutableStateFlow<List<String>>(emptyList())
    val officeA = _officeA.asStateFlow()

    private val _officeB = MutableStateFlow<List<String>>(emptyList())
    val officeB = _officeB.asStateFlow()

    fun shuffleEmployees(){
        val currentEmployees = _allEmployees.value
        if (currentEmployees.size < 6){
            _officeA.value = emptyList()
            _officeB.value = currentEmployees
            return
        }

        val shuffled = currentEmployees.shuffled()
        val officeASlice = shuffled.take(5)
        val officeBSlice = shuffled.drop(5)

        _officeA.value = officeASlice
        _officeB.value = officeBSlice
    }
}