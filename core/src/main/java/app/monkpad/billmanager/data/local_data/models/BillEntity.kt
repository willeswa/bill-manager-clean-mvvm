package app.monkpad.billmanager.data.local_data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills_table")
data class BillEntity(

    @PrimaryKey
    val description: String,
    val amount: Float,
    @ColumnInfo(name = "due_date") val dueDate: String,
    @ColumnInfo(name =  "category_title") val categoryTitle: String,
    val repeat: Boolean, //we will use this to setup a job
    val settled: Boolean
)
