package app.monkpad.billmanager.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import app.monkpad.billmanager.R
import app.monkpad.billmanager.presentation.MainActivity

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(message: String, appContext: Context) {

    val intent = Intent(appContext, MainActivity::class.java)

    val pendingIntent = PendingIntent.getActivity(
        appContext,
        NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        appContext,
        appContext.getString(R.string.bill_notification_channel_id))
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(appContext.getString(R.string.notification_title))
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setStyle(NotificationCompat.BigTextStyle())
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setOnlyAlertOnce(true)
        .setStyle(NotificationCompat.BigTextStyle()).apply {

        }

    notify(NOTIFICATION_ID, builder.build())

}