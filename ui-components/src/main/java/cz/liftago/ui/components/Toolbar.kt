package cz.liftago.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(title: String, withBack: Boolean = true, onBack: () -> Unit) {
    Surface(shadowElevation = 4.dp) {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                if (withBack) {
                    BackButton(onClick = onBack)
                }
            }
        )
    }
}