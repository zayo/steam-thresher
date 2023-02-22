package cz.liftago.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.liftago.ui.theme.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownWithButton(
    actionText: String,
    options: List<String>,
    onConfirmed: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(16.dp)) {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = options[selectedOption],
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEachIndexed { index, selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier.exposedDropdownSize(),
                        onClick = {
                            selectedOption = index
                            expanded = false
                        },
                        text = { Text(text = selectionOption) }
                    )
                }
            }
        }

        FilledTonalButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onConfirmed(selectedOption) }
        ) {
            Text(text = actionText)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewTheme {
        Column(
            modifier = Modifier.height(IntrinsicSize.Max)
        ) {
            DropdownWithButton(
                actionText = "click",
                options = listOf("first"),
                onConfirmed = {}
            )
            DropdownWithButton(
                actionText = "click2",
                options = listOf("second"),
                onConfirmed = {}
            )
        }
    }
}

