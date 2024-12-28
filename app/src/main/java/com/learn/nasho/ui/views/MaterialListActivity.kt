package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.enums.CategoryType
import com.learn.nasho.data.enums.QuestionType
import com.learn.nasho.data.enums.Status
import com.learn.nasho.data.remote.dto.CategoryDto
import com.learn.nasho.data.remote.dto.MaterialDto
import com.learn.nasho.data.remote.response.QuestionListResponse
import com.learn.nasho.databinding.ActivityMaterialListBinding
import com.learn.nasho.databinding.ItemLayoutExamBinding
import com.learn.nasho.ui.adapters.MaterialAdapter
import com.learn.nasho.ui.adapters.RecyclerViewClickListener
import com.learn.nasho.ui.viewmodels.material.CategoryDetailViewModel
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.ui.viewmodels.material.QuestionListViewModel
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable

class MaterialListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialListBinding

    private lateinit var materialAdapterPhase1: MaterialAdapter
    private lateinit var materialAdapterPhase2: MaterialAdapter

    private lateinit var factory: MaterialViewModelFactory
    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels {
        factory
    }
    private val questionListViewModel: QuestionListViewModel by viewModels {
        factory
    }

    private val categoryId: MutableLiveData<String?> = MutableLiveData("")
    private val currentPhase: MutableLiveData<Int?> = MutableLiveData(0)

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

            binding.apply {

                if (data.type == CategoryType.NAHWU.type) {
                    ivIllustration.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MaterialListActivity,
                            R.drawable.img_onboard_nahwu
                        )
                    )
                    tvMaterialListTitle2.visibility = View.VISIBLE
                    llMaterial2.visibility = View.VISIBLE
                    tvUjianListTitle2.visibility = View.VISIBLE
                    layoutExam2.root.visibility = View.VISIBLE

                    layoutExam1.tvExamTitle.text = getString(R.string.mid_exam)
                    layoutExam1.tvExamDesc.text = getString(R.string.theory, data.type)

                    layoutExam2.tvExamTitle.text = getString(R.string.end_exam)
                    layoutExam2.tvExamDesc.text = getString(R.string.theory, data.type)
                } else if (data.type == CategoryType.SHARAF.type) {
                    ivIllustration.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MaterialListActivity,
                            R.drawable.img_onboard_sharaf
                        )
                    )

                    layoutExam1.tvExamTitle.text = getString(R.string.exam_type, data.type)
                    layoutExam1.tvExamDesc.text = getString(R.string.theory, data.type)
                }

                "${data.type} (${data.typeArab})".also { tvMaterialTitle.text = it }
                tvDesc.text = data.desc

                materialAdapterPhase1 =
                    MaterialAdapter(object : RecyclerViewClickListener {
                        override fun onItemClicked(position: Int) {
                            data.type?.let {
                                handleItemClick(position, phase = 1, it)
                            }
                        }
                    })
                rvMaterial1.setHasFixedSize(true)
                rvMaterial1.layoutManager = LinearLayoutManager(this@MaterialListActivity)
                rvMaterial1.adapter = materialAdapterPhase1


                materialAdapterPhase2 =
                    MaterialAdapter(object : RecyclerViewClickListener {
                        override fun onItemClicked(position: Int) {
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

                layoutExam1.itemView.setOnClickListener {
                    val phase = 1
                    currentPhase.value = phase
                    categoryId.value?.let { id ->
                        questionListViewModel.getExamQuestions(id, phase)
                    }
                }

                layoutExam2.itemView.setOnClickListener {
                    val phase = 2
                    currentPhase.value = phase
                    categoryId.value?.let { id ->
                        questionListViewModel.getExamQuestions(id, phase)
                    }
                }

            }

            categoryDetailViewModel.categoryDetail.observe(this@MaterialListActivity) { resultState ->
                when (resultState) {
                    is ResultState.Success -> {

                        val response = resultState.data
                        if (response.error == true) {
                            Toast.makeText(
                                this@MaterialListActivity,
                                getString(R.string.material_failed, response.message),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {

                            val resultData = response.data
                            if (resultData?.materialPhase1?.size!! > 0) {
                                resultData.materialPhase1.let { materialAdapterPhase1.setItems(it) }
                                binding.tvEmptyMaterial1.visibility = View.GONE
                            } else {
                                binding.tvEmptyMaterial1.visibility = View.VISIBLE
                            }

                            if (resultData.materialPhase2?.size!! > 0) {
                                resultData.materialPhase2.let { materialAdapterPhase2.setItems(it) }
                                binding.tvEmptyMaterial2.visibility = View.GONE
                            } else {
                                binding.tvEmptyMaterial2.visibility = View.VISIBLE
                            }

                            when (resultData.status) {

                                Status.EXAM1.type -> {
                                    resultData.exam1Status?.let { passed ->
                                        lockExam(binding.layoutExam1, false, passed)
                                    }
                                    materialAdapterPhase2.setLockItem(true)
                                    resultData.exam2Status?.let { passed ->
                                        lockExam(binding.layoutExam2, true, passed)
                                    }
                                }

                                Status.EXAM2.type -> {
                                    resultData.exam1Status?.let { passed ->
                                        lockExam(binding.layoutExam1, false, passed)
                                    }
                                    materialAdapterPhase2.setLockItem(false)
                                    resultData.exam2Status?.let { passed ->
                                        lockExam(binding.layoutExam2, false, passed)
                                    }
                                }

                                else -> {
                                    materialAdapterPhase1.setLockItem(true)
                                    resultData.exam1Status?.let { passed ->
                                        lockExam(binding.layoutExam1, true, passed)
                                    }
                                    materialAdapterPhase2.setLockItem(true)
                                    resultData.exam2Status?.let { passed ->
                                        lockExam(binding.layoutExam2, true, passed)
                                    }
                                }
                            }
                        }
                    }

                    is ResultState.Error -> {
                        Toast.makeText(
                            this@MaterialListActivity,
                            getString(R.string.material_failed, resultState.message),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {}
                }
            }

            questionListViewModel.questionExamList.observe(this@MaterialListActivity) { resultState ->
                when (resultState) {
                    is ResultState.Success -> {

                        val response = resultState.data
                        val message = response.message

                        if (response.error == true) {
                            Toast.makeText(
                                this@MaterialListActivity,
                                getString(R.string.question_failed, message),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Log.d(TAG, "onCreate: questions size: ${response.data?.size}")
                            if (response.data?.size!! > 0) {
                                goToExamPage(response)
                            } else {
                                Toast.makeText(
                                    this@MaterialListActivity,
                                    "Data Ujian tidak tersedia",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    is ResultState.Error -> {
                        Toast.makeText(
                            this@MaterialListActivity,
                            getString(R.string.question_failed, resultState.message),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        categoryId.value?.let { categoryDetailViewModel.getCategoryDetailById(it) }
    }

    private fun handleItemClick(position: Int, phase: Int, type: String) {
        when (phase) {
            1 -> {
                val data = materialAdapterPhase1.getItem(position)
                goToMaterialDetail(material = data, type = type)
            }

            2 -> {
                val data = materialAdapterPhase2.getItem(position)
                goToMaterialDetail(material = data, type = type)
            }
        }
    }

    private fun lockExam(item: ItemLayoutExamBinding, lock: Boolean, passed: Boolean) {
        if (lock) {
            item.llLearLock.root.visibility = View.VISIBLE
            item.itemView.isEnabled = false
            item.ivLearnStatus.visibility = View.GONE
        } else {
            item.llLearLock.root.visibility = View.GONE
            item.itemView.isEnabled = true
            if (passed) {
                item.ivLearnStatus.visibility = View.VISIBLE
            } else {
                item.ivLearnStatus.visibility = View.GONE
            }
        }
    }

    private fun goToMaterialDetail(material: MaterialDto, type: String) {
        val intent = Intent(this@MaterialListActivity, MaterialDetailsActivity::class.java)
        intent.putExtra(Constants.MATERIAL_DATA, material)
        intent.putExtra(Constants.MATERIAL_TYPE, type)
        intent.putExtra(Constants.CATEGORY_ID, categoryId.value)
        startActivity(intent)
    }

    private fun goToExamPage(data: QuestionListResponse) {
        val intent = Intent(this@MaterialListActivity, QuizActivity::class.java)
        intent.putExtra(Constants.QUESTION_DATA, data)
        intent.putExtra(Constants.QUESTION_TYPE, QuestionType.EXAM.type)
        intent.putExtra(Constants.CATEGORY_ID, categoryId.value)
        intent.putExtra(Constants.EXAM_PHASE, currentPhase.value)
        startActivity(intent)
    }

    companion object {
        var TAG: String = MaterialListActivity::class.java.name
    }
}