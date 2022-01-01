package app.monkpad.billmanager.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentNewBillBinding
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.presentation.newbill.NewBillViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

class Utility  {

    companion object {
        fun styleToolbar(
            destination: NavDestination,
            toolbar: MaterialToolbar,
            bottomNav: BottomNavigationView,
            context: Context
        ) {
            val resources = context.resources
            if (destination.id == R.id.new_nav) {
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
            categoryName: String,
            categoryLogo: String,
            repeat: Boolean,
            paid: Boolean
        ): BillDTO {
            val overdue = System.currentTimeMillis() > dueDate && !paid
            return BillDTO(
                description,
                amount,
                dueDate,
                categoryName,
                categoryLogo,
                repeat,
                paid,
                overdue)
        }

        @Throws(Exception::class)
        fun validateEntries (
            dueDate: Long,
            amount: String,
            description: String)  {
            when {
                amount < 1.toString() -> throw Exception("Bill value cannot be 0 or negative")
                dueDate < System.currentTimeMillis() -> throw Exception("Please set a valid due date")
                description.isEmpty() -> throw Exception("Description cannot be empty")
            }
        }

        fun listenOnDatePicker(binding: FragmentNewBillBinding, viewModel: NewBillViewModel, picker: MaterialDatePicker<Long>) {
            picker.addOnPositiveButtonClickListener {
                binding.billDuedateEdittext.text = Utility.formattedDate(it)
                viewModel.setDueDate(it)
            }
        }
    }
}
