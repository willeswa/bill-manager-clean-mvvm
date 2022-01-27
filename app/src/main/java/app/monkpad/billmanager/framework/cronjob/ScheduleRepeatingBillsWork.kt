package app.monkpad.billmanager.framework.cronjob

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
import java.util.*

class ScheduleRepeatingBillsWork(appContext: Context, workParams: WorkerParameters) :
        CoroutineWorker(appContext, workParams) {

    private val repository = BillsRepository(BillsLocalDataSource(appContext))

    private val appContext = applicationContext

    override suspend fun doWork(): Result {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext, NotificationManager::class.java) as NotificationManager

        return try {
            withContext(Dispatchers.IO) {

                val allBills = repository.getBillsForScheduling()

                val billsAlreadyPastDue = billsPastDue(allBills)

               if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 2){
                   updateBillsIfSettled(allBills)
                   notificationManager.sendNotification(
                       applicationContext.getString(R.string.reset_bills_message),
                       applicationContext
                   )
               }

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

    private suspend fun updateBillsIfSettled(allBills: List<Bill>) {
       withContext(Dispatchers.IO){
           for(bill in allBills){
               if(bill.settled && bill.repeat != null){
                   bill.dueDate = bill.nextDueDate!!
                   bill.settled = false
                   repository.updateBill(bill)
               } else if(bill.settled && bill.repeat == null){
                   repository.deleteBill(bill)
               }
           }
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