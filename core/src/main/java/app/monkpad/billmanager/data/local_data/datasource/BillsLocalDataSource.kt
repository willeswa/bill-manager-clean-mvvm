package app.monkpad.billmanager.data.local_data.datasource

import android.content.Context
import app.monkpad.billmanager.data.local_data.database.BillsManagerDatabase
import app.monkpad.billmanager.data.mappers.asDomainModel
import app.monkpad.billmanager.data.mappers.asEntityModel
import app.monkpad.billmanager.domain.models.Bill
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BillsLocalDataSource (context: Context){

    private val billsDao = BillsManagerDatabase.getDatabase(context.applicationContext)
        .billDao()

    fun getBills(): Flow<List<Bill>> =
        billsDao.getBills().map { it.map {billEntity ->  billEntity.asDomainModel() }}

    suspend fun addBill(bill: Bill){
        billsDao.addBill(bill.asEntityModel())
    }

    suspend fun toggleBillStatus(bill: Bill) =
        billsDao.toggleBillStatus(bill.asEntityModel())

    suspend  fun deleteBill(bill: Bill) {
        billsDao.deleteBill(bill.asEntityModel())
    }


}


