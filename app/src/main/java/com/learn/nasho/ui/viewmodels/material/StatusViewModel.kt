package com.learn.nasho.ui.viewmodels.material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.enums.Status
import com.learn.nasho.data.remote.response.GeneralResponse
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StatusViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _status: MutableLiveData<ResultState<GeneralResponse>> =
        MutableLiveData(ResultState.Loading)
    val status: LiveData<ResultState<GeneralResponse>>
        get() = _status

    fun updateStatus(categoryId: String, status: Status) {
        viewModelScope.launch {
            materialRepository.updateStatus(categoryId, status.type)
                .onStart { _status.postValue(ResultState.Loading) }
                .catch { exception ->
                    _status.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _status.postValue(result)}
        }
    }
}