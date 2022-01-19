package app.monkpad.billmanager.data.mappers

import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryEntity
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category

internal fun Bill.asEntityModel(): BillEntity =
    BillEntity(
        id = id,
        amount = amount,
        description = description,
        repeat = repeat,
        settled = settled,
        dueDate = dueDate,
        categoryTitle = categoryName,
        nextDueDate = nextDueDate,
        paidOn = paidOn
    )

internal fun Bill.asCreatedEntityModel(): BillEntity =
    BillEntity(
        description = description,
        amount = amount,
        dueDate = dueDate,
        categoryTitle = categoryName,
        repeat = repeat,
        settled = settled,
        nextDueDate = nextDueDate,
        paidOn = paidOn
    )


internal fun Category.asEntityModel(): CategoryEntity =
    CategoryEntity(
        title = name,
        logo = logo
    )