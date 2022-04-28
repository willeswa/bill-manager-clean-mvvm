package app.monkpad.billmanager.domain.usecases

import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.domain.models.Bill
import javax.inject.Inject

class DeleteBillUseCase @Inject constructor(private val repository: BillsRepository) {
    suspend operator fun invoke(bill: Bill) =
        repository.deleteBill(bill)
}