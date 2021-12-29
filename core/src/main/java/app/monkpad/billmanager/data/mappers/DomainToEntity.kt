package app.monkpad.billmanager.data.mappers

import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.domain.Bill

internal fun Bill.asEntityModel(categoryId: Int): BillEntity =
    BillEntity(
        amount = amount,
        description = description,
        categoryId = categoryId,
        repeat = repeat,
        settled = settled,
        dueDate = dueDate
    )