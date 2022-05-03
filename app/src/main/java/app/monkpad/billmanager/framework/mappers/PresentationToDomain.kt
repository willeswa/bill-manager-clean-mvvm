package app.monkpad.billmanager.framework.mappers

import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category
import app.monkpad.billmanager.framework.models.BillDTO

fun BillDTO.asDomainModel(): Bill =
    Bill(
        id = id,
        description = description,
        amount = amount,
        dueDate = dueDate,
        repeat = repeat,
        settled = paid,
        categoryName = categoryName,
        nextDueDate = nextDueDate,
        paidOn = paidOn
    )
