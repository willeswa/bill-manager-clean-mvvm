package app.monkpad.billmanager.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentNewBillBinding
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.framework.models.CategoryDTO
import app.monkpad.billmanager.presentation.newbill.NewBillViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

class Utility {

    companion object {
        val categories = setOf(
            CategoryDTO("category_housing", "Housing"),
            CategoryDTO("category_education", "Education"),
            CategoryDTO("category_electronics", "Electronics"),
            CategoryDTO("category_food", "Food"),
            CategoryDTO("category_health", "Health Care"),
            CategoryDTO("category_clothing", "Clothing"),
            CategoryDTO("category_investment", "Investment"),
            CategoryDTO("category_home", "Home Improvement"),
            CategoryDTO("category_transport", "Transport"),
            CategoryDTO("category_debt", "Debt"),
            CategoryDTO("category_leisure", "Leisure"),
            CategoryDTO("category_others", "Others")
        )

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
            category: CategoryDTO,
            repeat: Boolean,
            paid: Boolean
        ): BillDTO {
            val overdue = System.currentTimeMillis() > dueDate && !paid
            return BillDTO(
                description,
                amount,
                dueDate,
                category.name,
                repeat,
                paid,
                overdue
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
    }
}
