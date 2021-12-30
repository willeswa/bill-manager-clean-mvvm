package app.monkpad.billmanager.data.local_data.datasource

import android.content.Context
import app.monkpad.billmanager.data.local_data.database.BillsManagerDatabase
import app.monkpad.billmanager.data.mappers.asDomainModel
import app.monkpad.billmanager.data.mappers.asEntityModel
import app.monkpad.billmanager.domain.models.Bill


class BillsLocalDataSource (context: Context){

    private val billsDao = BillsManagerDatabase.getDatabase(context.applicationContext)
        .billDao()

    suspend fun getBills(categoryTitle: String): List<Bill>{
        return billsDao.getBills(categoryTitle).map{it.asDomainModel()}
    }

    suspend fun addBill(bill: Bill, title: String){
        billsDao.addBill(bill.asEntityModel(title))
    }
}


