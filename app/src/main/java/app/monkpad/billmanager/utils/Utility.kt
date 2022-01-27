package app.monkpad.billmanager.utils

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentNewBillBinding
import app.monkpad.billmanager.framework.cronjob.ScheduleRepeatingBillsWork
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.framework.models.CategoryDTO
import app.monkpad.billmanager.presentation.newbill.NewBillViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

object Utility {
    const val BILL_REMINDER_JOB = "bill_reminder_job"

    val categories = setOf(
        CategoryDTO("category_housing", "Housing"),
        CategoryDTO("category_education", "Education"),
        CategoryDTO("category_food", "Food"),
        CategoryDTO("category_utility", "Utility"),
        CategoryDTO("category_health", "Health Care"),
        CategoryDTO("category_personal", "Personal"),
        CategoryDTO("category_investment", "Investment"),
        CategoryDTO("category_transport", "Transport"),
        CategoryDTO("category_debt", "Debt"),
        CategoryDTO("category_others", "Others")
    )

    fun styleToolbar(
        destination: NavDestination,
        toolbar: MaterialToolbar,
        bottomNav: BottomNavigationView,
        context: Context
    ) {
        val resources = context.resources
        if (destination.id == R.id.new_nav || destination.id == R.id.savings_nav) {
            toolbar.setBackgroundColor(resources.getColor(R.color.primary_variant))
            toolbar.navigationIcon =
                resources.getDrawable(R.drawable.ic_twotone_keyboard_backspace_24)
            toolbar.setTitleTextColor(resources.getColor(R.color.white))
            toolbar.setNavigationIconTint(resources.getColor(R.color.white))
            bottomNav.visibility = View.GONE
        } else {
            toolbar.setBackgroundColor(resources.getColor(R.color.background_default))
            toolbar.setTitleTextColor(resources.getColor(R.color.primary_variant))
            bottomNav.visibility = View.VISIBLE

        }
    }

    fun formattedDate(it: Long?): String {
        val formatter = SimpleDateFormat("EEE, d MMM yyyy")
        return formatter.format(it)
    }

    fun getDatePicker(fragmentManager: FragmentManager): MaterialDatePicker<Long> {
        val picker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select dates")
                .build()
        picker.show(fragmentManager, "tag")
        return picker
    }

    fun makeBill(
        amount: Float,
        description: String,
        dueDate: Long,
        category: CategoryDTO,
        repeat: Long?,
        paid: Boolean
    ): BillDTO {
        Log.i("DAYS", "" + repeat)
        val overdue = System.currentTimeMillis() > dueDate && !paid
        var nextDueDate: Long? = null
        if (repeat != null) {
            nextDueDate = (dueDate + (repeat * 86400000))
        }
        return BillDTO(
            0,
            description,
            amount,
            dueDate,
            category.name,
            repeat,
            paid,
            overdue,
            nextDueDate,
            paidOn = null
        )
    }

    @Throws(Exception::class)
    fun validateEntries(
        dueDate: Long,
        amount: String,
        description: String,
        categoryName: CategoryDTO?
    ) {
        when {
            amount < 1.toString() -> throw Exception("Bill value cannot be 0 or negative")
            dueDate < System.currentTimeMillis() -> throw Exception("Please set a valid due date")
            description.isEmpty() || description.length < 2 -> throw Exception("Please provide a valid description")
            categoryName == null -> throw Exception("Please select a category")
        }
    }

    fun listenOnDatePicker(
        binding: FragmentNewBillBinding,
        viewModel: NewBillViewModel,
        picker: MaterialDatePicker<Long>
    ) {
        picker.addOnPositiveButtonClickListener {
            binding.billDuedateEdittext.text = Utility.formattedDate(it)
            viewModel.setDueDate(it)
        }
    }

    fun notifyUser(message: String, context: Context) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.show()
    }

    fun updateBill(
        billToEdit: BillDTO,
        amount: Float,
        description: String,
        dueDate: Long,
        category: CategoryDTO,
        repeat: Long?,
    ): BillDTO {
        billToEdit.description = description
        billToEdit.amount = amount
        billToEdit.dueDate = dueDate
        billToEdit.categoryName = category.name

        if (repeat != null) {
            billToEdit.nextDueDate = (dueDate + (repeat * 86400000))
        } else {
            billToEdit.nextDueDate = null
        }
        billToEdit.repeat = repeat


        return billToEdit
    }

    fun formattedDecimal(value: Float): String {
        val formatter = DecimalFormat("#,###,###.00")
        return formatter.format(value)
    }

    fun formattedMoney(text: String): Spanned {
        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun formattedPaidStatus(text: String): Spanned {
        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun whichButton(repeat: Long): Int {
        return when (repeat) {
            7L -> R.id.weekly_radio_button
            14L -> R.id.bi_monthly_radio_button
            30L -> R.id.monthly_radio_button
            else -> 0
        }
    }

    fun scheduleRepeatingBills(workManager: WorkManager) {
        val periodicRequest = PeriodicWorkRequestBuilder<ScheduleRepeatingBillsWork>(
            1,
            TimeUnit.DAYS,
            1,
            TimeUnit.HOURS
        )
            .build()
        workManager.enqueueUniquePeriodicWork(
            BILL_REMINDER_JOB,
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicRequest)
    }
}