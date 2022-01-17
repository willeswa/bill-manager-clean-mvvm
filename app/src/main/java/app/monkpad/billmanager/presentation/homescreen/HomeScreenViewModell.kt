package app.monkpad.billmanager.presentation.homescreen

import android.app.Application
import androidx.lifecycle.*
import app.monkpad.billmanager.framework.BillManagerViewModel
import app.monkpad.billmanager.framework.UseCases
import app.monkpad.billmanager.framework.mappers.asDomainModel
import app.monkpad.billmanager.framework.mappers.asPresentationModel
import app.monkpad.billmanager.framework.models.BillDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenViewModel(
    application: Application,
    useCases: UseCases
) : BillManagerViewModel(application, useCases) {

    private val _showPaid = MutableLiveData(false)
    val showPaid: LiveData<Boolean> = _showPaid

    val isLoading = MutableLiveData(true)

    private val _bills = useCases.getBillsUseCase().map { billList ->
        billList.map { bill -> bill.asPresentationModel() }
            .sortedBy { it.dueDate }
    }.asLiveData().also {
        viewModelScope.launch {
            delay(2000)
            isLoading.postValue(false)
        }
    }

    val unpaidBills: LiveData<List<BillDTO>> = Transformations.map(_bills) { bills ->
        bills.filter { !it.paid }
    }

    val paidBills: LiveData<List<BillDTO>> = Transformations.map(_bills) { bills ->
        bills.filter { it.paid }
    }

    val billValue: LiveData<Float> = Transformations.map(unpaidBills) { billsToSum ->
        billsToSum.sumOf { it.amount.toDouble() }.toFloat()
    }

    fun onTabItemClicked(tabText: String) {
        _showPaid.postValue(tabText == "Paid")
    }

    fun onBillStatusToggled(bill: BillDTO) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                useCases.toggleBillStatusUseCase(bill.asDomainModel())
            }
        }
    }

    fun onBillDeleted(bill: BillDTO) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                useCases.deleteBillUseCase(bill.asDomainModel())
            }
        }
    }

    fun onHomeNavItemClicked() {
        _showPaid.postValue(false)
    }

}