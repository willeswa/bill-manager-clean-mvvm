package app.monkpad.billmanager.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


object BillManagerViewModelFactory: ViewModelProvider.Factory {
    lateinit var application: Application

    lateinit var useCases: UseCases

    fun inject(application: Application, useCases: UseCases){
        BillManagerViewModelFactory.application = application
        BillManagerViewModelFactory.useCases = useCases
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(BillManagerViewModel::class.java.isAssignableFrom(modelClass)){
            return modelClass.getConstructor(Application::class.java, UseCases::class.java)
                .newInstance(application, useCases)
        } else {
            throw IllegalStateException("ViewModel must extend BillManagerViewModel")
        }
    }

}