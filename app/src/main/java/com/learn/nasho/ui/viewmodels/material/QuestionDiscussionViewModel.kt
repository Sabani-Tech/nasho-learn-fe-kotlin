package com.learn.nasho.ui.viewmodels.material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.QuizDiscussionResponse
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QuestionDiscussionViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _questionExamDiscussion: MutableLiveData<ResultState<QuizDiscussionResponse>> =
        MutableLiveData(ResultState.Loading)
    val questionExamDiscussion: LiveData<ResultState<QuizDiscussionResponse>>
        get() = _questionExamDiscussion

    private val _questionQuizDiscussion: MutableLiveData<ResultState<QuizDiscussionResponse>> =
        MutableLiveData(ResultState.Loading)
    val questionQuizDiscussion: LiveData<ResultState<QuizDiscussionResponse>>
        get() = _questionQuizDiscussion

    fun getExamDiscussion(categoryId: String, phase: Int) {
        viewModelScope.launch {
            materialRepository.getExamDiscussion(categoryId, phase)
                .onStart { _questionExamDiscussion.postValue(ResultState.Loading) }
                .catch { exception ->
                    _questionExamDiscussion.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _questionExamDiscussion.postValue(result) }
        }
    }

    fun getQuizDiscussion(categoryId: String, materialId: String) {
        viewModelScope.launch {
            materialRepository.getQuizDiscussion(categoryId, materialId)
                .onStart { _questionQuizDiscussion.postValue(ResultState.Loading) }
                .catch { exception ->
                    _questionQuizDiscussion.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _questionQuizDiscussion.postValue(result) }
        }
    }
}