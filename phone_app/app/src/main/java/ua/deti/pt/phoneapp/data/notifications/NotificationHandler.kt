package ua.deti.pt.phoneapp.data.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class NotificationHandler(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "notification_channel_id"

    fun showSimpleNotification() {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle("Do you even lift, bro?")
            .setContentText("Use PhoneApp to track your progress and get better every day!")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    fun returnAllNotifications(): List<Notification> {
        val activeNotifications = notificationManager.activeNotifications
        return activeNotifications.map { it.notification }.toList()
    }

    fun getAllNotificationTitlesAndTexts(): List<Pair<String?, String?>> {
        val activeNotifications = notificationManager.activeNotifications
        return activeNotifications.map { Pair(it.notification.extras?.getString(NotificationCompat.EXTRA_TITLE), it.notification.extras?.getString(NotificationCompat.EXTRA_TEXT)) }
    }

    fun deleteNotification(i: Int) {
        val activeNotifications = notificationManager.activeNotifications
        if (i in activeNotifications.indices) {
            val notificationId = activeNotifications[i].id
            notificationManager.cancel(notificationId)
        }
    }
}