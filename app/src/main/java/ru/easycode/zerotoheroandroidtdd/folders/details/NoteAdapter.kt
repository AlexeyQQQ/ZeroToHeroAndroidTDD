package ru.easycode.zerotoheroandroidtdd.folders.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.core.data.NoteUi
import ru.easycode.zerotoheroandroidtdd.databinding.NoteItemBinding

class NoteAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private var list: List<NoteUi> = ArrayList<NoteUi>()

    var itemClickListener: ((NoteUi) -> Unit)? = null

    fun update(newList: List<NoteUi>) {
        val callback = ItemDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = NoteItemBinding.inflate(
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
        holder.bind(itemUi.title)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(itemUi)
        }
    }
}

class ItemViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title: String) {
        binding.noteTitleTextView.text = title
    }
}

class ItemDiffCallback(
    private val oldList: List<NoteUi>,
    private val newList: List<NoteUi>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}