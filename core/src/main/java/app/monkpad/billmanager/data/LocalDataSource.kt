package app.monkpad.billmanager.data

import app.monkpad.billmanager.domain.Bill
import app.monkpad.billmanager.domain.Category

interface BillsLocalDataSource {
    suspend fun getCategoriesWithBills(): List<Category>

    suspend fun addBill(bill: Bill, categoryId: Int)

    suspend fun getBill(billId: Int): Bill
}


