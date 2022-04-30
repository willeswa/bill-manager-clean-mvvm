package app.monkpad.billmanager.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentManager
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentNewBillBinding
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.presentation.newbill.NewBillViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DecimalFormat
import java.text.SimpleDateFormat

object Utility {
    const val BILL_REMINDER_JOB = "bill_reminder_job"


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
        category: String,
        repeat: Long?,
        paid: Boolean,
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
            category,
            repeat,
            paid,
            overdue,
            nextDueDate,
            paidOn = null
        )
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
        category: String,
        repeat: Long?,
    ): BillDTO {
        billToEdit.description = description
        billToEdit.amount = amount
        billToEdit.dueDate = dueDate
        billToEdit.categoryName = category

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

    fun whichButton(repeat: Long?): Int {
        return when (repeat) {
            7L -> R.id.weekly_radio_button
            14L -> R.id.bi_monthly_radio_button
            30L -> R.id.monthly_radio_button
            else -> 0
        }
    }

    fun getDrawable(context: Context, drawable: Int): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, drawable, null)
    }

    fun getDrawableTint(context: Context, tint: Int): ColorStateList? {
        return  ResourcesCompat.getColorStateList(context.resources, tint, null)
    }

//    fun scheduleRepeatingBills(workManager: WorkManager) {
//        val periodicRequest = PeriodicWorkRequestBuilder<ScheduleRepeatingBillsWork>(
//            1,
//            TimeUnit.DAYS,
//            1,
//            TimeUnit.HOURS
//        )
//            .build()
//        workManager.enqueueUniquePeriodicWork(
//            BILL_REMINDER_JOB,
//            ExistingPeriodicWorkPolicy.REPLACE,
//            periodicRequest)
//    }
}