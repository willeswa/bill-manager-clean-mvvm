package app.monkpad.billmanager.data.mappers

import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.data.local_data.models.CategoryWithBills
import app.monkpad.billmanager.domain.Bill
import app.monkpad.billmanager.domain.Category

internal fun BillEntity.toDomain(): Bill =
    Bill(
        description = description,
        amount = amount,
        dueDate =dueDate,
        repeat = repeat,
        settled = settled
    )

internal fun CategoryWithBills.asDomain(): Category =
    Category(
        name = category.title,
        logo = category.logo,
        bills = billEntities.map { it.toDomain()}
    )