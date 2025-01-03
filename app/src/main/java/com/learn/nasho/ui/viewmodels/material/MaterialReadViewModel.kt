package com.learn.nasho.ui.viewmodels.material

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.repository.MaterialRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MaterialReadViewModel(private val materialRepository: MaterialRepository) : ViewModel() {

    private val _setMaterialStatus = MutableLiveData<Boolean>()
    val setMaterialStatus: LiveData<Boolean>
        get() = _setMaterialStatus

    fun setMaterialReadStep(materialNumber: Int, step: Int) {
        viewModelScope.launch {
            materialRepository.setMaterialReadStep(materialNumber, step)
                .catch {
                    _setMaterialStatus.value = false
                }
                .collect { success ->
                    _setMaterialStatus.value = success
                    Log.d("MaterialReadViewModel", "setMaterialReadStep: status: $success")
                }
        }
    }

    fun getMaterialReadStep(materialNumber: Int): LiveData<Int> {
        return materialRepository.getMaterialReadStep(materialNumber).asLiveData()
    }
}