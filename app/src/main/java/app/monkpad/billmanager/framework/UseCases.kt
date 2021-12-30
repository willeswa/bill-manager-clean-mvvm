package app.monkpad.billmanager.framework

import app.monkpad.billmanager.domain.usecases.AddBillUseCase
import app.monkpad.billmanager.domain.usecases.AddCategoryUseCase
import app.monkpad.billmanager.domain.usecases.GetBillsUseCase
import app.monkpad.billmanager.domain.usecases.GetCategoryUseCase

class UseCases(
    val addBillUseCase: AddBillUseCase,
    val getBillUseCase: GetBillsUseCase,
    val addCategoryUseCase: AddCategoryUseCase,
    val getCategoryUseCase: GetCategoryUseCase
)