package cz.liftago.core.navigation

import android.os.Parcelable

/**
 * Action with arguments. Used for navigation in a type-safe manner.
 *
 * Example of implementation:
 * ```
 * @Parcelize
 * class FeatureAction(override val args: FeatureArgs) : Action<FeatureArgs>(args)
 * ```
 * where `FeatureArgs` is implementation of [Args].
 *
 * @param args The [Args] implementation.
 *
 * @see RootAction
 */
abstract class Action<out A : Args>(open val args: A): Parcelable

/**
 * Base abstract class of [Action] with [EmptyArgs].
 *
 * Example of extending the class:
 * ```
 * @Parcelize
 * object FeatureAction : EmptyArgsAction()
 * ```
 *
 * @see RootAction
 */
abstract class EmptyArgsAction : Action<EmptyArgs>(EmptyArgs)
