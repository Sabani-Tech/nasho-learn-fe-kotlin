package com.learn.nasho.ui.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.databinding.ActivityQuizBinding
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.ui.viewmodels.material.QuestionListViewModel
import com.learn.nasho.utils.Constants

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var factory: MaterialViewModelFactory
    private val questionListViewModel: QuestionListViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = MaterialViewModelFactory.getInstance(this@QuizActivity)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type: String? = intent.getStringExtra(Constants.QUESTION_TYPE)
        val phase: Int = intent.getIntExtra(Constants.EXAM_PHASE, 0)

        Log.d(TAG, "onCreate: type: $type & phase: $phase")

        questionListViewModel.getQuestions()

        questionListViewModel.questionList.observe(this@QuizActivity) { resultState ->
            when (resultState) {
                is ResultState.Success -> {

                    val response = resultState.data
                    val message = response.message

                    if (response.error == true) {
                        Toast.makeText(
                            this@QuizActivity,
                            getString(R.string.update_status_failed, message),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.d(TAG, "onCreate: questions size: ${response.data?.size}")
//                        showQuestions(response)
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(
                        this@QuizActivity,
                        getString(R.string.update_status_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }
    }

//    private fun showQuestions(index: Int){
//        binding.tvQuestPage.text = "Soal ${index + 1}/${dataQuiz?.data?.size}"
//        dataQuiz?.let{
//            listOpsi = it.data[index].pilihan
//            binding.tvSoalQuiz.text = it.data[index].soal
//            binding.tvQuizTitle.text = it.data[index].nama
//            adapterListOpsi.submitList(listOpsi)
//            idSoal = it.data[index].soalId
//        }
//    }

    companion object {
        var TAG: String = QuizActivity::class.java.name
    }
}