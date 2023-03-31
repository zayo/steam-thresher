package cz.liftago.feature.dfault

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.liftago.core.demo.ActionsEnumerator
import cz.liftago.core.demo.Notificator
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.actions.DefaultAction
import cz.liftago.core.navigation.actions.FeatureComposeAction
import cz.liftago.core.navigation.blocks.NavHostComposeFragment
import cz.liftago.ui.components.Toolbar
import cz.liftago.ui.strings.R
import cz.liftago.ui.theme.withThemedContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@AndroidEntryPoint
class FeatureDefaultFragment : NavHostComposeFragment() {

    @Inject
    lateinit var enumerator: ActionsEnumerator

    @Inject
    lateinit var notificator: Notificator

    override fun ComposeView.onCreateCompose(actions: StateFlow<Action<Args>>) {
        withThemedContent {
            val action by actions.collectAsState()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Toolbar(title = stringResource(id = action.title()), withBack = false) {}

                    val viewModel = createViewModel(action)

                    when (action) {
                        is DefaultAction -> DefaultCompose(viewModel)

                        else -> action.unknownAction()
                    }
                }
            }
        }
    }

    private fun createViewModel(action: Action<Args>): AlmostViewModel =
        AlmostViewModel(action, enumerator, fragmentNavigator, notificator)

    @StringRes
    private fun Action<Args>.title() = when (this) {
        is DefaultAction -> R.string.title_default
        is FeatureComposeAction -> R.string.title_feature_compose
        else -> unknownAction()
    }
}