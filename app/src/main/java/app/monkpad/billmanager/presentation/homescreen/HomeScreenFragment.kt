package app.monkpad.billmanager.presentation.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import app.monkpad.billmanager.databinding.FragmentHomeScreenBinding
import app.monkpad.billmanager.framework.BillManagerViewModelFactory
import com.google.android.material.tabs.TabLayout

class HomeScreenFragment : Fragment() {

    private lateinit var mainCollectionsAdapter: HomeScreenRecyclerAdapter
    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel: HomeScreenViewModel by viewModels{
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

        mainCollectionsAdapter = HomeScreenRecyclerAdapter()
        binding.mainScreenRecycler.adapter = mainCollectionsAdapter
        binding.viewmodel = viewModel
        mainCollectionsAdapter.notifyDataSetChanged()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
              viewModel.showPaid(tab?.text as String)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
               tab?.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewModel.showPaid(tab?.text as String)
            }
        })
    }
}