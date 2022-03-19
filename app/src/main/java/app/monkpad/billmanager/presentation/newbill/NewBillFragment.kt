package app.monkpad.billmanager.presentation.newbill

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentNewBillBinding
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.framework.models.CategoryDTO
import app.monkpad.billmanager.utils.Utility
//import com.google.android.gms.ads.AdRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NewBillFragment : Fragment() {
    private lateinit var binding: FragmentNewBillBinding
    private val args: NewBillFragmentArgs by navArgs()
    private var repeat: Long? = null

    private val viewModel: NewBillViewModel by activityViewModels{
        BillManagerViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewBillBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel


        binding.billRepSwitch.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                binding.requencyGroup.visibility = View.VISIBLE
            } else {
                binding.requencyGroup.clearCheck()
                repeat = null
                binding.requencyGroup.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val adRequest: AdRequest = AdRequest.Builder().build()
//        binding.adView3.loadAd(adRequest)

        var dueDate = 0L
        var category: CategoryDTO? = null
        val paid = false

        var billToEdit: BillDTO? = null

        if(args.billId != -1){

            binding.addNewBill.text = getString(R.string.update_bill_string)
            viewModel.getBill(args.billId).observe(viewLifecycleOwner, {
                billToEdit = it
                populateFormWithBill(it)
                dueDate = it.dueDate
                category = Utility.categories.find{cat -> cat.name == it.categoryName}
                binding.billRepSwitch.isChecked = it.repeat != null
            })
        }

        viewModel.showDatePicker.observe(viewLifecycleOwner, Observer(){yes ->
            if(yes){
                val picker = Utility.getDatePicker(requireActivity().supportFragmentManager)
                Utility.listenOnDatePicker(binding, viewModel, picker)
            }
            viewModel.finishShowingDatePicker()
        })

        viewModel.dueDate.observe(viewLifecycleOwner, Observer(){timeInLong ->
            if(timeInLong != null){ dueDate = timeInLong }
        })

        viewModel.showCategories.observe(viewLifecycleOwner, Observer(){yes ->
            val categories = Utility.categories.map{it.name}.toTypedArray()
            if(yes) {
              MaterialAlertDialogBuilder(requireContext())
                  .setTitle("Select a category")
                  .setItems(categories){ _, picked ->
                      binding.billCategoryEdittext.text = Utility.categories.elementAt(picked).name
                      viewModel.setCategory(Utility.categories.elementAt(picked))
                  }
                  .show()
            }
            viewModel.finishShowingCategoriesDialog()
        })

        viewModel.category.observe(viewLifecycleOwner, Observer(){
            category = it
            viewModel.addCategoryIfDoesNotExist(it)
        })

        binding.requencyGroup.setOnCheckedChangeListener{button, _ ->
            when(button.checkedRadioButtonId){
                R.id.monthly_radio_button -> repeat = 30
                R.id.bi_monthly_radio_button -> repeat = 14
                R.id.weekly_radio_button -> repeat = 7
            }
        }

        viewModel.submit.observe(viewLifecycleOwner, Observer(){readyToSubmit ->
            if(readyToSubmit){
                val amount = binding.billValueEdittext.editText?.text.toString()
                val description = binding.billDescriptionEdittext.editText?.text.toString()

                try {
                    Utility.validateEntries(dueDate, amount, description, category)
                    if(args.billId == -1){
                        val bill = Utility.makeBill(amount.toFloat(), description, dueDate, category!!, repeat, paid)
                        viewModel.addNewBill(bill)
                    } else {
                        val bill = Utility.updateBill(billToEdit!!, amount.toFloat(), description, dueDate, category!!, repeat)
                        viewModel.updateBill(bill)
                    }
                    viewModel.finishSubmitting()
                    findNavController().popBackStack()
                } catch (e: Exception) {
                    viewModel.setError(e.message)
                }
            }
        })
    }

    private fun populateFormWithBill(bill: BillDTO?) {
        binding.billValueEdittext.editText?.setText(bill?.amount.toString())
        binding.billDescriptionEdittext.editText?.setText(bill?.description)
        binding.billDuedateEdittext.text = Utility.formattedDate(bill?.dueDate)
        binding.billCategoryEdittext.text = bill?.categoryName
        Log.i("BILL_REPEAT", ""+bill?.repeat)
        if(bill?.repeat != null){
            binding.requencyGroup.visibility = View.VISIBLE
            binding.requencyGroup.check(Utility.whichButton(bill?.repeat!!))
        }
    }
}