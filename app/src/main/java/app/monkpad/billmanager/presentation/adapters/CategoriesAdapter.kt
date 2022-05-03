package app.monkpad.billmanager.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.monkpad.billmanager.databinding.ItemCategoryRecyclerBinding
import app.monkpad.billmanager.framework.models.enums.Categories
import app.monkpad.billmanager.presentation.interactions.CategoryItemSelect

class CategoriesAdapter(private val callback: CategoryItemSelect): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private var categories = listOf<Categories>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder =
        CategoriesViewHolder.from(parent)

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
       val item = categories[position]
        holder.bind(item, callback)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setCategories(){
        val cats = Categories.values()
        categories = cats.toList()
        notifyDataSetChanged()
    }


    class CategoriesViewHolder(val binding: ItemCategoryRecyclerBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Categories, callback: CategoryItemSelect) {
            binding.item = item
            binding.callback = callback
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): CategoriesViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryRecyclerBinding.inflate(inflater, parent, false)
                return CategoriesViewHolder(binding)
            }
        }
    }
}