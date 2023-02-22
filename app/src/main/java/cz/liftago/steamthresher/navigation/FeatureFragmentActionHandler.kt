package cz.liftago.steamthresher.navigation

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.actions.FeatureFragmentAction
import cz.liftago.core.navigation.blocks.ActionHandler
import cz.liftago.core.navigation.blocks.NavHostFragment
import cz.liftago.feature.fragment.FeatureFragment
import javax.inject.Inject
import kotlin.reflect.KClass

class FeatureFragmentActionHandler @Inject constructor(): ActionHandler {

    override val actions: Set<KClass<out Action<Args>>> = setOf(
        FeatureFragmentAction::class,
    )

    override val handler: KClass<out NavHostFragment> =
        FeatureFragment::class
}