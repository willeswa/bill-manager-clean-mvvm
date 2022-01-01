package app.monkpad.billmanager.framework.mappers

import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.framework.models.BillDTO

fun BillDTO.asDomainModel(): Bill =
    Bill(
        description = description,
        amount = amount,
        dueDate = dueDate,
        repeat = repeat,
        settled = paid
    )