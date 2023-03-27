package cz.liftago.steamthresher.navigation

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.actions.FeatureComposeAction
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction1
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction2
import cz.liftago.core.navigation.actions.FeatureComposeInnerAction3
import cz.liftago.core.navigation.blocks.ActionHandler
import cz.liftago.core.navigation.blocks.NavHostFragment
import cz.liftago.feature.compose.FeatureComposeFragment
import javax.inject.Inject
import kotlin.reflect.KClass

class FeatureComposeActionHandler @Inject constructor() : ActionHandler {

    override val actions: Set<KClass<out Action<Args>>> = setOf(
        FeatureComposeAction::class,
        FeatureComposeInnerAction1::class,
        FeatureComposeInnerAction2::class,
        FeatureComposeInnerAction3::class,
    )

    override val handler: KClass<out NavHostFragment> =
        FeatureComposeFragment::class
}