package cz.liftago.core.navigation.internal

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.MainThread
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.ActionsDelegate
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.Navigator
import cz.liftago.core.navigation.utils.putNavigationAction

internal class DefaultNavigator(private val delegate: ActionsDelegate) : Navigator {

    @MainThread
    override fun navigate(action: Action<Args>) = delegate.navigate(action)

    override fun createNavigationPendingIntent(
        context: Context,
        action: Action<Args>,
        requestCode: Int?
    ): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            requestCode ?: 0,
            Intent(context, NavigatorBroadcastReceiver::class.java)
                .putNavigationAction(action)
                // Because we use the same NavigatorBroadcastReceiver for all intents, we need to make the intent unique
                // in terms of PendingIntent's matching. If the data uri is not enough, caller must use requestCode.
                .setData(Uri.parse("action://${action::class.simpleName}")),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
}
