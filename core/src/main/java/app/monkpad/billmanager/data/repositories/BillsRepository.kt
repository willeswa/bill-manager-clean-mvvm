package app.monkpad.billmanager.data.repositories

import android.util.Log
import app.monkpad.billmanager.data.local_data.datasource.BillsLocalDataSource
import app.monkpad.billmanager.domain.models.Bill
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BillsRepository @Inject constructor(
    private val localDataSource: BillsLocalDataSource
) {
    suspend fun addBill(bill: Bill) {
        Log.i("local_datasource_add", "" + bill.repeat)
        localDataSource.addBill(bill)
    }

    fun getBills(): Flow<List<Bill>> =
        localDataSource.getBills()

    suspend fun toggleBillStatus(bill: Bill) =
        localDataSource.toggleBillStatus(bill)

    suspend fun deleteBill(bill: Bill) {
        localDataSource.deleteBill(bill)
    }

    fun getBill(id: Int): Flow<Bill> =
        localDataSource.getBill(id)

    suspend fun updateBill(bill: Bill) =
        localDataSource.updateBill(bill)

    suspend fun getBillsForScheduling() =
        localDataSource.getBillsForScheduling()


}