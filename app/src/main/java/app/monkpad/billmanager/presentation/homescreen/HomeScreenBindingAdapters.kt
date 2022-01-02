package app.monkpad.billmanager.presentation.homescreen

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.R
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.utils.Utility
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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


@BindingAdapter("setCategoryLogo")
fun setCategoryLogo(imageView: ImageView, bill: BillDTO){
    val category = Utility.categories.find { it.name == bill.categoryName }
    val context = imageView.context
    val resources = context.resources
    val resourceId = resources.getIdentifier(category?.logo, "drawable", context.packageName)
    imageView.setImageResource(resourceId)
}

