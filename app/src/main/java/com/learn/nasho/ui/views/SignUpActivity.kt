package com.learn.nasho.ui.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.databinding.ActivitySignUpBinding
import com.learn.nasho.ui.viewmodels.user.RegisterViewModel
import com.learn.nasho.ui.viewmodels.user.UserViewModelFactory
import com.learn.nasho.utils.hideLoading
import com.learn.nasho.utils.showLoading
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this@SignUpActivity)
        val registerViewModel: RegisterViewModel by viewModels {
            factory
        }

        registerViewModel.register.observe(this) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    hideLoading(binding.loading)
                    val response = resultState.data
                    if (response.error == true) {
                        Toast.makeText(
                            this@SignUpActivity,
                            getString(R.string.register_failed, response.message),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@SignUpActivity, response.message,
                            Toast.LENGTH_LONG
                        ).show()
                        onBackPressedDispatcher.onBackPressed()
                    }
                }

                is ResultState.Error -> {
                    hideLoading(binding.loading)
                    Toast.makeText(
                        this,
                        getString(R.string.register_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }

        binding.appBar.apply {
            tvTitle.text = getString(R.string.title_sign_up)
            tvDesc.text = getString(R.string.desc_sign_up)

            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.apply {
            tilFullName.editText?.doOnTextChanged { text, _, _, _ ->
                val fullName = text.toString()
                if (fullName.isNotEmpty()) {
                    tilFullName.isErrorEnabled = false
                } else {
                    tilFullName.isErrorEnabled = true
                    tilFullName.error = "*Nama Lengkap tidak boleh kosong"
                }
                validateInput()
            }

            tilEmail.editText?.doOnTextChanged { text, _, _, _ ->
                val email = text.toString()
                if (validateEmail(email) || email.isEmpty()) {
                    tilEmail.isErrorEnabled = false
                } else {
                    tilEmail.isErrorEnabled = true
                    tilEmail.error = "*Email harus sesuai format penulisan"
                }
                validateInput()
            }

            tilPassword.editText?.doOnTextChanged { text, _, _, _ ->
                val password = text.toString()

                if (password.length >= 8) {
                    tilPassword.isErrorEnabled = false
                } else {
                    tilPassword.isErrorEnabled = true
                    if (password.isNotEmpty() && password.length < 8) {
                        tilPassword.error = "*Kata sandi harus minimal 8 karakter"
                    } else {
                        tilPassword.error = "*Kata sandi harus terdiri dari huruf dan karakter"
                    }
                }
                validateInput()
            }

            tilPasswordConfirm.editText?.doOnTextChanged { _, _, _, _ ->
                val password = tilPassword.editText?.text.toString()
                val confirm = tilPasswordConfirm.editText?.text.toString()
                if (confirm == password) {
                    tilPasswordConfirm.isErrorEnabled = false
                } else {
                    tilPasswordConfirm.isErrorEnabled = true
                    tilPasswordConfirm.error = "*Kata sandi tidak sama"
                }
                validateInput()
            }

            btnSignUp.setOnClickListener {
                lifecycleScope.launch {

                    val fullName = tilFullName.editText?.text.toString().trim()
                    val email = tilEmail.editText?.text.toString().trim()
                    val password = tilPassword.editText?.text.toString().trim()
                    val passwordConfirmation = tilPasswordConfirm.editText?.text.toString().trim()

                    registerViewModel.registerUser(
                        fullName = fullName,
                        email = email,
                        password = password,
                        passwordConfirmation = passwordConfirmation
                    )
                    showLoading(loading)
                }
            }
        }
    }

    private fun validateInput() {
        val email = binding.tilEmail.editText?.text.toString()
        val fullName = binding.tilFullName.editText?.text.toString()
        val password = binding.tilPassword.editText?.text.toString()
        val confirm = binding.tilPasswordConfirm.editText?.text.toString()

        val isEmailValid = validateEmail(email)
        val isPasswordValid = password.isNotEmpty()
        val isConfirmValid = confirm == password
        val isFullName = fullName.isNotEmpty()

        binding.btnSignUp.isEnabled =
            isEmailValid && isPasswordValid && isConfirmValid && isFullName
    }

    private fun validateName(name: String): Boolean = name.length in 6..30

    private fun validateEmail(email: String): Boolean {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex())
    }

    private fun validatePassword(password: String): Boolean {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$".toRegex())
    }

    private fun pwDigits(password: String): Boolean = password.length >= 8


}