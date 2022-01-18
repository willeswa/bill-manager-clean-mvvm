package app.monkpad.billmanager.cronjob

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.monkpad.billmanager.R
import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.utils.Utility
import app.monkpad.billmanager.utils.sendNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScheduleRepeatingBillsWork(appContext: Context, workParams: WorkerParameters) :
        CoroutineWorker(appContext, workParams) {

    private val repository = BillsRepository(BillsLocalDataSource(appContext))

    private val appContext = applicationContext

    override suspend fun doWork(): Result {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext, NotificationManager::class.java) as NotificationManager

        return try {
            withContext(Dispatchers.IO) {

                val billsForScheduling = repository.getBillsForScheduling()

                val billsAlreadyPastDue = billsPastDue(billsForScheduling)

                withContext(Dispatchers.Main) {
                    if (billsAlreadyPastDue.size > 1) {
                        notificationManager.sendNotification(
                            appContext.getString(
                                R.string.many_notification_body_text,
                                billsAlreadyPastDue.size),
                            appContext)
                    } else if (billsAlreadyPastDue.size == 1) {
                        notificationManager.sendNotification(
                            appContext.getString(
                                R.string.one_notification_body_text,
                                billsAlreadyPastDue[0].description
                            ),
                            appContext)
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()

        }

    }

    private fun billsPastDue(billsForScheduling: List<Bill>): MutableList<Bill> {
        val billsPastDue = mutableListOf<Bill>()

        for (bill in billsForScheduling) {

            val dayBillIsDue = Utility.formattedDate(bill.dueDate)
            val currentDay = Utility.formattedDate(System.currentTimeMillis())

            if ((dayBillIsDue == currentDay || bill.dueDate < System.currentTimeMillis()) && !bill.settled) {
                billsPastDue.add(bill)
            }
        }
        return billsPastDue
    }


}