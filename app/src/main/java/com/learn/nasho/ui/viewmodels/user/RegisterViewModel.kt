package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.RegisterResponse
import com.learn.nasho.data.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _register: MutableLiveData<ResultState<RegisterResponse>> =
        MutableLiveData(ResultState.Loading)
    val register: LiveData<ResultState<RegisterResponse>>
        get() = _register

    fun registerUser(fullName: String, email: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            userRepository.registerUser(fullName, email, password, passwordConfirmation)
                .onStart { _register.postValue(ResultState.Loading) }
                .catch { exception ->
                    _register.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _register.postValue(result) }
        }
    }
}