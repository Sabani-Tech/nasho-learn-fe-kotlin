package com.learn.nasho.ui.viewmodels.material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.dto.AnswerExamDto
import com.learn.nasho.data.remote.dto.AnswerQuizDto
import com.learn.nasho.data.remote.response.CorrectionResponse
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SubmitViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _submitExamQuestion: MutableLiveData<ResultState<CorrectionResponse>> =
        MutableLiveData(ResultState.Loading)
    val submitExamQuestion: LiveData<ResultState<CorrectionResponse>>
        get() = _submitExamQuestion

    private val _submitQuizQuestion: MutableLiveData<ResultState<CorrectionResponse>> =
        MutableLiveData(ResultState.Loading)
    val submitQuizQuestion: LiveData<ResultState<CorrectionResponse>>
        get() = _submitQuizQuestion

    fun submitExam(categoryId: String, phase: Int, exam: List<AnswerExamDto>) {
        viewModelScope.launch {
            materialRepository.submitExam(categoryId, phase, exam)
                .onStart { _submitExamQuestion.postValue(ResultState.Loading) }
                .catch { exception ->
                    _submitExamQuestion.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _submitExamQuestion.postValue(result) }
        }
    }

    fun submitQuiz(categoryId: String, materialId: String, quiz: List<AnswerQuizDto>) {
        viewModelScope.launch {
            materialRepository.submitQuiz(categoryId, materialId, quiz)
                .onStart { _submitQuizQuestion.postValue(ResultState.Loading) }
                .catch { exception ->
                    _submitQuizQuestion.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _submitQuizQuestion.postValue(result) }
        }
    }
}