package app.monkpad.billmanager.data.local_data.database

import androidx.room.*
import app.monkpad.billmanager.data.local_data.models.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {

    @Query("SELECT * FROM bills_table")
    fun getBills(): Flow<List<BillEntity>>

    @Query("SELECT * FROM bills_table")
    fun getBillsForScheduling(): List<BillEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBill(vararg bills: BillEntity)

    @Update
    suspend fun toggleBillStatus(vararg bill: BillEntity)

    @Delete
    suspend fun deleteBill(vararg bill: BillEntity)

    @Query("SELECT * FROM bills_table WHERE id = :id")
    fun getBill(id: Int): Flow<BillEntity>

    @Update
    suspend fun updateBill(vararg bill: BillEntity)

}