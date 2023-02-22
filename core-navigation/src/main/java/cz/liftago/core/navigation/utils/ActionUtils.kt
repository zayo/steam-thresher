package cz.liftago.core.navigation.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import kotlin.properties.ReadOnlyProperty

/**
 * Intent extra which contains [Action].
 */
private const val EXTRA_NAVIGATION_ACTION = "navigation_action"

/**
 * Inserts the [Action] instance into [Bundle].
 *
 * @param action The navigator action.
 *
 * @return The same [Bundle] for chaining calls.
 */
fun Bundle.putNavigationAction(action: Action<Args>): Bundle = apply {
    putParcelable(EXTRA_NAVIGATION_ACTION, action)
}

/**
 * Reads the [Action] from [Bundle].
 *
 * @return The [Action] instance.
 */
fun <T : Action<*>> Bundle.requireNavigationAction(): T =
    this.requireParcelable(EXTRA_NAVIGATION_ACTION)

/**
 * Checks if the [Bundle] contains [Action] param, thus it's safe to call
 * [requireNavigationAction].
 */
fun Bundle.hasNavigationAction(): Boolean =
    this.containsKey(EXTRA_NAVIGATION_ACTION)

/**
 * Inserts the [Action] instance into [Intent].
 *
 * @param action The navigator action.
 *
 * @return The same [Intent] for chaining calls.
 */
fun <T : Action<*>> Intent.putNavigationAction(action: T): Intent =
    this.putExtra(EXTRA_NAVIGATION_ACTION, action)

/**
 * Reads the [Action] from [Intent] extras.
 *
 * @return The [Action] instance.
 */
fun <T : Action<*>> Intent.requireNavigationAction(): T =
    this.requireParcelableExtra(EXTRA_NAVIGATION_ACTION)

/**
 * Checks if the [Intent] contains [Action] param, thus it's safe to call
 * [requireNavigationAction].
 */
fun Intent.hasNavigationAction(): Boolean =
    this.hasExtra(EXTRA_NAVIGATION_ACTION)

/**
 * Convenience method for retrieving [Parcelable] from [Intent] of type [T].
 *
 * @param message Optional message for exception.
 *
 * @return A parcelable value.
 *
 * @throws IllegalArgumentException when the value is `null` or not of type [T].
 * @see Intent.getParcelableExtra
 */
fun <T : Parcelable> Intent.requireParcelableExtra(
    key: String,
    message: () -> String = {
        "Intent has no Parcelable extra with key '$key' of given type."
    }
): T = @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    ?: throw IllegalArgumentException(message())


/**
 * Convenience method for retrieving [Parcelable] from [Bundle] of type [T].
 *
 * @param message Optional message for exception.
 *
 * @return A parcelable value.
 *
 * @throws IllegalArgumentException when the value is `null` or not of type [T].
 * @see Bundle.getParcelable
 */
fun <T : Parcelable> Bundle.requireParcelable(
    key: String,
    message: () -> String = {
        "Bundle has no Parcelable with key '$key' of given type."
    }
): T = @Suppress("DEPRECATION") getParcelable(key) as? T
    ?: throw IllegalArgumentException(message())

fun <T : Action<*>> Activity.navigationAction(): ReadOnlyProperty<Activity, T> =
    ReadOnlyProperty { thisRef, _ -> thisRef.intent.requireNavigationAction() }
