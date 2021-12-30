package app.monkpad.billmanager.presentation.homescreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.databinding.BillRecyclerItemBinding
import app.monkpad.billmanager.domain.models.Bill

class HomeScreenRecyclerAdapter: RecyclerView.Adapter<HomeScreenRecyclerAdapter.MainViewHolder>() {
    var bills = listOf<Bill>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
       val billItem = bills[position]
        holder.bind(billItem)
    }

    override fun getItemCount() = bills.size


    fun setItems(list: List<Bill>) {
        bills = list
        notifyDataSetChanged()
    }


    class MainViewHolder(private val binding: BillRecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(billItem: Bill){
            binding.bill = billItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = BillRecyclerItemBinding.inflate(inflater, parent, false)
                return MainViewHolder(binding)
            }
        }
    }

}