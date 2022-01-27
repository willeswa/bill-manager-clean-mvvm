package app.monkpad.billmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.WorkManager
import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.data.local_data.datasource.CategoryLocalDataSource
import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.data.repositories.CategoriesRepository
import app.monkpad.billmanager.domain.usecases.*
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import app.monkpad.billmanager.framework.UseCases
import app.monkpad.billmanager.utils.Utility.scheduleRepeatingBills

class BillManagerApplication : Application() {
    private lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        workManager = WorkManager.getInstance(applicationContext)
        createChannel(getString(R.string.bill_notification_channel_id),
            getString(R.string.notification_channel_name))
        scheduleRepeatingBills(workManager)

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


}