package com.learn.nasho.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.nasho.data.remote.dto.MaterialDto
import com.learn.nasho.databinding.ItemLayoutMaterialBinding

class MaterialAdapter(
    private var onItemClickCallback: RecyclerViewClickListener
) : RecyclerView.Adapter<MaterialAdapter.MyViewHolder>() {

    private var oldList = emptyList<MaterialDto>()
    private var lockItem = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemLayoutMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = oldList[position]

        with(holder.binding) {
            tvMaterialTitle.text = buildString {
                append("Materi ")
                append(position + 1)
            }
            tvMaterialDesc.text = data.title?.trim()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(position)
            }

            if (lockItem) {
                itemView.isEnabled = false
                llLearLock.root.visibility = View.VISIBLE
                ivLearnStatus.visibility = View.GONE
            } else {
                itemView.isEnabled = true
                llLearLock.root.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun getItem(index: Int): MaterialDto {
        return oldList[index]
    }

    fun setLockItem(lock: Boolean) {
        lockItem = lock
    }

    fun setItems(newList: List<MaterialDto>) {
        oldList = newList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemLayoutMaterialBinding) :
        RecyclerView.ViewHolder(binding.root)
}