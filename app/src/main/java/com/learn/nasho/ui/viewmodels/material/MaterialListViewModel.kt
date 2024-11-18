package com.learn.nasho.ui.viewmodels.material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.MaterialsResponse
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MaterialListViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _materialList: MutableLiveData<ResultState<MaterialsResponse>> =
        MutableLiveData(ResultState.Loading)
    val materialList: LiveData<ResultState<MaterialsResponse>>
        get() = _materialList

    fun getMaterialListByCategory(categoryId: String) {
        viewModelScope.launch {
            materialRepository.getMaterialListByCategory(categoryId)
                .onStart { _materialList.postValue(ResultState.Loading) }
                .catch { exception ->
                    _materialList.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _materialList.postValue(result) }
        }
    }
}