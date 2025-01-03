package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.response.ProfileResponse
import com.learn.nasho.data.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _profile: MutableLiveData<ResultState<ProfileResponse>> =
        MutableLiveData(ResultState.Loading)
    val profile: LiveData<ResultState<ProfileResponse>>
        get() = _profile

    fun getProfileUser() {
        viewModelScope.launch {
            userRepository.getProfileUser()
                .onStart { _profile.postValue(ResultState.Loading) }
                .catch { exception ->
                    _profile.postValue(ResultState.Error(exception.message.toString()))
                }
                .collectLatest { result -> _profile.postValue(result) }
        }
    }
}