package app.monkpad.billmanager.framework.mappers

import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category
import app.monkpad.billmanager.framework.models.BillDTO


fun Bill.asPresentationModel(category: Category): BillDTO =
    BillDTO(
        description = description,
        amount = amount,
        dueDate = dueDate,
        categoryName = category.name,
        categoryLogo = category.logo,
        repeat = repeat,
        paid = settled,
        overdue = System.currentTimeMillis() > dueDate && !settled)