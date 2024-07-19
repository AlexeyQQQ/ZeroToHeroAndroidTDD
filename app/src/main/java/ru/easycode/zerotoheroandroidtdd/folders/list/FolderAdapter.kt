package ru.easycode.zerotoheroandroidtdd.folders.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.core.data.FolderUi
import ru.easycode.zerotoheroandroidtdd.databinding.FolderItemBinding

class FolderAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private var list: List<FolderUi> = ArrayList<FolderUi>()

    var itemClickListener: ((FolderUi) -> Unit)? = null

    fun update(newList: List<FolderUi>) {
        val callback = ItemDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = FolderItemBinding.inflate(
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
        holder.bind(itemUi.title, itemUi.notesCount)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(itemUi)
        }
    }
}

class ItemViewHolder(private val binding: FolderItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title: String, notesCount: Int) {
        binding.folderTitleTextView.text = title
        binding.folderCountTextView.text = notesCount.toString()
    }
}

class ItemDiffCallback(
    private val oldList: List<FolderUi>,
    private val newList: List<FolderUi>,
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