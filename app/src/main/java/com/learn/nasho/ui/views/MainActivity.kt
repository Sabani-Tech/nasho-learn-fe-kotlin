package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.remote.dto.CategoryDto
import com.learn.nasho.databinding.ActivityMainBinding
import com.learn.nasho.ui.viewmodels.material.CategoryListViewModel
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.utils.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get category
        val factory: MaterialViewModelFactory =
            MaterialViewModelFactory.getInstance(this@MainActivity)
        val categoryListViewModel: CategoryListViewModel by viewModels {
            factory
        }

        var nahwu = CategoryDto()
        var sharaf = CategoryDto()

        categoryListViewModel.getCategoryList()
        categoryListViewModel.categoryList.observe(this@MainActivity) { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    val response = resultState.data
                    if (response.error == true) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.category_failed, response.message),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {

                        response.data?.forEach { category ->
                            with(binding) {
                                Log.d("TAG", "onCreate: category type: ${category.type}")
                                if (category.type == getString(R.string.sharaf).lowercase()) {
                                    sharaf = category
                                    tvDescSharaf.text = sharaf.desc
                                    btnStartLearSharaf.isEnabled = true
                                }

                                if (category.type == getString(R.string.nahwu).lowercase()) {
                                    nahwu = category
                                    tvDescNahwu.text = nahwu.desc
                                    btnStartLearNahwu.isEnabled = true
                                }

                            }
                        }
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.category_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }

        }

        binding.apply {
            btnSetting.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }

            btnStartLearNahwu.setOnClickListener {
                goToMaterialList(nahwu)
            }

            btnStartLearSharaf.setOnClickListener {
                goToMaterialList(sharaf)
            }

        }

    }

    private fun goToMaterialList(category: CategoryDto) {
        val intent = Intent(this@MainActivity, MaterialListActivity::class.java)
        intent.putExtra(Constants.CATEGORY_DATA, category)
        startActivity(intent)
    }
}