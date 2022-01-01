package app.monkpad.billmanager.presentation.newbill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.monkpad.billmanager.databinding.FragmentNewBillBinding
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import app.monkpad.billmanager.utils.Utility

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
        val categoryName = "Food"
        val categoryLogo = "dummy"
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

        viewModel.submit.observe(viewLifecycleOwner, Observer(){readyToSubmit ->
            if(readyToSubmit){
                val amount = binding.billValueEdittext.editText?.text.toString()
                val description = binding.billDescriptionEdittext.editText?.text.toString()
                try {
                    Utility.validateEntries(dueDate, amount, description)
                    val bill = Utility.makeBill(amount.toFloat(), description, dueDate, categoryName, categoryLogo, repeat, paid)
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