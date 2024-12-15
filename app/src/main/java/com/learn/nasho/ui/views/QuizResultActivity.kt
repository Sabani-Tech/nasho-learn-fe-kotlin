package com.learn.nasho.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.learn.nasho.R
import com.learn.nasho.data.remote.dto.CorrectionDto
import com.learn.nasho.databinding.ActivityQuizResultBinding
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable
import java.util.Locale

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: CorrectionDto? = intent.parcelable(Constants.CORRECTION_DATA)
        val type: String? = intent.getStringExtra(Constants.QUESTION_TYPE)


        binding.apply {
            tvResultLabel.text = String.format(Locale.getDefault(), "Hasil %s", type)

            data?.let { result ->
                tvQuizTitle.text = result.title
                tvQuizGrade.text = result.score.toString()
                tvQuizGradeMax.text = String.format(Locale.getDefault(), "/%d", result.totalScore)
                tvTrueAnswer.text =
                    String.format(Locale.getDefault(), "%d Soal", result.correctCount)
                tvFalseAnswer.text =
                    String.format(Locale.getDefault(), "%d Soal", result.incorrectCount)

                if (result.passed == true) {
                    cvResult.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@QuizResultActivity, R.color.card_correct
                        )
                    )
                    tvMotivationalSentences.text = "Kamu Hebat!"
                    tvMotivationalSentences2.text = "Tetap pertahankan semangatmu ya."
                    imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@QuizResultActivity, R.drawable.ic_quiz_success
                        )
                    )


                } else {
                    cvResult.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@QuizResultActivity, R.color.card_incorrect
                        )
                    )
                    tvMotivationalSentences.text = "Tetap Semangat!"
                    tvMotivationalSentences2.text = "Terus semangat, kamu pasti bisa."
                    imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@QuizResultActivity, R.drawable.ic_quiz_fail
                        )
                    )
                }

                btnBack.setOnClickListener { finish() }
                btnDiscussion.setOnClickListener {
                    // Request Get
                }
            }
        }
    }
}