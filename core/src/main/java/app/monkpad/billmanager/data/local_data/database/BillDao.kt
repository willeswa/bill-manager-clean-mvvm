package app.monkpad.billmanager.data.local_data.database

import androidx.room.*
import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryWithBills
import app.monkpad.billmanager.domain.models.Bill

@Dao
interface BillDao {

    @Transaction
    @Query("SELECT * FROM categories_table")
    suspend fun getBillsWithCategories(): List<CategoryWithBills>

    @Query("SELECT * FROM bills_table WHERE category_title=:categoryTitle")
    suspend fun getBills(categoryTitle: String): List<BillEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBill(vararg bills: BillEntity)

    @Update
    suspend fun toggleBillStatus(vararg bill: BillEntity)

}