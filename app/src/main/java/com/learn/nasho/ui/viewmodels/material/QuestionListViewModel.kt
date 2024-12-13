package com.learn.nasho.ui.viewmodels.material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.QuestionListResponse
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QuestionListViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _questionExamList: MutableLiveData<ResultState<QuestionListResponse>> =
        MutableLiveData(ResultState.Loading)
    val questionExamList: LiveData<ResultState<QuestionListResponse>>
        get() = _questionExamList

    private val _questionQuizList: MutableLiveData<ResultState<QuestionListResponse>> =
        MutableLiveData(ResultState.Loading)
    val questionQuizList: LiveData<ResultState<QuestionListResponse>>
        get() = _questionQuizList

    fun getExamQuestions(categoryId: String, phase: Int) {
        viewModelScope.launch {
            materialRepository.getExamQuestions(categoryId, phase)
                .onStart { _questionExamList.postValue(ResultState.Loading) }
                .catch { exception ->
                    _questionExamList.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _questionExamList.postValue(result) }
        }
    }

    fun getQuizQuestions(categoryId: String, materialId: String) {
        viewModelScope.launch {
            materialRepository.getQuizQuestions(categoryId, materialId)
                .onStart { _questionQuizList.postValue(ResultState.Loading) }
                .catch { exception ->
                    _questionQuizList.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _questionQuizList.postValue(result) }
        }
    }
}