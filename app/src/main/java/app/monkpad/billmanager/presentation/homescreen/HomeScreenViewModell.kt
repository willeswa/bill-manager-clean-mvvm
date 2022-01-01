package app.monkpad.billmanager.presentation.homescreen

import android.app.Application
import androidx.lifecycle.*
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category
import app.monkpad.billmanager.framework.BillManagerViewModel
import app.monkpad.billmanager.framework.UseCases
import app.monkpad.billmanager.framework.mappers.asPresentationModel
import app.monkpad.billmanager.framework.models.BillDTO
import kotlinx.coroutines.*
import kotlinx.coroutines.coroutineScope

class HomeScreenViewModel(
    application: Application,
    useCases: UseCases): BillManagerViewModel(application, useCases) {

    private val _paid = MutableLiveData(false)

    private val categories = MutableLiveData<List<Category>>()

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

    val billValue: LiveData<Float> = Transformations.map(bills){ billsToSum ->
        billsToSum.sumOf { it.amount.toDouble() }.toFloat()
    }

    val unpaidBills: LiveData<List<BillDTO>> = Transformations.map(bills){ bills ->
        bills.filter{!it.paid}
    }

    val paidBills: LiveData<List<BillDTO>> = Transformations.map(bills){ bills ->
        bills.filter{it.paid}
    }

    fun showPaid(tabText: String){
        _paid.postValue(tabText == "Paid")
    }


}