package app.monkpad.billmanager.domain.usecases

import app.monkpad.billmanager.data.repositories.BillsRepository
import app.monkpad.billmanager.domain.models.Bill
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillUseCase @Inject constructor(private val repository: BillsRepository) {
    operator fun invoke(id: Int): Flow<Bill> =
        repository.getBill(id)
}