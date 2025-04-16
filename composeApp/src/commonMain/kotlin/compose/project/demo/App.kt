package compose.project.demo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import compose.project.demo.data.repo.EmployeeRepository
import compose.project.demo.presentation.ShuffleViewModel
import compose.project.demo.presentation.ShuffleScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = remember { ShuffleViewModel() }

        ShuffleScreen(
            viewModel = viewModel
        )
    }
}