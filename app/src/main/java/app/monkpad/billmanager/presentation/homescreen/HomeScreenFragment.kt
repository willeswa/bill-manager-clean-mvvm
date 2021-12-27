package app.monkpad.billmanager.presentation.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.monkpad.billmanager.databinding.FragmentHomeScreenBinding

class HomeScreenFragment : Fragment() {

    private lateinit var mainCollectionsAdapter: MainRecyclerAdapter
    private lateinit var binding: FragmentHomeScreenBinding

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

        mainCollectionsAdapter = MainRecyclerAdapter()
        binding.mainScreenRecycler.adapter = mainCollectionsAdapter
    }


}