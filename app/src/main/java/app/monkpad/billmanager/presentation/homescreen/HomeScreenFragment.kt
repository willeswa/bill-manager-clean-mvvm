package app.monkpad.billmanager.presentation.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentHomeScreenBinding
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.presentation.interactions.BillClickListener
import app.monkpad.billmanager.utils.Utility
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout

//import com.google.android.gms.ads.AdRequest

class HomeScreenFragment : Fragment() {

    private lateinit var mainCollectionsAdapter: HomeScreenRecyclerAdapter
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var dialog: BottomSheetDialog
    private val viewModel: HomeScreenViewModel by activityViewModels {
        BillManagerViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val adRequest: AdRequest = AdRequest.Builder().build()
//        binding.adView.loadAd(adRequest)

        var togglePaid: Button?

        mainCollectionsAdapter = HomeScreenRecyclerAdapter(BillClickListener { billDTO ->
            dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme).apply {
                setContentView(R.layout.view_bill_to_edit_dialog)

                val dialogEditMenu = findViewById<ImageView>(R.id.edit_bill_menu_flow_dialog)
                val dialogTitle = findViewById<TextView>(R.id.bill_description_dialog)
                val dialogCategoryTitle = findViewById<TextView>(R.id.bill_category_dialog)
                val dialogValue = findViewById<TextView>(R.id.bill_value_dialog)
                val dialogDueDate = findViewById<TextView>(R.id.due_date_dialog)
                val dialogPaidOnContainer = findViewById<LinearLayout>(R.id.paid_on_container)
                val dialogPaidOn = findViewById<TextView>(R.id.paid_on)
                val dialogNextDueContainer = findViewById<LinearLayout>(R.id.next_due_date_container)


                dialogEditMenu?.setOnClickListener {
                    PopupMenu(requireContext(), it).apply {
                        inflate(R.menu.edit_box_diag_menu)
                        show()
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.action_diag_delete -> deletedItem(billDTO)
                                R.id.action_diag_edit -> updateBillItem(billDTO)
                                else -> false
                            }
                        }
                    }

                }

                togglePaid = findViewById(R.id.toggle_paid_dialog)


                dialogTitle?.text = billDTO.description
                dialogCategoryTitle?.text = billDTO.categoryName
                dialogValue?.text = Utility.formattedMoney(
                    resources.getString(
                        R.string.money_value_of_2,
                        Utility.formattedDecimal(billDTO.amount)
                    )
                )


                if (billDTO.paid) {
                    togglePaid?.text = "Mark as pending"
                    dialogPaidOnContainer?.visibility = View.VISIBLE
                    dialogPaidOn?.text = Utility.formattedPaidStatus(
                        resources.getString(
                            R.string.paid_on_string,
                            Utility.formattedDate(billDTO.paidOn)
                        )
                    )
                    togglePaid?.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.error_color
                        )
                    )
                    if(billDTO.nextDueDate != null){
                        dialogNextDueContainer?.visibility = View.VISIBLE
                        dialogDueDate?.text = Utility.formattedPaidStatus(
                            resources.getString(
                                R.string.next_payment_string,
                                Utility.formattedDate(billDTO.nextDueDate)
                            )
                        )
                    } else {
                        dialogNextDueContainer?.visibility = View.GONE
                    }
                } else {
                    dialogDueDate?.text = Utility.formattedPaidStatus(
                        resources.getString(
                            R.string.unpaid_diag_string,
                            Utility.formattedDate(billDTO.dueDate)
                        )
                    )
                    dialogPaidOnContainer?.visibility = View.GONE
                    togglePaid?.text = "Mark as paid"
                    togglePaid?.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.secondary_light
                        )
                    )
                }


            }
            dialog.show()

            togglePaid?.setOnClickListener { _ ->
                billDTO.paid = !billDTO.paid
                billDTO.paidOn = System.currentTimeMillis()
                viewModel.onBillStatusToggled(billDTO)
                dialog.dismiss()
            }
        })

        binding.mainScreenRecycler.adapter = mainCollectionsAdapter
        binding.viewmodel = viewModel



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.onTabItemClicked(tab?.text as String)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewModel.onTabItemClicked(tab?.text as String)
            }
        })
    }

    private fun updateBillItem(billDTO: BillDTO): Boolean {
        dialog.dismiss()
        val action = HomeScreenFragmentDirections.actionHomeNavToNewNav()
        findNavController().navigate(action)
        return true
    }

    private fun deletedItem(bill: BillDTO): Boolean {
        dialog.dismiss()
        viewModel.onBillDeleted(bill)
        val message = "Bill has been deleted!"
        Utility.notifyUser(message, requireContext())
        return true
    }

}