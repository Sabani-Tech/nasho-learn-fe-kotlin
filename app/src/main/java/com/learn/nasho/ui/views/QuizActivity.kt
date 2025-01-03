package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButton
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.enums.QuestionType
import com.learn.nasho.data.remote.dto.AnswerExamDto
import com.learn.nasho.data.remote.dto.AnswerKeyDto
import com.learn.nasho.data.remote.dto.AnswerQuizDto
import com.learn.nasho.data.remote.dto.CorrectionDto
import com.learn.nasho.data.remote.dto.Option
import com.learn.nasho.data.remote.dto.QuestionDto
import com.learn.nasho.data.remote.response.QuestionListResponse
import com.learn.nasho.databinding.ActivityQuizBinding
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.ui.viewmodels.material.SubmitViewModel
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable
import java.util.Locale

class QuizActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var data: List<QuestionDto>
    private var type: String? = null
    private var categoryId: String? = null
    private var materialId: String? = null
    private var phase: Int = 0

    private lateinit var factory: MaterialViewModelFactory
    private val submitViewModel: SubmitViewModel by viewModels {
        factory
    }

    private val currentIndex: MutableLiveData<Int> = MutableLiveData(0)
    private val selectedAnswer: MutableLiveData<AnswerKeyDto> = MutableLiveData(AnswerKeyDto())
    private val answerListQuiz: MutableLiveData<List<AnswerQuizDto>> = MutableLiveData(ArrayList())
    private val answerListExam: MutableLiveData<List<AnswerExamDto>> = MutableLiveData(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = MaterialViewModelFactory.getInstance(this@QuizActivity)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //disable back button
            }
        })

        val resp: QuestionListResponse? = intent.parcelable(Constants.QUESTION_DATA)
        type = intent.getStringExtra(Constants.QUESTION_TYPE)
        categoryId = intent.getStringExtra(Constants.CATEGORY_ID)
        materialId = intent.getStringExtra(Constants.MATERIAL_ID)
        phase = intent.getIntExtra(Constants.EXAM_PHASE, 0)

        resp?.let { questionList ->

            Log.d(TAG, "onCreate: data size: ${questionList.data?.size}")
            questionList.data?.let { dataQuiz ->
                data = dataQuiz
                binding.tvQuizHeader.text = type
                currentIndex.value?.let { index -> showQuestions(index) }
            }
        }

        binding.apply {
            btnOptionOne.setOnClickListener(this@QuizActivity)
            btnOptionTwo.setOnClickListener(this@QuizActivity)
            btnOptionThree.setOnClickListener(this@QuizActivity)
            btnOptionFour.setOnClickListener(this@QuizActivity)

            btnNext.setOnClickListener(this@QuizActivity)
        }

        submitViewModel.submitQuizQuestion.observe(this@QuizActivity) { resultState ->
            when (resultState) {
                is ResultState.Success -> {

                    val response = resultState.data
                    val message = response.message

                    if (response.error == true) {
                        Toast.makeText(
                            this@QuizActivity,
                            getString(R.string.submit_failed, message),
                            Toast.LENGTH_LONG
                        ).show()
                        currentIndex.value = currentIndex.value?.minus(1)
                    } else {
                        Log.d(TAG, "onCreate: message: $message")
                        val correctionData = response.data!!
                        type?.let { typeQuestion ->
                            goToQuizResult(typeQuestion, correctionData)
                        }
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(
                        this@QuizActivity,
                        getString(R.string.submit_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                    currentIndex.value = currentIndex.value?.minus(1)
                }

                else -> {}
            }
        }

        submitViewModel.submitExamQuestion.observe(this@QuizActivity) { resultState ->
            when (resultState) {
                is ResultState.Success -> {

                    val response = resultState.data
                    val message = response.message

                    if (response.error == true) {
                        Toast.makeText(
                            this@QuizActivity,
                            getString(R.string.submit_failed, message),
                            Toast.LENGTH_LONG
                        ).show()
                        currentIndex.value = currentIndex.value?.minus(1)
                    } else {
                        Log.d(TAG, "onCreate: message: $message")
                        val correctionData = response.data!!
                        type?.let { typeQuestion ->
                            goToQuizResult(typeQuestion, correctionData)
                        }
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(
                        this@QuizActivity,
                        getString(R.string.submit_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                    currentIndex.value = currentIndex.value?.minus(1)
                }

                else -> {}
            }
        }
    }

    override fun onClick(view: View) {
        val clickedButton = view as MaterialButton

        binding.apply {

            setButtonOptionDeactivate(btnOptionOne)
            setButtonOptionDeactivate(btnOptionTwo)
            setButtonOptionDeactivate(btnOptionThree)
            setButtonOptionDeactivate(btnOptionFour)
            setButtonNextDisable(btnNext)

            if (clickedButton.id == R.id.btn_next) {
                saveCurrentAnswer()
                currentIndex.value = currentIndex.value?.plus(1)
                currentIndex.value?.let { index -> showQuestions(index) }
            } else {
                selectedAnswer.value = parseButtonTextToOption(clickedButton.text.toString())
                setButtonOptionActivate(clickedButton)
                setButtonNextEnable(btnNext)
            }
        }
    }

    private fun showQuestions(index: Int) {
        binding.apply {

            if (index >= data.size) {
                finishQuiz()
                return
            } else {
                val currentData = data[index]
                tvQuizTitle.text = currentData.title
                tvQuestPage.text =
                    String.format(Locale.getDefault(), "Soal %s/%s", (index + 1), data.size)
                tvSoalQuiz.text = currentData.question
                tvPoint.text = String.format(Locale.getDefault(), "%d Point", currentData.point)

                btnOptionOne.text = currentData.option?.let { options ->
                    getCurrentOption(options, 0)
                }
                btnOptionTwo.text = currentData.option?.let { options ->
                    getCurrentOption(options, 1)
                }
                btnOptionThree.text = currentData.option?.let { options ->
                    getCurrentOption(options, 2)
                }
                btnOptionFour.text = currentData.option?.let { options ->
                    getCurrentOption(options, 3)
                }

                if ((currentIndex.value?.plus(1)) == data.size) {
                    btnNext.text = getString(R.string.finish)
                }
            }
        }
    }


    private fun getCurrentOption(options: List<Option>, index: Int): String {
        return "${options[index].key}. ${options[index].value}"
    }

    private fun finishQuiz() {
        type?.let { typeQuestion ->
            when (typeQuestion) {
                QuestionType.QUIZ.type -> {
                    answerListQuiz.value?.let { data ->
                        submitViewModel.submitQuiz(categoryId!!, materialId!!, data)
                    }
                }

                QuestionType.EXAM.type -> {
                    answerListExam.value?.let { data ->
                        submitViewModel.submitExam(categoryId!!, phase, data)
                    }
                }

                else -> {}
            }
        }
    }

    private fun goToQuizResult(type: String, data: CorrectionDto) {
        val intent = Intent(this@QuizActivity, QuizResultActivity::class.java)
        intent.putExtra(Constants.CORRECTION_DATA, data)
        intent.putExtra(Constants.QUESTION_TYPE, type)
        intent.putExtra(Constants.CATEGORY_ID, categoryId)
        intent.putExtra(Constants.MATERIAL_ID, materialId)
        intent.putExtra(Constants.EXAM_PHASE, phase)
        startActivity(intent)
        finish()
    }

    private fun parseButtonTextToOption(buttonText: String): AnswerKeyDto {
        val parts = buttonText.split(". ", limit = 2)
        return if (parts.size == 2) {
            AnswerKeyDto(
                key = parts[0].trim(),
            )
        } else {
            AnswerKeyDto()
        }
    }

    private fun saveCurrentAnswer() {
        currentIndex.value?.let { index ->
            val currentData = data[index]
            selectedAnswer.value?.let { selected ->

                type?.let { typeQuestion ->
                    when (typeQuestion) {
                        QuestionType.QUIZ.type -> {
                            answerListQuiz.value?.let {
                                val answer = AnswerQuizDto(
                                    id = currentData.id,
                                    point = currentData.point,
                                    batch = currentData.batch,
                                    answer = selected
                                )
                                addAnswerQuizToList(answer)
                            }
                        }

                        QuestionType.EXAM.type -> {
                            answerListExam.value?.let {
                                val answer = AnswerExamDto(
                                    id = currentData.id,
                                    point = currentData.point,
                                    batch = currentData.batch,
                                    answer = selected
                                )
                                addAnswerExamToList(answer)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun addAnswerQuizToList(answer: AnswerQuizDto) {
        val currentList = answerListQuiz.value ?: emptyList()
        val updatedList = currentList.toMutableList()
        updatedList.add(answer)
        answerListQuiz.value = updatedList
    }

    private fun addAnswerExamToList(answer: AnswerExamDto) {
        val currentList = answerListExam.value ?: emptyList()
        val updatedList = currentList.toMutableList()
        updatedList.add(answer)
        answerListExam.value = updatedList
    }

    private fun setButtonNextEnable(button: MaterialButton) {
        button.apply {
            isEnabled = true
            setBackgroundColor(
                ContextCompat.getColor(
                    this@QuizActivity, R.color.green_main_color
                )
            )
            setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.white))
        }
    }

    private fun setButtonNextDisable(button: MaterialButton) {
        button.apply {
            isEnabled = false
            setBackgroundColor(
                ContextCompat.getColor(
                    this@QuizActivity, R.color.Gray100
                )
            )
            setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.Gray300))
        }
    }

    private fun setButtonOptionActivate(button: MaterialButton) {
        button.apply {
            setBackgroundColor(
                ContextCompat.getColor(this@QuizActivity, R.color.option_activate_selected)
            )
            setStrokeColorResource(R.color.option_activate_stroke_selected)
            setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.option_activate_text))
        }
    }

    private fun setButtonOptionDeactivate(button: MaterialButton) {
        button.apply {
            setBackgroundColor(
                ContextCompat.getColor(this@QuizActivity, R.color.option_deactivate_selected)
            )
            setStrokeColorResource(R.color.option_deactivate_stroke_selected)
            setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.option_deactivate_text))
        }
    }

    companion object {
        var TAG: String = QuizActivity::class.java.name
    }
}