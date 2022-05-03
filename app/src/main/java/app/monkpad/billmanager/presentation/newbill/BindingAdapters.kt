package app.monkpad.billmanager.presentation.newbill

import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.monkpad.billmanager.utils.Utility

@BindingAdapter("setFloat")
fun setFloat(textView: TextView, value: Float){
    textView.text = value.toString()
}

@BindingAdapter(value = ["dueDate", "paid", "paidOn"], requireAll = true)
fun dynamicDates(textView: TextView, dueDate: Long, paid:Boolean, paidOn: Long?){
    if(paidOn != null && paid){
        textView.text = "Paid on ${Utility.formattedDate(paidOn)}"
    } else {
        textView.text = Utility.formattedDate(dueDate)
    }

}


@BindingAdapter("setDate")
fun setDate(textView: TextView, dateLong: Long){
    textView.text = Utility.formattedDate(dateLong)
}