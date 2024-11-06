package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.GeneralResponse
import com.learn.nasho.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _register: MutableStateFlow<ResultState<GeneralResponse>> =
        MutableStateFlow(ResultState.Loading)
    val register: StateFlow<ResultState<GeneralResponse>>
        get() = _register


    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.registerUser(name, email, password)
                .onEach { result ->
                    _register.value = result
                }
                .catch { exception ->
                    _register.value = ResultState.Error(exception.message.toString())
                }
                .collect()
        }
    }
}