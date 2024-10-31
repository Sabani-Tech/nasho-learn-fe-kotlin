package com.learn.nasho.ui.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivitySharafBinding

class SharafActivity : AppCompatActivity() {

    private lateinit var binding :ActivitySharafBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySharafBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}