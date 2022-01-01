package app.monkpad.billmanager.presentation.homescreen

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.R
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.framework.models.BillDTO
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout

@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<BillDTO>?){
    val adapter = recyclerView.adapter as HomeScreenRecyclerAdapter
    list?.let {
        adapter.setItems(list)
    }
}

@BindingAdapter("setFloat")
fun setFloat(textView: TextView, value: Float){
    textView.text = value.toString()
}


