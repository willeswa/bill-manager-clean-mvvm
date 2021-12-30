package app.monkpad.billmanager.domain.usecases

import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category

class GetBillsUseCase(private val repository: BillsRepository) {

    suspend operator fun invoke(category: Category): List<Bill> =
        repository.getBills(category)
}