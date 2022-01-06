package app.monkpad.billmanager.framework

import app.monkpad.billmanager.domain.usecases.*

class UseCases(
    val addBillUseCase: AddBillUseCase,
    val getBillsUseCase: GetBillsUseCase,
    val addCategoryUseCase: AddCategoryUseCase,
    val getCategoryUseCase: GetCategoryUseCase,
    val toggleBillStatusUseCase: ToggleBillStatusUseCase,
    val deleteBillUseCase: DeleteBillUseCase,
    val getBillUseCase: GetBillUseCase,
    val updateBillUseCase: UpdateBillUseCase
)