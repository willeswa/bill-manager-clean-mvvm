package app.monkpad.billmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import app.monkpad.billmanager.cronjob.ScheduleRepeatingBillsWork
import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.data.local_data.datasource.CategoryLocalDataSource
import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.data.repositories.CategoriesRepository
import app.monkpad.billmanager.domain.usecases.*
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import app.monkpad.billmanager.framework.UseCases
import java.util.concurrent.TimeUnit

private const val BILL_REMINDER_JOB = "bill_reminder_job"

class BillManagerApplication : Application() {
    private lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()

        workManager = WorkManager.getInstance(applicationContext)
        createChannel(getString(R.string.bill_notification_channel_id),
            getString(R.string.notification_channel_name))
        scheduleRepeatingBills()

        val billRepository = BillsRepository(BillsLocalDataSource(applicationContext))
        val categoryRepository = CategoriesRepository(CategoryLocalDataSource(applicationContext))

        BillManagerViewModelFactory.inject(
            this,
            UseCases(
                AddBillUseCase(billRepository),
                GetBillsUseCase(billRepository),
                AddCategoryUseCase(categoryRepository),
                GetCategoryUseCase(categoryRepository),
                ToggleBillStatusUseCase(billRepository),
                DeleteBillUseCase(billRepository),
                GetBillUseCase(billRepository),
                UpdateBillUseCase(billRepository)
            )
        )

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

    private fun scheduleRepeatingBills() {
        val periodicRequest = PeriodicWorkRequestBuilder<ScheduleRepeatingBillsWork>(
            24,
            TimeUnit.HOURS,
            6,
            TimeUnit.HOURS
        ).build()
        workManager.enqueueUniquePeriodicWork(
            BILL_REMINDER_JOB,
        ExistingPeriodicWorkPolicy.KEEP,
        periodicRequest)
    }


}