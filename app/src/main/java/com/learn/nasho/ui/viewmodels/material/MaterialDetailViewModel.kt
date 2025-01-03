package com.learn.nasho.ui.viewmodels.material

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.MaterialDetailResponse
import com.learn.nasho.data.remote.response.MaterialsResponse
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MaterialDetailViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _materialDetail: MutableLiveData<ResultState<MaterialDetailResponse>> =
        MutableLiveData(ResultState.Loading)
    val materialDetail: LiveData<ResultState<MaterialDetailResponse>>
        get() = _materialDetail

    fun getMaterialDetail(materialId: String) {
        viewModelScope.launch {
            materialRepository.getMaterialDetail(materialId)
                .onStart { _materialDetail.postValue(ResultState.Loading) }
                .catch { exception ->
                    _materialDetail.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _materialDetail.postValue(result) }
        }
    }
}