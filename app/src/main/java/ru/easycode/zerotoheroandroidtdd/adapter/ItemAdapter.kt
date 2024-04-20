package ru.easycode.zerotoheroandroidtdd.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.easycode.zerotoheroandroidtdd.databinding.ItemBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var list: List<CharSequence> = ArrayList<CharSequence>()

    fun update(newList: List<CharSequence>) {
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
        val text = list[position]
        holder.bind(text.toString())
    }


    class ItemViewHolder(private val binding: ItemBinding) : ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.elementTextView.text = text
        }
    }
}