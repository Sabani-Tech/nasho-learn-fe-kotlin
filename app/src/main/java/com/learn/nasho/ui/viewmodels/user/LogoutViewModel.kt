package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.repository.UserRepository
import kotlinx.coroutines.launch

class LogoutViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _logout: MutableLiveData<Boolean?> = MutableLiveData()
    val logout: LiveData<Boolean?>
        get() = _logout

    fun logoutUser() {
        viewModelScope.launch {
            userRepository.clearTokenData().collect { isSuccess ->
                _logout.postValue(isSuccess)
            }
        }
    }
}