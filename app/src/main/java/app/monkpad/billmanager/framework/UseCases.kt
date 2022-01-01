package app.monkpad.billmanager.framework

import app.monkpad.billmanager.domain.usecases.*

class UseCases(
    val addBillUseCase: AddBillUseCase,
    val getBillUseCase: GetBillsUseCase,
    val addCategoryUseCase: AddCategoryUseCase,
    val getCategoryUseCase: GetCategoryUseCase,
    val toggleBillStatusUseCase: ToggleBillStatusUseCase
)