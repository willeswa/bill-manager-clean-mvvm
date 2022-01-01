package app.monkpad.billmanager.presentation.homescreen

import android.app.Application
import androidx.lifecycle.*
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category
import app.monkpad.billmanager.framework.BillManagerViewModel
import app.monkpad.billmanager.framework.UseCases
import app.monkpad.billmanager.framework.mappers.asDomainModel
import app.monkpad.billmanager.framework.mappers.asPresentationModel
import app.monkpad.billmanager.framework.models.BillDTO
import kotlinx.coroutines.*
import kotlinx.coroutines.coroutineScope

class HomeScreenViewModel(
    application: Application,
    useCases: UseCases): BillManagerViewModel(application, useCases) {

    private val _paid = MutableLiveData(false)

    private val categories = MutableLiveData<List<Category>>()

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                useCases.addCategoryUseCase(
                    Category(
                        name = "Food",
                        logo = "dummy"
                    ))
            }
        }

        viewModelScope.launch {
            categories.postValue(
                withContext(Dispatchers.IO){
                    useCases.getCategoryUseCase()
                }
            )
        }
    }

    val paid: LiveData<Boolean> = _paid

    val bills = MediatorLiveData<List<BillDTO>>().apply {
        addSource(categories){catList ->
            catList.map{category ->
                viewModelScope.launch{
                    postValue(useCases.getBillUseCase(category).map{ it ->
                        it.asPresentationModel(category)
                    }.sortedBy { it.dueDate })
                }
            }
        }
        }



    val unpaidBills: LiveData<List<BillDTO>> = Transformations.map(bills){ bills ->
        bills.filter{!it.paid}
    }

    val paidBills: LiveData<List<BillDTO>> = Transformations.map(bills){ bills ->
        bills.filter{it.paid}
    }

    val billValue: LiveData<Float> = Transformations.map(unpaidBills){ billsToSum ->
        billsToSum.sumOf { it.amount.toDouble() }.toFloat()
    }

    fun showPaid(tabText: String){
        _paid.postValue(tabText == "Paid")
    }

    fun toggleBillStatus(bill: BillDTO) {
        coroutineScope.launch{
            withContext(Dispatchers.IO){
                useCases.toggleBillStatusUseCase(bill.asDomainModel(), bill.categoryName)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}