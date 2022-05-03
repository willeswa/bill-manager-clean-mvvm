package app.monkpad.billmanager.data.mappers

import app.monkpad.billmanager.data.local_data.models.BillEntity
import app.monkpad.billmanager.domain.models.Bill

internal fun BillEntity.asDomainModel(): Bill =
    Bill(
        id = id,
        description = description,
        amount = amount,
        dueDate = dueDate,
        repeat = repeat,
        settled = settled,
        categoryName = categoryTitle,
        nextDueDate = nextDueDate,
        paidOn = paidOn
    )