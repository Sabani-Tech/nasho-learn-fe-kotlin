package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.learn.nasho.data.repository.UserRepository

class TokenAccessViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getTokenAccess(): LiveData<String> {
        return userRepository.getTokenAccess().asLiveData()
    }
}