package cz.liftago.feature.compose.viewmodel

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args

interface FeatureComposeViewModel {

    val identity: String

    fun navigate(action: Action<Args>)
}