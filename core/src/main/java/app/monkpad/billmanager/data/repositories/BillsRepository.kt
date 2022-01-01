package app.monkpad.billmanager.data.repositories

import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category

class BillsRepository(
    private val localDataSource: BillsLocalDataSource
) {
    suspend fun addBill(bill: Bill, categoryTitle: String) =
        localDataSource.addBill(bill, categoryTitle)

    suspend fun getBills(category: Category): List<Bill> =
        localDataSource.getBills(category.name)

    suspend fun toggleBillStatus(bill: Bill, category: String) =
        localDataSource.toggleBillStatus(bill, category)
}