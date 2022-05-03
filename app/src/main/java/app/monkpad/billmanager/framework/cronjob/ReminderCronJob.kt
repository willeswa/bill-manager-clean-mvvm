package app.monkpad.billmanager.framework.cronjob

import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.monkpad.billmanager.R
import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.utils.sendNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class ReminderCronJob @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: BillsRepository,
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {
        val bills: List<Bill> = repository.getBillsForScheduling()
        val billsForReminder: MutableList<Bill> = mutableListOf()

        for (bill in bills) {
            if (!bill.settled && bill.dueDate < System.currentTimeMillis()) {
                billsForReminder.add(bill)
            }
        }

        withContext(Dispatchers.Main) {
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (billsForReminder.size == 1) {
                notificationManager.sendNotification("Your bill: ${billsForReminder[0].description} is passed due. Click here to view it.",
                    applicationContext)
            } else if (billsForReminder.size > 1) {
                notificationManager.sendNotification(applicationContext.getString(R.string.msg_notification_many),
                    applicationContext)
            }
        }

        return Result.success()
    }
}