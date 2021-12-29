package app.monkpad.billmanager.data.local_data.datasource

import app.monkpad.billmanager.data.BillsLocalDataSource
import app.monkpad.billmanager.data.local_data.database.BillsManagerDatabase
import app.monkpad.billmanager.data.mappers.asDomain
import app.monkpad.billmanager.data.mappers.asEntityModel
import app.monkpad.billmanager.domain.Bill
import app.monkpad.billmanager.domain.Category

class BillsLocalDataSource(private val localDatabase: BillsManagerDatabase): BillsLocalDataSource {

    override suspend fun getCategoriesWithBills(): List<Category> {
        val categories = localDatabase.billsDao.getBillsWithCategories()
        return categories.map{it.asDomain()}
    }

    override suspend fun addBill(bill: Bill, categoryId: Int) {
        localDatabase.billsDao.addBill(bill.asEntityModel(categoryId))
    }

    override suspend fun getBill(billId: Int): Bill {
        TODO("Not yet implemented")
    }

}