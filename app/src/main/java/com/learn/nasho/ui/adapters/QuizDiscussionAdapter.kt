package com.learn.nasho.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.learn.nasho.R
import com.learn.nasho.data.remote.dto.QuizDiscussionDto
import com.learn.nasho.databinding.ItemLayoutDiscussionBinding
import java.util.Locale

class QuizDiscussionAdapter(private var disscussionData: List<QuizDiscussionDto>) :
    RecyclerView.Adapter<QuizDiscussionAdapter.MyViewHolder>() {

//    private var oldList = emptyList<QuizDiscussionDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemLayoutDiscussionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = disscussionData[position]
        with(holder.binding) {
            tvNo.text = String.format(Locale.getDefault(), "No. %d", (position + 1))
            tvPoint.text = String.format(Locale.getDefault(), "%d poin", data.point)
            tvQuestion.text = data.question

            tvRightAnswerKey.text = data.rightAnswer?.key
            tvRightAnswerValue.text = data.rightAnswer?.value

            val isCorrect = data.point!! > 0
            tvAnswer.text =
                String.format(Locale.getDefault(), "%s. %s", data.answer?.key, data.answer?.value)
            tvCorrect.text = if (isCorrect) "Benar" else "Salah"
            tvPraise.text = if (isCorrect) "Kamu hebat!" else "Masih  belum tepat nih."
            tvPraiseOther.text =
                if (isCorrect) "Terus pertahankan ya" else "Tetap berusaha dan tetap semangat"

            val imagePraise = if (isCorrect) {
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_benar)
            } else {
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_salah)
            }
            ivPraise.setImageDrawable(imagePraise)

            val cvAnswerBackgroundColor = if (isCorrect) {
                ContextCompat.getColor(holder.itemView.context, R.color.card_correct)
            } else {
                ContextCompat.getColor(holder.itemView.context, R.color.card_incorrect)
            }

            val cvPraiseBackgroundColor = if (isCorrect) {
                ContextCompat.getColor(holder.itemView.context, R.color.icon_correct)
            } else {
                ContextCompat.getColor(holder.itemView.context, R.color.icon_incorrect)
            }

            val textBackgroundColor = if (isCorrect) {
                ContextCompat.getColor(holder.itemView.context, R.color.text_correct)
            } else {
                ContextCompat.getColor(holder.itemView.context, R.color.text_incorrect)
            }

            cvAnswer.setCardBackgroundColor(cvAnswerBackgroundColor)
            cvPraise.setCardBackgroundColor(cvPraiseBackgroundColor)
            tvPoint.setTextColor(textBackgroundColor)
            tvAnswerLabel.setTextColor(textBackgroundColor)
            tvCorrect.setTextColor(textBackgroundColor)
            tvAnswer.setTextColor(textBackgroundColor)
            tvPraise.setTextColor(textBackgroundColor)

        }
    }

    override fun getItemCount(): Int {
        return disscussionData.size
    }

    fun getItem(index: Int): QuizDiscussionDto {
        return disscussionData[index]
    }

//    fun setItems(newList: List<QuizDiscussionDto>) {
//        oldList = newList
//        notifyDataSetChanged()
//    }

    class MyViewHolder(val binding: ItemLayoutDiscussionBinding) :
        RecyclerView.ViewHolder(binding.root)
}