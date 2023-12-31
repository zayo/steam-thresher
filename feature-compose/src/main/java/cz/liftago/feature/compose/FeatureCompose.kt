package cz.liftago.feature.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import cz.liftago.core.navigation.actions.FeatureComposeAction
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction1
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction2
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction3
import cz.liftago.feature.compose.viewmodel.FeatureComposeViewModel

@Composable
internal fun FeatureCompose(
    viewModel: FeatureComposeViewModel,
    backQueue: ArrayDeque<NavBackStackEntry>,
    currentEntry: NavBackStackEntry
) {
    val currentEntryPosition = backQueue.lastIndexOf(currentEntry)

    fun String.normalize() = replace("/.*$".toRegex(), "")
    fun origin() = backQueue.last().destination.route?.normalize() ?: "unknown"
    Column {
        NavButton(text = "FeatureComposeAction(root)") {
            viewModel.navigate(FeatureComposeAction)
        }
        NavButton(text = "FeatureComposeInnerAction1(first)") {
            viewModel.navigate(FeatureComposeInnerAction1(origin()))
        }
        NavButton(text = "FeatureComposeInnerAction2(second)") {
            viewModel.navigate(FeatureComposeInnerAction2(origin()))
        }
        NavButton(text = "FeatureComposeInnerAction3(third)") {
            viewModel.navigate(FeatureComposeInnerAction3(origin()))
        }

        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = "Back Stack",
            style = MaterialTheme.typography.headlineSmall
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            tonalElevation = 2.dp,
        ) {
            LazyColumn {
                itemsIndexed(backQueue) { index, item ->
                    if (index == currentEntryPosition) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color(0x548BC34A))
                        ) {
                            Text(text = item.destination.route ?: "<EndOfStack>")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = viewModel.identity)
                        }
                    } else {
                        Text(text = item.destination.route ?: "<EndOfStack>")
                    }
                }
            }
        }
    }
}

@Composable
private fun NavButton(text: String, onClick: () -> Unit) {
    FilledTonalButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Text(text = text)
    }
}