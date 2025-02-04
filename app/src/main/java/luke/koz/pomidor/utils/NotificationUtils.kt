package luke.koz.pomidor.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import luke.koz.pomidor.MainActivity
import luke.koz.pomidor.R

object NotificationUtils {
    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            "pomodoro_timer_channel",
            "Pomodoro Timer",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Pom pom done"
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(context: Context, title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "pomodoro_timer_channel")
            .setSmallIcon(R.drawable.baseline_alarm_on_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            if (PermissionUtils.isNotificationPermissionGranted(context)) {
                notify(1, notification)
            }
        }
    }
}