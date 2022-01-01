package app.monkpad.billmanager.data.mappers

import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryEntity
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category

internal fun Bill.asEntityModel(categoryTitle: String): BillEntity =
    BillEntity(
        amount = amount,
        description = description,
        repeat = repeat,
        settled = settled,
        dueDate = dueDate,
        categoryTitle = categoryTitle
    )


internal fun Category.asEntityModel(): CategoryEntity =
    CategoryEntity(
        title = name,
        logo = logo
    )

