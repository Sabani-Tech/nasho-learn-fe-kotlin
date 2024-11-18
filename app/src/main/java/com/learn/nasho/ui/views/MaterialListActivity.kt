package com.learn.nasho.ui.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.enums.CategoryType
import com.learn.nasho.data.remote.dto.CategoryDto
import com.learn.nasho.databinding.ActivityMaterialListBinding
import com.learn.nasho.ui.viewmodels.material.CategoryListViewModel
import com.learn.nasho.ui.viewmodels.material.MaterialListViewModel
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable

class MaterialListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialListBinding

    private lateinit var factory: MaterialViewModelFactory
    private val materialListViewModel: MaterialListViewModel by viewModels {
        factory
    }

    private val categoryId: MutableLiveData<String?> = MutableLiveData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = MaterialViewModelFactory.getInstance(this@MaterialListActivity)

        binding = ActivityMaterialListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: CategoryDto? = intent.parcelable(Constants.CATEGORY_DATA)
        if (data != null) {

            categoryId.value = data.id

            binding.appBar.apply {
                tvTitle.text = getString(R.string.learn_title, data.type)

                ivBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }

//            materialListViewModel.materialList.observe(this@MaterialListActivity) { resultState ->
//                when (resultState) {
//                    is ResultState.Success -> {
//                        val response = resultState.data
//                        if (response.error == true) {
//                            Toast.makeText(
//                                this@MaterialListActivity,
//                                getString(R.string.category_failed, response.message),
//                                Toast.LENGTH_LONG
//                            ).show()
//                        } else {

//                            response.data?.forEach { category ->
//                                with(binding) {
//                                    Log.d("TAG", "onCreate: category type: ${category.type}")
//                                    if (category.type == CategoryType.SHARAF.type.lowercase()) {
//                                        sharaf = category
//                                        tvDescSharaf.text = sharaf.desc
//                                        btnStartLearSharaf.isEnabled = true
//                                    }
//
//                                    if (category.type == CategoryType.NAHWU.type.lowercase()) {
//                                        nahwu = category
//                                        tvDescNahwu.text = nahwu.desc
//                                        btnStartLearNahwu.isEnabled = true
//                                    }
//
//                                }
//                            }
//                        }
//                    }
//
//                    is ResultState.Error -> {
//                        Toast.makeText(
//                            this@MaterialListActivity,
//                            getString(R.string.category_failed, resultState.message),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//
//                    else -> {}
//                }
//
//            }

            if (data.type == CategoryType.NAHWU.type) {
                binding.ivIllustration.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.img_onboard_nahwu)
                )
            } else if (data.type == CategoryType.SHARAF.type) {
                binding.ivIllustration.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.img_onboard_sharaf)
                )
            }

            binding.apply {
                "${data.type} (${data.id})".also { tvMaterialTitle.text = it }
            }

        }
    }

    override fun onResume() {
        super.onResume()
//        categoryId.value?.let { materialListViewModel.getMaterialListByCategory(it) }
    }
}