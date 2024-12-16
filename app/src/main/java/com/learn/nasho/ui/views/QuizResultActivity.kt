package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.enums.QuestionType
import com.learn.nasho.data.remote.dto.CorrectionDto
import com.learn.nasho.data.remote.dto.QuizDiscussionDto
import com.learn.nasho.data.remote.response.QuizDiscussionResponse
import com.learn.nasho.databinding.ActivityQuizResultBinding
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.ui.viewmodels.material.QuestionDiscussionViewModel
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable
import java.util.Locale

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding
    private lateinit var factory: MaterialViewModelFactory
    private val questionDiscussionViewModel: QuestionDiscussionViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: CorrectionDto? = intent.parcelable(Constants.CORRECTION_DATA)
        val type = intent.getStringExtra(Constants.QUESTION_TYPE)
        val categoryId = intent.getStringExtra(Constants.CATEGORY_ID)
        val materialId = intent.getStringExtra(Constants.MATERIAL_ID)
        val phase = intent.getIntExtra(Constants.EXAM_PHASE, 0)

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
                    type?.let { typeQuestion ->
                        when (typeQuestion) {
                            QuestionType.QUIZ.type -> {
                                questionDiscussionViewModel.getQuizDiscussion(
                                    categoryId!!, materialId!!
                                )

                            }

                            QuestionType.EXAM.type -> {
                                questionDiscussionViewModel.getExamDiscussion(categoryId!!, phase)
                            }

                            else -> {}
                        }
                    }
                }
            }
        }

        questionDiscussionViewModel.questionQuizDiscussion.observe(this@QuizResultActivity) { resultState ->
            when (resultState) {
                is ResultState.Success -> {

                    val response = resultState.data
                    val message = response.message

                    if (response.error == true) {
                        Toast.makeText(
                            this@QuizResultActivity,
                            getString(R.string.discussion_failed, message),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.d(TAG, "onCreate: message: $message")
                        type?.let { typeData ->
                            goToDiscussionPage(typeData, response)
                        }
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(
                        this@QuizResultActivity,
                        getString(R.string.discussion_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }

        questionDiscussionViewModel.questionExamDiscussion.observe(this@QuizResultActivity) { resultState ->
            when (resultState) {
                is ResultState.Success -> {

                    val response = resultState.data
                    val message = response.message

                    if (response.error == true) {
                        Toast.makeText(
                            this@QuizResultActivity,
                            getString(R.string.discussion_failed, message),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.d(TAG, "onCreate: message: $message")
                        type?.let { typeData ->
                            goToDiscussionPage(typeData, response)
                        }
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(
                        this@QuizResultActivity,
                        getString(R.string.discussion_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }
    }

    private fun goToDiscussionPage(type: String, data: QuizDiscussionResponse) {
        val intent = Intent(this@QuizResultActivity, QuizDiscussionActivity::class.java)
        intent.putExtra(Constants.DISCUSSION_DATA, data)
        intent.putExtra(Constants.QUESTION_TYPE, type)
        startActivity(intent)
        finish()
    }

    companion object {
        var TAG: String = QuizResultActivity::class.java.name
    }
}