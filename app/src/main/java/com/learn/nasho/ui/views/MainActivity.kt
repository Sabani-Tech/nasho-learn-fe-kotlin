package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivityMainBinding
import com.learn.nasho.utils.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get category

        binding.apply {
            btnSetting.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }

            btnStartLearNahwu.setOnClickListener {
                goToMaterialList(getString(R.string.nahwu))
            }

            btnStartLearSharaf.setOnClickListener {
                goToMaterialList(getString(R.string.sharaf))
            }

        }

    }

    private fun goToMaterialList(material: String){
        val intent = Intent(this@MainActivity, MaterialListActivity::class.java)
        intent.putExtra(Constants.MATERIAL_DATA, material)
        startActivity(intent)
    }
}