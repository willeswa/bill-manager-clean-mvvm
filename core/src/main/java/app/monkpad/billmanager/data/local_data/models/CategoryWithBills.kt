package app.monkpad.billmanager.data.local_data.models

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithBills(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "billId"
    )
    val billEntities: List<BillEntity>
)