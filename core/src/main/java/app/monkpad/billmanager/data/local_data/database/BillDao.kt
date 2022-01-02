package app.monkpad.billmanager.data.local_data.database

import androidx.room.*
import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryWithBills
import app.monkpad.billmanager.domain.models.Bill

@Dao
interface BillDao {


    @Query("SELECT * FROM bills_table")
    suspend fun getBills(): List<BillEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBill(vararg bills: BillEntity)

    @Update
    suspend fun toggleBillStatus(vararg bill: BillEntity)

    @Delete
    suspend fun deleteBill(vararg bill: BillEntity)

}