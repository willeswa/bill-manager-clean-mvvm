package app.monkpad.billmanager.presentation.newbill

//import com.google.android.gms.ads.AdRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentNewBillBinding
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.framework.models.enums.Categories
import app.monkpad.billmanager.presentation.bottom_sheets.CategoriesBottomSheet
import app.monkpad.billmanager.presentation.homescreen.HomeScreenFragment
import app.monkpad.billmanager.utils.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewBillFragment : Fragment() {
    private lateinit var binding: FragmentNewBillBinding
    private val categories by lazy {
        Categories.values()
    }

    private val viewModel: NewBillViewModel by activityViewModels()

    private var dueDate: Long? = null
    private var frequency: Long? = null
    private var category: Categories? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentNewBillBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        observeCategories()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val adRequest: AdRequest = AdRequest.Builder().build()
//        binding.adView3.loadAd(adRequest)
        var billToEdit: BillDTO? = null

        if (!HomeScreenFragment.IS_NEW_BILL) {
            val args: NewBillFragmentArgs by navArgs()
            val billId = args.billId
            binding.addNewBill.text = getString(R.string.update_bill_string)
            viewModel.getBill(billId).observe(viewLifecycleOwner) {
                populateUpdateBillFormWith(it)
                billToEdit = it
            }

        }

        binding.billRepSwitch.setOnCheckedChangeListener { _, isChecked ->
            isRepeatOn(isChecked)
        }

        binding.requencyGroup.setOnCheckedChangeListener { button, s ->
            when (button.checkedRadioButtonId) {
                R.id.monthly_radio_button -> frequency = 30
                R.id.bi_monthly_radio_button -> frequency = 14
                R.id.weekly_radio_button -> frequency = 7
            }
        }

        viewModel.showDatePicker.observe(viewLifecycleOwner) { yes ->
            if (yes) {
                val picker = Utility.getDatePicker(requireActivity().supportFragmentManager)

                picker.addOnPositiveButtonClickListener {
                    dueDate = bindDate(it)
                }

                picker.addOnNegativeButtonClickListener {
                    dueDate = bindDate()
                }
            }
            viewModel.finishShowingDatePicker()
        }

        viewModel.selectedCat.observe(viewLifecycleOwner) {
            bindCategory(it)
            category = it
        }

        viewModel.submit.observe(viewLifecycleOwner) { readyToSubmit ->
            if (readyToSubmit) {
                try {
                    val billValue = getBillValue()
                    val description = getBillDescription()
                    val dueDate = getDueDate()
                    val category = getCategory()



                    if (HomeScreenFragment.IS_NEW_BILL) {
                        val bill = Utility.makeBill(billValue,
                            description,
                            dueDate,
                            category.title,
                            frequency,
                            false)
                        viewModel.addNewBill(bill)
                    } else {
                        billToEdit?.let {
                            val bill = Utility.updateBill(it,
                                billValue,
                                description,
                                dueDate,
                                category.title,
                                frequency)
                            viewModel.updateBill(bill)
                        }
                    }
                    viewModel.finishSubmitting()
                    findNavController().popBackStack()
                } catch (e: Exception) {
                    viewModel.setError(e.message)
                }
            }
        }
    }

    private fun getDueDate(): Long {
        return dueDate ?: throw Exception("Please select a category")
    }

    private fun getCategory(): Categories {
        return category ?: throw Exception("Please set a valid due date")
    }


    @Throws(Exception::class)
    private fun getBillDescription(): String {
        val description = binding.billDescriptionEdittext.editText?.text.toString()
        return description.ifEmpty {
            throw Exception(getString(R.string.msg_empty_description_error))
        }
    }

    @Throws(Exception::class)
    private fun getBillValue(): Float {
        val billValue = binding.billValueEdittext.editText?.text.toString()
        return if (billValue.isEmpty()) {
            throw  Exception(getString(R.string.msg_error_empty_bill_value))
        } else {
            val billValueFloat = billValue.toFloat()
            if (billValueFloat <= 0) {
                throw Exception(getString(R.string.msg_bill_value_zero))
            } else {
                billValueFloat
            }
        }
    }

    private fun bindCategory(it: Categories?) {
        it?.let {
            binding.billCategoryEdittext.icon = Utility.getDrawable(requireContext(), it.drawable)
            binding.billCategoryEdittext.text = it.title
            binding.billCategoryEdittext.iconTint =
                Utility.getDrawableTint(requireContext(), it.color)
        }
    }

    private fun bindDate(it: Long? = null): Long? {
        return if (it == null) {
            binding.billDuedateEdittext.text = getString(R.string.title_select_due_date)
            null
        } else {
            if (it < System.currentTimeMillis()) {
                viewModel.setError(getString(R.string.msg_invalid_date_error))
                binding.billDuedateEdittext.text = getString(R.string.msg_invalid_date_error)
                null
            } else {
                binding.billDuedateEdittext.text = Utility.formattedDate(it)
                it
            }
        }

    }


    private fun observeCategories() {
        viewModel.showCategories.observe(viewLifecycleOwner) { yes ->
            if (yes) {
                val categoriesModal = CategoriesBottomSheet()
                activity?.supportFragmentManager?.let { categoriesModal.show(it, "") }
            }
            viewModel.finishShowingCategoriesDialog()
        }
    }


    private fun isRepeatOn(isChecked: Boolean) {
        if (isChecked) {
            binding.requencyGroup.visibility = View.VISIBLE
        } else {
            binding.requencyGroup.clearCheck()
            binding.requencyGroup.visibility = View.GONE
        }
    }

    private fun populateUpdateBillFormWith(bill: BillDTO?) {

        val switch = binding.billRepSwitch
        bill?.let {
            switch.isChecked = it.repeat != null
            val category = categories.find { category -> category.title == it.categoryName }
            this.category = category
            this.dueDate = it.dueDate
            this.frequency = it.repeat

            if (switch.isChecked) {
                binding.requencyGroup.visibility = View.VISIBLE
                binding.requencyGroup.check(Utility.whichButton(it.repeat))
            }

            binding.billValueEdittext.editText?.setText(it.amount.toString())
            binding.billDescriptionEdittext.editText?.setText(it.description)
            binding.billDuedateEdittext.text = Utility.formattedDate(it.dueDate)

            category?.let { cat ->
                binding.billCategoryEdittext.text = cat.title
                binding.billCategoryEdittext.icon =
                    Utility.getDrawable(requireContext(), cat.drawable)
                binding.billCategoryEdittext.iconTint =
                    Utility.getDrawableTint(requireContext(), cat.color)
            }

        }

    }
}