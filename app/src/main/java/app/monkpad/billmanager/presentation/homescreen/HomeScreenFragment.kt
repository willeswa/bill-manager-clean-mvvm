package app.monkpad.billmanager.presentation.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentHomeScreenBinding


class HomeScreenFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


}