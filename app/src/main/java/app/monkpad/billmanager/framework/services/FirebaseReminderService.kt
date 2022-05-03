package app.monkpad.billmanager.framework.services;

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import app.monkpad.billmanager.BillManagerApplication
import app.monkpad.billmanager.framework.cronjob.ReminderCronJob
import app.monkpad.billmanager.framework.cronjob.ResetCronJob
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebaseReminderService: FirebaseMessagingService(){
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("TAG_MESSAGE_RECIEVED", " ==> "+message.data)

        when(message.data["reminder"]){
            BillManagerApplication.action_reset -> resetCronJob()
            BillManagerApplication.action_send_reminders -> remindCronJob()
        }
    }

    private fun remindCronJob() {
        val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<ReminderCronJob>()
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(uploadWorkRequest)
    }

    private fun resetCronJob() {
        val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<ResetCronJob>()
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(uploadWorkRequest)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}