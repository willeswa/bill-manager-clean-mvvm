package app.monkpad.billmanager.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import app.monkpad.billmanager.BillManagerApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BillManagerViewModel @Inject constructor(
    application: Application,
    protected val useCases:  UseCases): AndroidViewModel(application)