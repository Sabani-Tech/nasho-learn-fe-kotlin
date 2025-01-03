package com.learn.nasho.ui.viewmodels.material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.CategoriesResponse
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CategoryListViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _categoryList: MutableLiveData<ResultState<CategoriesResponse>> =
        MutableLiveData(ResultState.Loading)
    val categoryList: LiveData<ResultState<CategoriesResponse>>
        get() = _categoryList

    fun getCategoryList() {
        viewModelScope.launch {
            materialRepository.getCategoryList()
                .onStart { _categoryList.postValue(ResultState.Loading) }
                .catch { exception ->
                    _categoryList.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _categoryList.postValue(result) }
        }
    }
}