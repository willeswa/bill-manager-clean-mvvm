package app.monkpad.billmanager.presentation.homescreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import app.monkpad.billmanager.framework.BillManagerViewModel
import app.monkpad.billmanager.framework.UseCases
import app.monkpad.billmanager.framework.mappers.asDomainModel
import app.monkpad.billmanager.framework.mappers.asPresentationModel
import app.monkpad.billmanager.framework.models.BillDTO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map


class HomeScreenViewModel(
    application: Application,
    useCases: UseCases
) : BillManagerViewModel(application, useCases) {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val _paid = MutableLiveData(false)
    val paid: LiveData<Boolean> = _paid
    val loading = MutableLiveData(true)

    private val _bills = useCases.getBillUseCase().map { billList ->
        billList.map { bill ->
            bill.asPresentationModel()
        }
            .sortedBy { billSorted ->
                billSorted.dueDate
            }

    }.asLiveData().also {
        coroutineScope.launch {
            delay(2000)
            loading.postValue(false)
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

    fun showPaid(tabText: String) {
        _paid.postValue(tabText == "Paid")
    }

    fun toggleBillStatus(bill: BillDTO) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                useCases.toggleBillStatusUseCase(bill.asDomainModel())
            }
        }
    }

    fun deleteBill(bill: BillDTO) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                useCases.deleteBillUseCase(bill.asDomainModel())
            }
        }
    }

    fun atHomeScreen(){
        _paid.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}