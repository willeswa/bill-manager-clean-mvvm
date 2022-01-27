package app.monkpad.billmanager.framework.cronjob

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import app.monkpad.billmanager.utils.Utility.scheduleRepeatingBills

class ScheduleRepeatingBillsReceiver : BroadcastReceiver() {
    private lateinit var workManager: WorkManager
    override fun onReceive(context: Context?, intent: Intent?) {
        workManager = WorkManager.getInstance(context!!)
        scheduleRepeatingBills(workManager)
    }
}