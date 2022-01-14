package app.monkpad.billmanager.presentation.profilescreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import app.monkpad.billmanager.framework.BillManagerViewModel
import app.monkpad.billmanager.framework.UseCases
import app.monkpad.billmanager.framework.mappers.asPresentationModel
import app.monkpad.billmanager.framework.models.BillDTO
import kotlinx.coroutines.flow.map

class ProfileScreenViewModel(
    application: Application, useCases: UseCases) : BillManagerViewModel(application, useCases) {

    val bills: LiveData<List<BillDTO>> = useCases.getBillsUseCase().map { billList ->
        billList.map { bill -> bill.asPresentationModel() }
    }.asLiveData()

//    val xvalues:LiveData<List<String>> = Transformations.map(_bills){billsDTOs ->
//        billsDTOs.groupBy{it.categoryName}.mapValues {(category, catSum) -> catSum.sumBy{
//            it.amount
//        }}
//    }

}