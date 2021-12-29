package app.monkpad.billmanager.data.local_data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills_table")
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val billId: Int = 1,
    val amount: Float,
    val description: String,
    @ColumnInfo(name = "due_date") val dueDate: String,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    val repeat: Boolean, //we will use this to setup a job
    val settled: Boolean
)
