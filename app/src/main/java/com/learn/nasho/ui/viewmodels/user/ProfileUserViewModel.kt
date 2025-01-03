package com.learn.nasho.ui.viewmodels.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.learn.nasho.data.repository.UserRepository

class ProfileUserViewModel (private val userRepository: UserRepository) : ViewModel() {

    fun getProfileUserData(): LiveData<String> {
        return userRepository.getProfileUserData().asLiveData()
    }
}