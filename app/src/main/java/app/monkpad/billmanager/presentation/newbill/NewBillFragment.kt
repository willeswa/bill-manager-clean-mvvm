package app.monkpad.billmanager.presentation.newbill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.monkpad.billmanager.R
import app.monkpad.billmanager.databinding.FragmentNewBillBinding


class NewBillFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentNewBillBinding.inflate(inflater, container, false)

        return binding.root
    }


}