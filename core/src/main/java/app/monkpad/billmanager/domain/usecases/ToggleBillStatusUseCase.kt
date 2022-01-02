package app.monkpad.billmanager.domain.usecases

import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.domain.models.Bill

class ToggleBillStatusUseCase(private val repository: BillsRepository) {
    suspend operator fun invoke(bill: Bill) =
        repository.toggleBillStatus(bill)
}