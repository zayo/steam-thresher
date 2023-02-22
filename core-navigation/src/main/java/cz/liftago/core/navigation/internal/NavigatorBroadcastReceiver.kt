package cz.liftago.core.navigation.internal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cz.liftago.core.navigation.utils.requireNavigationAction

/**
 * Listens to navigation actions sent from notifications and navigates to specified action.
 */
internal class NavigatorBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent): Unit =
        NavigatorInstance.navigator.navigate(intent.requireNavigationAction())
}
