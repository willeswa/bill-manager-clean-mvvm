package app.monkpad.billmanager.presentation.newbill

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.monkpad.billmanager.framework.BillManagerViewModel
import app.monkpad.billmanager.framework.UseCases
import app.monkpad.billmanager.framework.mappers.asDomainModel
import app.monkpad.billmanager.framework.mappers.asPresentationModel
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.framework.models.CategoryDTO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map

class NewBillViewModel(application: Application, useCases: UseCases):
    BillManagerViewModel(application, useCases) {

    private val _showDatePicker = MutableLiveData(false)
    val showDatePicker: LiveData<Boolean> = _showDatePicker

    private val _submit = MutableLiveData(false)
    val submit: LiveData<Boolean> = _submit

    private val _dueDate = MutableLiveData<Long>()
    val dueDate: LiveData<Long> = _dueDate

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val _showCategories = MutableLiveData(false)
    val showCategories: LiveData<Boolean> = _showCategories
    
    private val _category = MutableLiveData<CategoryDTO>()
    val category: LiveData<CategoryDTO> = _category

    fun startShowingDatePicker(){
        _showDatePicker.postValue(true)
    }

    fun finishShowingDatePicker(){
        _showDatePicker.postValue(false)
    }

    fun startShowingCategoriesDialog(){
        _showCategories.postValue(true)
    }

    fun finishShowingCategoriesDialog(){
        _showCategories.postValue(false)
    }

    fun addCategoryIfDoesNotExist(category: CategoryDTO){
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                useCases.addCategoryUseCase(category.asDomainModel())
            }
        }
    }
    
    fun setCategory(category: CategoryDTO){
        _category.postValue(category)
    }

    fun addNewBill(bill: BillDTO){
        Log.i("viewmodel_say_some", ""+bill.repeat)
        coroutineScope.launch{
            withContext(Dispatchers.IO){
                 useCases.addBillUseCase(bill.asDomainModel())
            }
        }
    }

    fun startSubmitting(){
        _submit.postValue(true)
    }

    fun finishSubmitting(){
        _submit.postValue(false)
    }

    fun setDueDate(it: Long) {
        _dueDate.postValue(it)
    }

    fun setError(error: String?) {
        _error.postValue(error)
        coroutineScope.launch{
            delay(2000)
            _error.postValue(null)
        }
    }

    fun getBill(id: Int): LiveData<BillDTO> =
        useCases.getBillUseCase(id).map{it.asPresentationModel()}.asLiveData()

    fun updateBill(bill: BillDTO) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                useCases.updateBillUseCase(bill.asDomainModel())
            }
        }
    }
}