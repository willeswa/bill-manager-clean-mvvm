package app.monkpad.billmanager.presentation.homescreen

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.R
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.utils.Utility

@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<BillDTO>?) {
    val adapter = recyclerView.adapter as HomeScreenRecyclerAdapter
    list?.let {
        adapter.setItems(list)
    }
}

@BindingAdapter("setFormattedMoney")
fun setFormattedMoney(textView: TextView, value: Float) {
    val context = textView.context
    textView.text = Utility.formattedMoney(
        context.resources.getString(
            R.string.money_value_of,
            Utility.formattedDecimal(value)
        )
    )
}


@BindingAdapter("setCategoryLogo")
fun setCategoryLogo(imageView: ImageView, bill: BillDTO) {
    val category = Utility.categories.find { it.name == bill.categoryName }
    val context = imageView.context
    val resources = context.resources
    val resourceId = resources.getIdentifier(category?.logo, "drawable", context.packageName)
    imageView.setImageResource(resourceId)
}


@BindingAdapter("setEmptyViewText")
fun setEmptyViewText(textView: TextView, unPaidList: List<BillDTO>?) {
    if (unPaidList?.size == 0) {
        textView.text =
            "Looks like you haven't created any bills. Hit the New Bill button below to add one."
    } else {
        textView.text =
            "Nothing going on here! Go ahead and pay off some bills to move them here \uD83D\uDE0A"
    }
}
