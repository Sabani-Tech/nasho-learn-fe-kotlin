package com.learn.nasho.ui.viewmodels.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learn.nasho.data.repository.UserRepository
import com.learn.nasho.di.Injection

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private var userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: UserViewModelFactory? = null

        fun getInstance(context: Context): UserViewModelFactory = instance ?: synchronized(this) {
            instance ?: UserViewModelFactory(Injection.provideUserRepository(context))
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                RegisterViewModel(userRepository) as T

            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(userRepository) as T

            modelClass.isAssignableFrom(TokenAccessViewModel::class.java) ->
                TokenAccessViewModel(userRepository) as T

            modelClass.isAssignableFrom(LogoutViewModel::class.java) ->
                LogoutViewModel(userRepository) as T

            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(userRepository) as T

            else -> throw IllegalArgumentException("unknown viewmodel class: ${modelClass.name}")
        }
    }
}