package app.monkpad.billmanager.data.local_data.models

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithBills(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "title",
        entityColumn = "description"
    )
    val billEntities: List<BillEntity>
)