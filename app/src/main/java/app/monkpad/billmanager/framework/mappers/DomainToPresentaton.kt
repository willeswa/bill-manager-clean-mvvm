package app.monkpad.billmanager.framework.mappers

import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category
import app.monkpad.billmanager.framework.models.BillDTO

fun Bill.asPresentationModel(): BillDTO =
    BillDTO(
        id = id,
        description = description,
        amount = amount,
        dueDate = dueDate,
        categoryName = categoryName,
        repeat = repeat,
        paid = settled,
        overdue = System.currentTimeMillis() > dueDate && !settled,
        nextDueDate = nextDueDate,
        paidOn = paidOn)

