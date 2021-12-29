package app.monkpad.billmanager.data.repositories

import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.domain.Bill

class BillsRepository(
    private val localDataSource: BillsLocalDataSource
) {
    suspend fun addBill(bill: Bill, categoryId: Int) =
        localDataSource.addBill(bill, categoryId)

    suspend fun getBills() = localDataSource.getCategoriesWithBills()

    suspend fun getBill(billId: Int) = localDataSource.getBill(billId)
}