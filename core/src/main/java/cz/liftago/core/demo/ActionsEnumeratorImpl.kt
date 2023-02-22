package cz.liftago.core.demo

import cz.liftago.core.navigation.actions.DefaultAction
import cz.liftago.core.navigation.actions.FeatureActivityAction
import cz.liftago.core.navigation.actions.FeatureComposeAction
import cz.liftago.core.navigation.actions.FeatureFragmentAction
import javax.inject.Inject

internal class ActionsEnumeratorImpl @Inject constructor(): ActionsEnumerator {
    override fun navigable() = listOf(
        DefaultAction,
        FeatureActivityAction,
        FeatureComposeAction,
        FeatureFragmentAction,
    )
}