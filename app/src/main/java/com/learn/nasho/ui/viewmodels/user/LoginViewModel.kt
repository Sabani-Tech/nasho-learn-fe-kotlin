package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.LoginResponse
import com.learn.nasho.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _login: MutableStateFlow<ResultState<LoginResponse>> =
        MutableStateFlow(ResultState.Loading)
    val login: StateFlow<ResultState<LoginResponse>>
        get() = _login


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            userRepository.loginUser(email, password)
                .onEach { result ->
                    _login.value = result
                }
                .catch { exception ->
                    _login.value = ResultState.Error(exception.message.toString())
                }
                .collect()
        }
    }
}