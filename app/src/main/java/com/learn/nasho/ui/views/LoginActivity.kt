package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tilEmailLogin.editText?.doOnTextChanged { text, _, _, _ ->
                val email = text.toString()
                if (validateEmail(email) || email.isEmpty()) {
                    tilEmailLogin.isErrorEnabled = false
                } else {
                    tilEmailLogin.isErrorEnabled = true
                    tilEmailLogin.error = "Email harus sesuai format penulisan"
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
                        "Password harus mengandung huruf dan angka, dan minimal 8 karakter"
                }
                validateInput()
            }

            btnLogin.setOnClickListener {
                val email = tilEmailLogin.editText?.text.toString().trim()
                val password = tilPasswordLogin.editText?.text.toString().trim()

                Toast.makeText(this@LoginActivity, "Login Berhasil! \n $email", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()


//                authViewModel.login(email, password).observe(this@Login, Observer { result ->
//                    when (result) {
//                        is Result.Loading -> {
//                            // Show loading indicator
//                        }
//
//                        is Result.Success -> {
//                            sPref.putString("token", result.data.data.accessToken)
//                            Log.d("Token", "token: ${result.data.data.accessToken}")
//                            sPref.apply()
//                            Toast.makeText(this@Login, "Login successful!", Toast.LENGTH_SHORT)
//                                .show()
//                            val intent = Intent(this@Login, Home::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
//
//                        is Result.Error -> {
//
//                            Toast.makeText(this@Login, result.errorMessage, Toast.LENGTH_SHORT)
//                                .show()
//                        }
//
//                        else -> {
//                            // Handle other cases if needed
//                        }
//                    }
//                })
            }

            tvRegister.setOnClickListener {
                Toast.makeText(this@LoginActivity, "Go to Register", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this@LoginActivity, SignUp::class.java))
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
        return email.contains("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex())

    }

    private fun validatePassword(password: String): Boolean {
        return password.contains("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]{8,}\$".toRegex())
    }
}