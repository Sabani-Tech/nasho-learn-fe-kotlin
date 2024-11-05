package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.learn.nasho.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSetting.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }

            btnStartLearNahwu.setOnClickListener {
                startActivity(Intent(this@MainActivity, NahwuActivity::class.java))
            }

            btnStartLearSharaf.setOnClickListener {
                startActivity(Intent(this@MainActivity, SharafActivity::class.java))
            }

        }

    }
}