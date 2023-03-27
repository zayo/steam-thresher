package cz.liftago.feature.dfault

import androidx.compose.runtime.Composable
import cz.liftago.ui.components.DemoLayout

@Composable
internal fun DefaultCompose(
    viewModel: AlmostViewModel
) {
    DemoLayout(
        options = viewModel.state,
        notify = viewModel::notify,
        navigate = viewModel::navigate,
        finish = viewModel::finishFragment
    )
}