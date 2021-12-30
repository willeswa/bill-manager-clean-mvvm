package app.monkpad.billmanager.presentation.homescreen

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.R
import app.monkpad.billmanager.domain.models.Bill
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout

@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<Bill>?){
    val adapter = recyclerView.adapter as HomeScreenRecyclerAdapter
    list?.let {
        adapter.setItems(list)
    }
}


