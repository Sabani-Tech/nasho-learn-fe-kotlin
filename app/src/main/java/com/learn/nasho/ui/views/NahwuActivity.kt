package com.learn.nasho.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivityNahwuBinding

class NahwuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNahwuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNahwuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.apply {
            tvTitle.text = getString(R.string.learn_nahwu)

            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}