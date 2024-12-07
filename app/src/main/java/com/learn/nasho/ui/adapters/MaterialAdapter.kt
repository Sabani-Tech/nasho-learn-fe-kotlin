package com.learn.nasho.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.nasho.data.remote.dto.MaterialDto
import com.learn.nasho.databinding.ItemLayoutMaterialBinding

class MaterialAdapter(
//    private var oldList: List<MaterialDto>,
    private var onItemClickCallback: RecyclerViewClickListener
) : RecyclerView.Adapter<MaterialAdapter.MyViewHolder>() {

    private var oldList = emptyList<MaterialDto>()
    private var lockItem = false
    private var readStep = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemLayoutMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = oldList[position]

        Log.d("MaterialAdapter", "RecyclerView Adapter set with ${oldList.size} items.")
        with(holder.binding) {
            tvMaterialTitle.text = buildString {
                append("Materi ")
                append(position + 1)
            }
            tvMaterialDesc.text = data.title

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

                if (readStep > position) {
                    ivLearnStatus.visibility = View.VISIBLE
                } else {
                    ivLearnStatus.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun getItem(index: Int): MaterialDto {
        return oldList[index]
    }

    fun setReadStepStatus(step: Int) {
        readStep = step
    }

    fun setLockItem(lock: Boolean) {
        lockItem = lock
    }

    fun setItems(newList: List<MaterialDto>) {
        oldList = newList
        notifyDataSetChanged()
    }

//    fun setItems(newList: List<MaterialDto>) {
//        Log.d("TAG", "setItems: oldList: $oldList")
//
//        // Periksa apakah ada perubahan antara oldList dan newList
//        if (oldList.isEmpty()) {
//            oldList = newList
//        }
//
//        val diffUtil = MaterialDiffCallback(oldList, newList)
//        Log.d("TAG", "setItems: diffUtil ${Gson().toJson(diffUtil)}")
//        val diffResults = DiffUtil.calculateDiff(diffUtil)
//        Log.d("TAG", "setItems: diffResults ${Gson().toJson(diffResults)}")
//        oldList = newList
//
//        diffResults.dispatchUpdatesTo(this@MaterialAdapter)  // Jika sudah benar, gunakan ini
//        // Coba gunakan notifyDataSetChanged() sementara untuk melihat pembaruan
//        notifyDataSetChanged()  // Digunakan sementara untuk debugging
//
//    }

    class MyViewHolder(val binding: ItemLayoutMaterialBinding) :
        RecyclerView.ViewHolder(binding.root)
}