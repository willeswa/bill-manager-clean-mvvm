package app.monkpad.billmanager.presentation.homescreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.databinding.BillRecyclerItemBinding
import app.monkpad.billmanager.domain.models.Bill
import app.monkpad.billmanager.framework.models.BillDTO
import app.monkpad.billmanager.presentation.interactions.BillClickListener

class HomeScreenRecyclerAdapter(private val clickListener: BillClickListener):
    RecyclerView.Adapter<HomeScreenRecyclerAdapter.MainViewHolder>() {
    var bills = listOf<BillDTO>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
       val billItem = bills[position]
        holder.bind(billItem, clickListener)
    }

    override fun getItemCount() = bills.size


    fun setItems(list: List<BillDTO>) {
        bills = list
        notifyDataSetChanged()
    }


    class MainViewHolder(private val binding: BillRecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(billItem: BillDTO, clickListener: BillClickListener){
            binding.clickListener = clickListener
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