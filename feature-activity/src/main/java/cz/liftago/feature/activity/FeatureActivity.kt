package cz.liftago.feature.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.liftago.core.demo.ActionsEnumerator
import cz.liftago.core.demo.Notificator
import cz.liftago.core.navigation.Navigator
import cz.liftago.core.navigation.RootAction
import cz.liftago.core.navigation.actions.FeatureActivityAction
import cz.liftago.core.navigation.utils.navigationAction
import cz.liftago.ui.components.DropdownWithButton
import cz.liftago.ui.components.Toolbar
import cz.liftago.ui.strings.R
import cz.liftago.ui.theme.setThemedContent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Example feature having an activity.
 * While this is possible, it's better not to be used since this goes completely out of the
 * navigation pattern.
 * The biggest issue is, NavHostActivity -> FeatureActivity -> NavHostActivity.
 * As the NavHostActivity can exists only once, the FeatureActivity would become a new parent and
 * the backpress can be used only once. This is currently prevented by calling [Navigator.navigate]
 * then [finish]. This is generally not sustainable pattern and better to be avoided.
 * It also lacks a lot of `startActivityForResult` functionality, that might be required.
 *
 * So keeping this here just for demo. For example light weight WebView activity might be a valid
 * case for this kind of navigation, or any other outer navigation.
 * Yet it should avoid using the [Navigator] to avoid messing with the navigation.
 */
@AndroidEntryPoint
class FeatureActivity : ComponentActivity() {

    @Inject
    lateinit var enumerator: ActionsEnumerator

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var notificator: Notificator

    private val action by navigationAction<FeatureActivityAction>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setThemedContent {
            Scaffold(
                topBar = {
                    Toolbar(
                        title = stringResource(id = R.string.title_feature_activity),
                        onBack = { finish() }
                    )
                }
            ) { paddingValues ->
                val actions = enumerator.navigable()
                val options = actions.map { act ->
                    listOfNotNull(
                        "[Root]".takeIf { act is RootAction },
                        "[Self]".takeIf { act::class == action::class },
                        act::class.simpleName
                    ).joinToString(" ")
                }

                Column(modifier = Modifier.padding(paddingValues)) {
                    DropdownWithButton(
                        actionText = stringResource(id = R.string.action_notify),
                        options = options,
                        onConfirmed = {
                            notificator.notify(actions[it])
                        },
                    )
                    DropdownWithButton(
                        actionText = stringResource(id = R.string.action_navigate),
                        options = options,
                        onConfirmed = {
                            navigator.navigate(actions[it])
                            // Finish to let NavHostActivity become a parent again.
                            finish()
                        },
                    )
                }
            }
        }
    }
}