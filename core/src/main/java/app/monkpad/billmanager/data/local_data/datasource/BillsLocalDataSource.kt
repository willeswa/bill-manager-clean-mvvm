package app.monkpad.billmanager.data.local_data.datasource

import android.content.Context
import app.monkpad.billmanager.data.local_data.database.BillDao
import app.monkpad.billmanager.data.local_data.database.BillsManagerDatabase
import app.monkpad.billmanager.data.mappers.asCreatedEntityModel
import app.monkpad.billmanager.data.mappers.asDomainModel
import app.monkpad.billmanager.data.mappers.asEntityModel
import app.monkpad.billmanager.domain.models.Bill
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BillsLocalDataSource @Inject constructor(@ApplicationContext context: Context){

    @Inject lateinit var billsDao: BillDao

    fun getBills(): Flow<List<Bill>> =
        billsDao.getBills().map { it.map {billEntity ->  billEntity.asDomainModel() }}

    suspend fun addBill(bill: Bill){
        billsDao.addBill(bill.asCreatedEntityModel())
    }

    suspend fun toggleBillStatus(bill: Bill) =
        billsDao.toggleBillStatus(bill.asEntityModel())

    suspend  fun deleteBill(bill: Bill) {
        billsDao.deleteBill(bill.asEntityModel())
    }

    fun getBill(id: Int): Flow<Bill> =
        billsDao.getBill(id).map{it.asDomainModel()}

    suspend fun updateBill(bill: Bill) =
        billsDao.updateBill(bill.asEntityModel())

    fun getBillsForScheduling(): List<Bill> =
        billsDao.getBillsForScheduling().map { it.asDomainModel()}
}