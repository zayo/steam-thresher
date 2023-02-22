package cz.liftago.core.demo

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cz.liftago.core.R
import cz.liftago.core.navigation.Action
import cz.liftago.core.navigation.Args
import cz.liftago.core.navigation.Navigator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class NotificatorImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val navigator: Navigator
) : Notificator {

    init {
        createNotificationChannel()
    }

    @SuppressLint("MissingPermission")
    override fun notify(action: Action<Args>) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Notification for ${action::class.simpleName}")
            .setContentText("Will open the app in the desired state")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(navigator.createNavigationPendingIntent(context, action))

        NotificationManagerCompat.from(context).notify(0, builder.build())
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification channel"
            val descriptionText = "Notifications from Steam Thresher"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }
}

private const val CHANNEL_ID = "notification_channel"