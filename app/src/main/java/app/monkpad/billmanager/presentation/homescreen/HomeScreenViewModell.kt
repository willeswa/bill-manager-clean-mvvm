package app.monkpad.billmanager.presentation.homescreen

import android.app.Application
import androidx.lifecycle.*
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.domain.models.Category
import app.monkpad.billmanager.framework.BillManagerViewModel
import app.monkpad.billmanager.framework.UseCases
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

        viewModelScope.launch{
            withContext(Dispatchers.IO){
                useCases.addBillUseCase(
                    Bill(
                        description = "Bill 2",
                        amount = 10f,
                        dueDate = "May 11",
                        repeat = true,
                        settled = false
                    ),
                    "Food"
                )
                useCases.addBillUseCase(
                    Bill(
                        description = "Bill 3",
                        amount = 10f,
                        dueDate = "May 11",
                        repeat = true,
                        settled = true
                    ),
                    "Food"
                )
                useCases.addBillUseCase(
                    Bill(
                        description = "Bill 5",
                        amount = 10f,
                        dueDate = "May 11",
                        repeat = true,
                        settled = false
                    ),
                    "Food"
                )
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


    val bills = MediatorLiveData<List<Bill>>().apply {
        addSource(categories){
            it.map{
                viewModelScope.launch{
                    postValue(useCases.getBillUseCase(it))
                }
            }
        }
        }

    val unpaidBills: LiveData<List<Bill>> = Transformations.map(bills){bills ->
        bills.filter{!it.settled}
    }

    val paidBills: LiveData<List<Bill>> = Transformations.map(bills){bills ->
        bills.filter{it.settled}
    }

    fun showPaid(tabText: String){
        _paid.postValue(tabText == "Paid")
    }



}