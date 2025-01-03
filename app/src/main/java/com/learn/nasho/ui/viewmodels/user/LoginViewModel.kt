package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.LoginResponse
import com.learn.nasho.data.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _login: MutableLiveData<ResultState<LoginResponse>> =
        MutableLiveData(ResultState.Loading)
    val login: LiveData<ResultState<LoginResponse>>
        get() = _login

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            userRepository.loginUser(email, password)
                .onStart { _login.postValue(ResultState.Loading) }
                .catch { exception ->
                    _login.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _login.postValue(result) }
        }
    }
}