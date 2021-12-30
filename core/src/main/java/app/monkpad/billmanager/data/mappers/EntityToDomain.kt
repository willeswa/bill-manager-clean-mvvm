package app.monkpad.billmanager.data.mappers

import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryEntity
import app.monkpad.billmanager.data.local_data.models.CategoryWithBills
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category

internal fun BillEntity.asDomainModel(): Bill =
    Bill(
        description = description,
        amount = amount,
        dueDate =dueDate,
        repeat = repeat,
        settled = settled
    )

internal fun CategoryEntity.asDomainModel(): Category =
    Category(
        name = title,
        logo = logo,
    )

