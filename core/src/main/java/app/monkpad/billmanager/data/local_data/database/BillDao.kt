package app.monkpad.billmanager.data.local_data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryWithBills

@Dao
interface BillDao {

    @Query("SELECT * FROM bills_table")
    suspend fun getBillsWithCategories(): List<CategoryWithBills>

    @Insert
    suspend fun addBill(vararg bills: BillEntity)
}