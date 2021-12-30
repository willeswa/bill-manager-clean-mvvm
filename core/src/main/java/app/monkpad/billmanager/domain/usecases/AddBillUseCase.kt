package app.monkpad.billmanager.domain.usecases

import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.domain.models.Bill

class AddBillUseCase(private val repository: BillsRepository) {

    suspend operator fun invoke(bill: Bill, categoryTitle: String) =
        repository.addBill(bill, categoryTitle)

}