package com.learn.nasho.ui.viewmodels.material

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.CategoryDetailResponse
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CategoryDetailViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _categoryDetail: MutableLiveData<ResultState<CategoryDetailResponse>> =
        MutableLiveData(ResultState.Loading)
    val categoryDetail: LiveData<ResultState<CategoryDetailResponse>>
        get() = _categoryDetail

    fun getCategoryDetailById(categoryId: String) {
        viewModelScope.launch {
            materialRepository.getCategoryDetailById(categoryId)
                .onStart { _categoryDetail.postValue(ResultState.Loading) }
                .catch { exception ->
                    _categoryDetail.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _categoryDetail.postValue(result)}
        }
    }
}