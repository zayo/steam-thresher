package cz.liftago.core.navigation

import androidx.annotation.MainThread

/**
 * Interface definition for handling [Action]s used by the [Navigator] to delegate the navigation
 * of supported [Action]s.
 *
 * Implementation of this class belongs to :app module that knows what the final implementation
 * of the handling navigation is.
 *
 * Example of using a custom [ActionsDelegate] implementation inside the `:feature-foo` module would be:
 * ```
 * ActionsDelegate { action ->
 *     val intent = when (action) {
 *         is FeatureActivityAction ->
 *             Intent(context, FeatureActivity::class.java)
 *
 *         else -> // Default handling activity
 *             Intent(context, MainActivity::class.java).apply {
 *                 putNavigationAction(action)
 *             }
 *         }
 *         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
 *         context.startActivity(intent)
 *     }
 * ```
 */
fun interface ActionsDelegate {

    /**
     * Navigates user to an action defined by parameter.
     * This method is called on the same thread as [Navigator.navigate] which should be [MainThread].
     */
    @MainThread
    fun navigate(action: Action<Args>)
}
