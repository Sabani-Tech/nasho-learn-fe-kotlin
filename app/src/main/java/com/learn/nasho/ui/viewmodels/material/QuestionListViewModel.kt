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

    private val _questionList: MutableLiveData<ResultState<QuestionListResponse>> =
        MutableLiveData(ResultState.Loading)
    val questionList: LiveData<ResultState<QuestionListResponse>>
        get() = _questionList

    fun getQuestions() {
        viewModelScope.launch {
            materialRepository.getQuestions()
                .onStart { _questionList.postValue(ResultState.Loading) }
                .catch { exception ->
                    _questionList.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _questionList.postValue(result) }
        }
    }
}