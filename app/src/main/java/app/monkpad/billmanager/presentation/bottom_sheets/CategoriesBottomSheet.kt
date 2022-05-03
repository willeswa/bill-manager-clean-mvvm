package app.monkpad.billmanager.presentation.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.databinding.BottomsheetCategoriesBinding
import app.monkpad.billmanager.presentation.adapters.CategoriesAdapter
import app.monkpad.billmanager.presentation.interactions.CategoryItemSelect
import app.monkpad.billmanager.presentation.newbill.NewBillViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoriesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetCategoriesBinding
    private val viewModel: NewBillViewModel by activityViewModels()

    private val categoryAdapter by lazy {
        CategoriesAdapter(CategoryItemSelect {
            viewModel.setSelectedCat(it)
            dismiss()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = BottomsheetCategoriesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.recyclerView.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
        categoryAdapter.setCategories()
        return binding.root
    }
}