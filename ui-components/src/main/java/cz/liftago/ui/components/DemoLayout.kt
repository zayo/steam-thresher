package cz.liftago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.liftago.ui.strings.R

@Composable
fun DemoLayout(
    options: List<String>,
    notify: (Int) -> Unit,
    navigate: (Int) -> Unit,
    finish: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DropdownWithButton(
            actionText = stringResource(id = R.string.action_notify),
            options = options,
            onConfirmed = notify,
        )
        DropdownWithButton(
            actionText = stringResource(id = R.string.action_navigate),
            options = options,
            onConfirmed = navigate,
        )
        DropdownWithButton(
            actionText = stringResource(id = R.string.action_finish),
            options = options,
            onConfirmed = finish,
        )
    }
}