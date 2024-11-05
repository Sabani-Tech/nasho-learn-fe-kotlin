package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.core.widget.doOnTextChanged
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.apply {
            tvTitle.text = getString(R.string.title_sign_up)
            tvDesc.text = getString(R.string.desc_sign_up)

            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.apply {
            btnSignUp.isEnabled = false

            tilFullName.editText?.doOnTextChanged { _, _, _, _ ->
                if (tilFullName.isNotEmpty()) {
                    tilFullName.isErrorEnabled = false
                } else {
                    tilFullName.isErrorEnabled = true
                    tilFullName.error = ""
                }
                validateInput()
            }

            tilEmail.editText?.doOnTextChanged { _, _, _, _ ->
                if (tilEmail.isNotEmpty()) {
                    tilEmail.isErrorEnabled = false
                } else {
                    tilEmail.isErrorEnabled = true
                    tilEmail.error = ""
                }
                validateInput()
            }

            tilPassword.editText?.doOnTextChanged { text, _, _, _ ->
                val password = text.toString()

                if (password.length >= 8 && validatePassword(password) && pwDigits(
                        password
                    )
                ) {
                    tilPassword.isErrorEnabled = false
                } else {
                    tilPassword.isErrorEnabled = true
                    if (password.isNotEmpty() && password.length < 8) {
                        tilPassword.error = "Kata sandi harus minimal 8 karakter"
                    } else {
                        tilPassword.error = "Kata sandi harus terdiri dari huruf dan karakter"
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
                    tilPasswordConfirm.error = "Kata sandi tidak sama"
                }
                validateInput()
            }

            btnSignUp.setOnClickListener {
                val email = tilEmail.editText?.text.toString().trim()

                Toast.makeText(
                    this@SignUpActivity,
                    "Sign Up Success! \n $email",
                    Toast.LENGTH_SHORT
                )
                    .show()

                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                startActivity(intent)
                finish()


//                authViewModel.postSignup(
//                    binding.tilPassword.editText?.text.toString(),
//                    binding.tilPasswordConfirm.editText?.text.toString(),
//                    binding.tilEmail.editText?.text.toString(),
//                    binding.tilFullName.editText?.text.toString()
//                )
//                    .observe(this@SignUpActivity, Observer { result ->
//                        when (result) {
//                            is Result.Loading -> {
//                                // Show loading indicator
//                            }
//
//                            is Result.Success -> {
//                                Toast.makeText(this@SignUp, "Login successful!", Toast.LENGTH_SHORT)
//                                    .show()
//                                val intent = Intent(this@SignUp, Login::class.java)
//                                startActivity(intent)
//                                finish()
//                            }
//
//                            is Result.Error -> {
//                                Toast.makeText(this@SignUp, result.errorMessage, Toast.LENGTH_SHORT)
//                                    .show()
//                            }
//                        }
//                    })
            }
        }
    }

    private fun validateInput() {
        val email = binding.tilEmail.editText?.text.toString()
        val name = binding.tilFullName.editText?.text.toString()
        val password = binding.tilPassword.editText?.text.toString()
        val confirm = binding.tilPasswordConfirm.editText?.text.toString()

        val isEmailValid = validateEmail(email)
        val isPasswordValid = validatePassword(password)
        val isConfirmValid = confirm == password
        val isName = validateName(name)

        binding.btnSignUp.isEnabled = isEmailValid && isPasswordValid && isConfirmValid && isName
    }

    private fun validateName(name: String): Boolean = name.length in 6..30

    private fun validateEmail(email: String): Boolean =
        email.contains("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex())

    private fun validatePassword(password: String): Boolean =
        password.contains("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]{8,}\$".toRegex())

    private fun pwDigits(password: String): Boolean = password.length >= 8


}