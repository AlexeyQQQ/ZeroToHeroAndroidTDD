package ru.easycode.zerotoheroandroidtdd.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.repository.ItemUi
import ru.easycode.zerotoheroandroidtdd.databinding.ItemBinding

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private var list: List<ItemUi> = ArrayList<ItemUi>()

    var itemClickListener: ((ItemUi) -> Unit)? = null

    fun update(newList: List<ItemUi>) {
        val callback = ItemDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemUi = list[position]
        holder.bind(itemUi.text)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(itemUi)
        }
    }
}

class ItemViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String) {
        binding.elementTextView.text = text
    }
}

class ItemDiffCallback(
    private val oldList: List<ItemUi>,
    private val newList: List<ItemUi>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}