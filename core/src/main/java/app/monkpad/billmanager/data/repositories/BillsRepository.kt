package app.monkpad.billmanager.data.repositories

import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category

class BillsRepository(
    private val localDataSource: BillsLocalDataSource
) {
    suspend fun addBill(bill: Bill) =
        localDataSource.addBill(bill)

    suspend fun getBills(): List<Bill> =
        localDataSource.getBills()

    suspend fun toggleBillStatus(bill: Bill) =
        localDataSource.toggleBillStatus(bill)

    suspend fun deleteBill(bill: Bill) {
        localDataSource.deleteBill(bill)
    }

}