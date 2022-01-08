package app.monkpad.billmanager.data.local_data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills_table")
data class BillEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val amount: Float,
    @ColumnInfo(name = "due_date") val dueDate: Long,
    @ColumnInfo(name =  "category_title") val categoryTitle: String,
    val repeat: Int?,
    val settled: Boolean
)
