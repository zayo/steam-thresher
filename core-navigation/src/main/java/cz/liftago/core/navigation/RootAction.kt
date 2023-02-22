package cz.liftago.core.navigation

/**
 * Marker interface for the [Action] to denote this action suppose to clear the back stack and
 * become a new root (first screen in the backstack).
 * This is useful especially when some data-driven change should reset the whole UI.
 *
 * Example of extending the class:
 * ```
 * @Parcelize
 * object FeatureAction : EmptyArgsAction(), RootAction
 * ```
 *
 * @see Action
 */
interface RootAction