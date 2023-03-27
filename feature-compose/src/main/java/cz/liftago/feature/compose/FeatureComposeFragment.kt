package cz.liftago.feature.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.actions.FeatureComposeAction
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction1
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction2
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction3
import cz.liftago.core.navigation.blocks.NavHostComposeFragment
import cz.liftago.feature.compose.viewmodel.FeatureViewModel
import cz.liftago.feature.compose.viewmodel.RootViewModel
import cz.liftago.ui.components.Toolbar
import cz.liftago.ui.strings.R
import cz.liftago.ui.theme.withThemedContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FeatureComposeFragment : NavHostComposeFragment() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun ComposeView.onCreateCompose(actions: StateFlow<Action<Args>>) {
        withThemedContent {
            val navController = rememberNavController()

            Scaffold(
                topBar = {
                    Toolbar(title = stringResource(id = R.string.title_feature_compose)) {
                        fragmentNavigator.finishFragment()
                    }
                }
            ) { padding ->
                NavHost(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    navController = navController,
                    startDestination = "root"
                ) {
                    composable("root") { entry ->
                        val parentEntry = remember(entry) {
                            navController.getBackStackEntry("root")
                        }
                        val viewModel: RootViewModel = hiltViewModel(parentEntry)
                        FeatureCompose(viewModel, navController.backQueue, entry)
                    }

                    composable("first/{arg}") { entry ->
                        val viewModel: FeatureViewModel = hiltViewModel()
                        FeatureCompose(viewModel, navController.backQueue, entry)
                    }

                    composable("second/{arg}") { entry ->
                        val viewModel: FeatureViewModel = hiltViewModel()
                        FeatureCompose(viewModel, navController.backQueue, entry)
                    }

                    composable("third/{arg}") { entry ->
                        val viewModel: FeatureViewModel = hiltViewModel()
                        FeatureCompose(viewModel, navController.backQueue, entry)
                    }
                }
            }

            LaunchedEffect(null) {
                actions.collectLatest {
                    when (it) {
                        is FeatureComposeAction ->
                            navController.popBackStack("root", inclusive = false)

                        is FeatureComposeInnerAction1 ->
                            navController.navigate("first/${it.args.origin}")

                        is FeatureComposeInnerAction2 ->
                            navController.navigate("second/${it.args.origin}")

                        is FeatureComposeInnerAction3 ->
                            navController.navigate("third/${it.args.origin}")
                    }
                }
            }
        }
    }
}