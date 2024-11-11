package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.databinding.ActivityLoginBinding
import com.learn.nasho.ui.viewmodels.user.LoginViewModel
import com.learn.nasho.ui.viewmodels.user.ProfileViewModel
import com.learn.nasho.ui.viewmodels.user.UserViewModelFactory
import com.learn.nasho.utils.hideLoading
import com.learn.nasho.utils.showLoading
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this@LoginActivity)
        val loginViewModel: LoginViewModel by viewModels {
            factory
        }
        val profileViewModel: ProfileViewModel by viewModels {
            factory
        }

        loginViewModel.login.observe(this) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    hideLoading(binding.loading)
                    val response = resultState.data
                    if (response.error == true) {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.login_failed, response.message),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        profileViewModel.getProfileUser()
                    }
                }

                is ResultState.Error -> {
                    hideLoading(binding.loading)
                    Toast.makeText(
                        this,
                        getString(R.string.login_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }

        profileViewModel.profile.observe(this) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    hideLoading(binding.loading)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                is ResultState.Error -> {
                    hideLoading(binding.loading)
                    Toast.makeText(
                        this,
                        getString(R.string.login_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }

        binding.apply {
            tilEmailLogin.editText?.doOnTextChanged { text, _, _, _ ->
                val email = text.toString()
                if (validateEmail(email) || email.isEmpty()) {
                    tilEmailLogin.isErrorEnabled = false
                } else {
                    tilEmailLogin.isErrorEnabled = true
                    tilEmailLogin.error = "*Email harus sesuai format penulisan"
                }
                validateInput()
            }

            tilPasswordLogin.editText?.doOnTextChanged { text, _, _, _ ->
                val password = text.toString()
                if (validatePassword(password) || password.isEmpty()) {
                    tilPasswordLogin.isErrorEnabled = false
                } else {
                    tilPasswordLogin.isErrorEnabled = true
                    tilPasswordLogin.error =
                        "*Password harus mengandung huruf dan angka, dan minimal 8 karakter"
                }
                validateInput()
            }

            btnLogin.setOnClickListener {
                lifecycleScope.launch {
                    val email = tilEmailLogin.editText?.text.toString().trim()
                    val password = tilPasswordLogin.editText?.text.toString().trim()

                    loginViewModel.loginUser(email, password)
                    showLoading(loading)
                }
            }

            tvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }
        }
    }

    private fun validateInput() {
        val email = binding.tilEmailLogin.editText?.text.toString()
        val password = binding.tilPasswordLogin.editText?.text.toString()

        val isEmailValid = validateEmail(email)
        val isPasswordValid = validatePassword(password)

        binding.btnLogin.isEnabled = isEmailValid && isPasswordValid
    }

    private fun validateEmail(email: String): Boolean {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex())

    }

    private fun validatePassword(password: String): Boolean {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$".toRegex())
    }
}