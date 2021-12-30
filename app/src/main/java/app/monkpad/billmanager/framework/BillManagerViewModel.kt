package app.monkpad.billmanager.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import app.monkpad.billmanager.BillManagerApplication

open class BillManagerViewModel(
    application: Application,
    protected val useCases:  UseCases): AndroidViewModel(application){
        protected val application: BillManagerApplication = getApplication()
    }