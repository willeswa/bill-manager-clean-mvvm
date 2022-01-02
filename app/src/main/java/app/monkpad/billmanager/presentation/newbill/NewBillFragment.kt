package app.monkpad.billmanager.presentation.newbill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentNewBillBinding
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import app.monkpad.billmanager.framework.models.CategoryDTO
import app.monkpad.billmanager.utils.Utility
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NewBillFragment : Fragment() {
    private lateinit var binding: FragmentNewBillBinding

    private val viewModel: NewBillViewModel by viewModels(){
        BillManagerViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewBillBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dueDate = 0L
        var category: CategoryDTO? = null
        val repeat = false
        val paid = false

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

        viewModel.submit.observe(viewLifecycleOwner, Observer(){readyToSubmit ->
            if(readyToSubmit){
                val amount = binding.billValueEdittext.editText?.text.toString()
                val description = binding.billDescriptionEdittext.editText?.text.toString()

                try {
                    Utility.validateEntries(dueDate, amount, description, category)
                    val bill = Utility.makeBill(amount.toFloat(), description, dueDate, category!!, repeat, paid)
                    viewModel.addNewBill(bill)
                    viewModel.finishSubmitting()
                    findNavController().popBackStack()
                } catch (e: Exception) {
                    viewModel.setError(e.message)
                }
            }
        })
    }
}