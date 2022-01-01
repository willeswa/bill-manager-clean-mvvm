package app.monkpad.billmanager.presentation.newbill

import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.monkpad.billmanager.utils.Utility

@BindingAdapter("setFloat")
fun setFloat(textView: TextView, value: Float){
    textView.text = value.toString()
}

@BindingAdapter("setDate")
fun setDate(textView: TextView, dateLong: Long){
    textView.text = Utility.formattedDate(dateLong)
}