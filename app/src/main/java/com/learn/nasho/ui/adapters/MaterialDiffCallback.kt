package com.learn.nasho.ui.adapters

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.learn.nasho.data.remote.dto.MaterialDto

class MaterialDiffCallback (
    private val oldList: List<MaterialDto>,
    private val newList: List<MaterialDto>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        Log.d("MaterialAdapter", "Comparing items: ${oldItem.id} vs ${newItem.id}")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        Log.d("MaterialAdapter", "Comparing contents: $oldItem vs $newItem")
        return oldItem == newItem
    }
}