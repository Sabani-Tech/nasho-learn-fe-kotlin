package com.learn.nasho.ui.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivityMaterialListBinding
import com.learn.nasho.utils.Constants

class MaterialListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getStringExtra(Constants.MATERIAL_DATA)

        binding.appBar.apply {
            tvTitle.text = getString(R.string.learn_title, data)

            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}