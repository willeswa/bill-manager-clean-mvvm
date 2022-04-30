package app.monkpad.billmanager.presentation.homescreen

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.R
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.framework.models.enums.Categories
import app.monkpad.billmanager.utils.Utility
import com.bumptech.glide.Glide

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


@BindingAdapter("setCategoryIcon")
fun setCategoryIcon(imageView: ImageView, categoryName: String){
    val context = imageView.context
    val category = Categories.values().find{categoryName == it.title}
    category?.let {
        Glide.with(imageView).load(category.drawable).into(imageView)
        imageView.setColorFilter(ContextCompat.getColor(context, category.color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

}

@BindingAdapter("setDrawable")
fun setDrawable(imageView: ImageView, drawable: Int) {
    Glide.with(imageView).load(drawable).into(imageView)
}


@BindingAdapter("setTint")
fun setTint(imageView: ImageView, color: Int) {
    imageView.setColorFilter(ContextCompat.getColor(imageView.context, color),
        android.graphics.PorterDuff.Mode.SRC_IN)
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
