package app.monkpad.billmanager.data.repositories

import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category
import kotlinx.coroutines.flow.Flow

class BillsRepository(
    private val localDataSource: BillsLocalDataSource
) {
    suspend fun addBill(bill: Bill) =
        localDataSource.addBill(bill)

    fun getBills(): Flow<List<Bill>> =
        localDataSource.getBills()

    suspend fun toggleBillStatus(bill: Bill) =
        localDataSource.toggleBillStatus(bill)

    suspend fun deleteBill(bill: Bill) {
        localDataSource.deleteBill(bill)
    }

}