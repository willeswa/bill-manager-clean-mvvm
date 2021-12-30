package app.monkpad.billmanager

import android.app.Application
import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.data.local_data.datasource.CategoryLocalDataSource
import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.data.repositories.CategoriesRepository
import app.monkpad.billmanager.domain.usecases.AddBillUseCase
import app.monkpad.billmanager.domain.usecases.AddCategoryUseCase
import app.monkpad.billmanager.domain.usecases.GetBillsUseCase
import app.monkpad.billmanager.domain.usecases.GetCategoryUseCase
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import app.monkpad.billmanager.framework.UseCases

class BillManagerApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val billRepository = BillsRepository(BillsLocalDataSource(applicationContext))
        val categoryRepository = CategoriesRepository(CategoryLocalDataSource(applicationContext))

        BillManagerViewModelFactory.inject(
            this,
            UseCases(
                AddBillUseCase(billRepository),
                GetBillsUseCase(billRepository),
                AddCategoryUseCase(categoryRepository),
                GetCategoryUseCase(categoryRepository)
            )
        )
    }
}