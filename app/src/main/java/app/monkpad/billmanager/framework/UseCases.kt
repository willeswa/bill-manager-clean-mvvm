package app.monkpad.billmanager.framework

import app.monkpad.billmanager.domain.usecases.*
import javax.inject.Inject

class UseCases @Inject constructor(
    val addBillUseCase: AddBillUseCase,
    val getBillsUseCase: GetBillsUseCase,
    val toggleBillStatusUseCase: ToggleBillStatusUseCase,
    val deleteBillUseCase: DeleteBillUseCase,
    val getBillUseCase: GetBillUseCase,
    val updateBillUseCase: UpdateBillUseCase,
)