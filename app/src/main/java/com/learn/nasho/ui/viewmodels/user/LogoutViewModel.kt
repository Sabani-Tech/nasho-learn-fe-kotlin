package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogoutViewModel(private val userRepository: UserRepository) : ViewModel() {

//    private val _logout: MutableStateFlow<Boolean?> = MutableStateFlow(null)
//    val logout: StateFlow<Boolean?>
//        get() = _logout
//
//    fun logoutUser() {
//        viewModelScope.launch {
//            userRepository.setLogout(isLogout = true).collect {
//                if (it) {
//                    userRepository.clearTokenData().collect { isSuccess ->
//                        _logout.value = isSuccess
//                    }
//                }
//            }
//        }
//    }

    private val _logout: MutableLiveData<Boolean?> = MutableLiveData()
    val logout: LiveData<Boolean?>
        get() = _logout

    fun logoutUser() {
        viewModelScope.launch {
            userRepository.setLogout(isLogout = true).collect { isLogoutSuccessful ->
                if (isLogoutSuccessful) {
                    userRepository.clearTokenData().collect { isSuccess ->
                        _logout.postValue(isSuccess)
                    }
                }
            }
        }
    }
}