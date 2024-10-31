package com.learn.nasho.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.learn.nasho.databinding.ActivityMaterialVideoBinding

class MaterialVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaterialVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}