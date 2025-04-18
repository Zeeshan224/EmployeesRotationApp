package compose.project.demo.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.project.demo.data.model.Employee

@Composable
fun ShuffleScreen(viewModel: ShuffleViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.fillMaxHeight(0.85f)) {
            EmployeeList("I-11", state.officeA, Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            EmployeeList("UBL Tower", state.officeB, Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.onIntent(ShuffleIntent.StartShuffle) },
                enabled = !state.isShuffling
            ) {
                Text("Start")
            }
            Button(
                onClick = { viewModel.onIntent(ShuffleIntent.StopShuffle) },
                enabled = state.isShuffling
            ) {
                Text("Stop")
            }
        }
    }
}

//    val officeA by viewModel.officeA.collectAsState()
//    val officeB by viewModel.officeB.collectAsState()
//
//    var isShuffling by remember { mutableStateOf(false) }
//
//    LaunchedEffect(isShuffling) {
//        while (isShuffling) {
//            viewModel.shuffleEmployees()
//            delay(800)
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        verticalArrangement = Arrangement.SpaceBetween,
//    ) {
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = { isShuffling = true },
//                enabled = !isShuffling
//            ) {
//                Text("Start")
//            }
//            Button(
//                onClick = { isShuffling = false },
//                enabled = isShuffling
//            ) {
//                Text("Stop")
//            }
//        }
//    }
//}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EmployeeList(title: String, employees: List<Employee>, modifier: Modifier) {
    Column(modifier) {
        Text(title, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        AnimatedContent(
            targetState = employees,
            transitionSpec = {
                fadeIn(animationSpec = tween(100)) with fadeOut(animationSpec = tween(100))
            },
            label = "Shuffling Animation"
        ) { animatedList ->
            LazyColumn {
                items(animatedList) { employee ->
                    Text(
                        text = "• ${employee.name}")
                }
            }
        }
    }
}
