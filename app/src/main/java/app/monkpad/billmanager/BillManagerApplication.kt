package app.monkpad.billmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import app.monkpad.billmanager.data.repositories.BillsRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BillManagerApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var billRepository: BillsRepository

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        createChannel(getString(R.string.bill_notification_channel_id),
            getString(R.string.notification_channel_name))

        Firebase.messaging.subscribeToTopic("billReminder")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("ERROR_TAG_FCM", "NOT SUCCESSFUL")
                }
            }


    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                enableVibration(true)
                description = "Time to pay 'em"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()


    companion object {
        const val REQUEST_CODE = 2030
        const val action_reset = "RESET_REMINDER"
        const val action_send_reminders = "SEND_REMINDER"
    }


}