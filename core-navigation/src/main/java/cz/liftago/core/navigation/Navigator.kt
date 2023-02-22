package cz.liftago.core.navigation

import android.app.PendingIntent
import android.content.Context
import androidx.annotation.MainThread

/**
 * Interface definition for a navigation inside the app.
 */
interface Navigator {

    /**
     * Uses the `action` to navigate. This method should be called from [MainThread].
     *
     * @param action The action instance with its args.
     */
    @MainThread
    fun navigate(action: Action<Args>)

    /**
     * Creates a navigation intent for notifications with specified [action].
     *
     * @param context The android context
     * @param action The action instance with its args.
     * @param requestCode Optional request code for the [PendingIntent]. It's necessary to use this
     * when using the same [action] class for two simultaneously existing [PendingIntent]s (even
     * when the [action]s have different arguments).
     *
     * @return Returns [PendingIntent] with set action for navigator.
     */
    fun createNavigationPendingIntent(
        context: Context,
        action: Action<Args>,
        requestCode: Int? = null
    ): PendingIntent
}
