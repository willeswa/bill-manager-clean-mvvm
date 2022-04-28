package app.monkpad.billmanager.domain.usecases

import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillsUseCase @Inject constructor(private val repository: BillsRepository) {

    operator fun invoke(): Flow<List<Bill>> =
        repository.getBills()
}