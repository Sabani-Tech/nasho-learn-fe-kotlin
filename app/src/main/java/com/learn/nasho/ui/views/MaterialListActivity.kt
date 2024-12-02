package com.learn.nasho.ui.views

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.enums.CategoryType
import com.learn.nasho.data.remote.dto.CategoryDto
import com.learn.nasho.data.remote.dto.MaterialDto
import com.learn.nasho.databinding.ActivityMaterialListBinding
import com.learn.nasho.ui.adapters.MaterialAdapter
import com.learn.nasho.ui.adapters.RecyclerViewClickListener
import com.learn.nasho.ui.viewmodels.material.CategoryListViewModel
import com.learn.nasho.ui.viewmodels.material.MaterialListViewModel
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable

class MaterialListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialListBinding

    private lateinit var materialAdapterPhase1: MaterialAdapter
    private lateinit var materialAdapterPhase2: MaterialAdapter

//    private lateinit var factory: MaterialViewModelFactory
//    private val materialListViewModel: MaterialListViewModel by viewModels {
//        factory
//    }

    private val categoryId: MutableLiveData<String?> = MutableLiveData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        factory = MaterialViewModelFactory.getInstance(this@MaterialListActivity)

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

            binding.apply {

                if (data.type == CategoryType.NAHWU.type) {
                    ivIllustration.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MaterialListActivity,
                            R.drawable.img_onboard_nahwu
                        )
                    )
                } else if (data.type == CategoryType.SHARAF.type) {
                    ivIllustration.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MaterialListActivity,
                            R.drawable.img_onboard_sharaf
                        )
                    )
                }

                "${data.type} (${data.id})".also { tvMaterialTitle.text = it }
                tvDesc.text = data.desc

                // Setup Phase 1 Adapter
                materialAdapterPhase1 =
                    MaterialAdapter(object : RecyclerViewClickListener {
                        override fun onItemClicked(position: Int) {
                            // Handle click for Phase 1
                            data.type?.let {
                                handleItemClick(position, phase = 1, it)
                            }
                        }
                    })
                rvMaterial1.setHasFixedSize(true)
                rvMaterial1.layoutManager = LinearLayoutManager(this@MaterialListActivity)
                rvMaterial1.adapter = materialAdapterPhase1


                // Setup Phase 2 Adapter
                materialAdapterPhase2 =
                    MaterialAdapter(object : RecyclerViewClickListener {
                        override fun onItemClicked(position: Int) {
                            // Handle click for Phase 2
                            data.type?.let {
                                handleItemClick(position, phase = 2, it)
                            }
                        }
                    })
                rvMaterial2.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MaterialListActivity)
                    adapter = materialAdapterPhase2
                }

                data.material?.let { materialAdapterPhase1.setItems(it) }

            }

//            materialListViewModel.materialList.observe(this@MaterialListActivity) { resultState ->
//                when (resultState) {
//                    is ResultState.Success -> {
//                        val response = resultState.data
//                        if (response.error == true) {
//                            Toast.makeText(
//                                this@MaterialListActivity,
//                                getString(R.string.material_failed, response.message),
//                                Toast.LENGTH_LONG
//                            ).show()
//                        } else {
//                            response.data?.let {
//                                Log.d("MaterialListActivity", "Data: ${it.size}")
//                                materialAdapterPhase1.setItems(it)
//                            }
//                        }
//                    }
//
//                    is ResultState.Error -> {
//                        Toast.makeText(
//                            this@MaterialListActivity,
//                            getString(R.string.material_failed, resultState.message),
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//
//                    else -> {}
//                }
//            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        categoryId.value?.let { materialListViewModel.getMaterialListByCategory(it) }
//    }

    // Handle different list clicks based on the phase
    private fun handleItemClick(position: Int, phase: Int, type: String) {
        // Do something different based on phase
        when (phase) {
            1 -> {
                // Handle click for Phase 1 item
                Toast.makeText(this, "Clicked item in Phase 1: $position", Toast.LENGTH_SHORT)
                    .show()
                val material = materialAdapterPhase1.getItem(position)
                Log.d("MaterialListActivity", "handleItemClick: data $material")
                goToMaterialDetail(material = material, type)
            }

            2 -> {
                // Handle click for Phase 2 item
                Toast.makeText(this, "Clicked item in Phase 2: $position", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun goToMaterialDetail(material: MaterialDto, type: String) {
        val intent = Intent(this@MaterialListActivity, MaterialVideoActivity::class.java)
        intent.putExtra(Constants.MATERIAL_DATA, material)
        intent.putExtra(Constants.MATERIAL_TYPE, type)
        startActivity(intent)
    }
}