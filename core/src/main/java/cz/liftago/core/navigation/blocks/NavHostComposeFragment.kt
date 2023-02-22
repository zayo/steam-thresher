package cz.liftago.core.navigation.blocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.utils.requireNavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Specialization of [NavHostFragment] for the @Composable usage.
 *
 * Example of usage:
 * ```
 * @AndroidEntryPoint
 * class FeatureComposeFragment : NavHostComposeFragment() {
 *
 * @Inject
 * lateinit var navigator: Navigator
 *
 * override fun ComposeView.onCreateCompose(actions: StateFlow<Action<Args>>) {
 *     withThemedContent {
 *         val action by actions.collectAsState()
 *         val navController = rememberNavHostController()
 *         NavHost(...) {
 *             ....
 *         }
 *
 *         LaunchEffect(null) {
 *              // Translate received actions to internal navController
 *              // Alternatively, you can skip navController & navHost and decide based on action
 *              // directly.
 *              repeatOnLifecycle(Resumed) {
 *                   actions.collect { action ->
 *                        when (action) {
 *                             Action1 -> navController.navigate(...)
 *                             Action2 -> navController.navigate(...)
 *                        }
 *                   }
 *              }
 *         }
 *     }
 * }
 * ```
 */
abstract class NavHostComposeFragment : NavHostFragment() {

    private val _action by lazy {
        MutableStateFlow(requireArguments().requireNavigationAction<Action<Args>>())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        onCreateCompose(_action.asStateFlow())
    }

    /**
     *
     */
    abstract fun ComposeView.onCreateCompose(actions: StateFlow<Action<Args>>)

    override fun handle(action: Action<Args>) {
        _action.value = action
    }
}