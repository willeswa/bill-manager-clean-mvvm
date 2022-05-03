package app.monkpad.billmanager.framework.cronjob

import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.monkpad.billmanager.R
import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.utils.sendNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class ResetCronJob @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val repository: BillsRepository,
) : CoroutineWorker(appContext, params) {


    override suspend fun doWork(): Result {
        val bills = repository.getBillsForScheduling()

        for (bill in bills) {
            if (bill.settled && bill.repeat != null) {

                bill.settled = false
                bill.nextDueDate?.let {
                    bill.dueDate = it
                }

                repository.updateBill(bill)
            }

            if (bill.settled && bill.repeat == null) {
                repository.deleteBill(bill)
            }
        }

        withContext(Dispatchers.Main) {
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.sendNotification(applicationContext.getString(R.string.msg_notification_reset),
                applicationContext)

        }
        return Result.success()
    }
}