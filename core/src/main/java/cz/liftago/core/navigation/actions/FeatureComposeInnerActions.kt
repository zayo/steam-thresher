package cz.liftago.core.navigation.actions

import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import kotlinx.parcelize.Parcelize

/**
 * Launches the UI within the :feature-compose module.
 */
@Parcelize
data class FeatureComposeInnerAction1(
    val origin: String = "",
) : Action<FeatureComposeInnerArgs>(FeatureComposeInnerArgs(origin))

@Parcelize
data class FeatureComposeInnerAction2(
    val origin: String = "",
) : Action<FeatureComposeInnerArgs>(FeatureComposeInnerArgs(origin))

@Parcelize
data class FeatureComposeInnerAction3(
    val origin: String = "",
) : Action<FeatureComposeInnerArgs>(FeatureComposeInnerArgs(origin))

@Parcelize
data class FeatureComposeInnerArgs(
    val origin: String
) : Args()
