package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.enums.QuestionType
import com.learn.nasho.data.remote.dto.MaterialDto
import com.learn.nasho.data.remote.response.QuestionListResponse
import com.learn.nasho.data.remote.response.QuizDiscussionResponse
import com.learn.nasho.databinding.ActivityMaterialDetailsBinding
import com.learn.nasho.ui.viewmodels.material.MaterialDetailViewModel
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.ui.viewmodels.material.QuestionDiscussionViewModel
import com.learn.nasho.ui.viewmodels.material.QuestionListViewModel
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest

class MaterialDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaterialDetailsBinding

    private lateinit var factory: MaterialViewModelFactory
    private val materialDetailViewModel: MaterialDetailViewModel by viewModels {
        factory
    }
    private val questionListViewModel: QuestionListViewModel by viewModels {
        factory
    }
    private val questionDiscussionViewModel: QuestionDiscussionViewModel by viewModels {
        factory
    }

    private lateinit var dataMaterial: MaterialDto
    private lateinit var typeMaterial: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = MaterialViewModelFactory.getInstance(this@MaterialDetailsActivity)

        binding = ActivityMaterialDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: MaterialDto? = intent.parcelable(Constants.MATERIAL_DATA)
        val type: String? = intent.getStringExtra(Constants.MATERIAL_TYPE)
        val categoryId: String? = intent.getStringExtra(Constants.CATEGORY_ID)


        if (data != null) {
            dataMaterial = data
            if (type != null) {
                typeMaterial = type
            }
            setSupportActionBar(binding.appBar.toolbarTitleAppBar)
            supportActionBar?.title = data.title

            materialDetailViewModel.materialDetail.observe(this@MaterialDetailsActivity) { resultState ->
                when (resultState) {
                    is ResultState.Success -> {

                        val response = resultState.data
                        val message = response.message

                        if (response.error == true) {
                            Toast.makeText(
                                this@MaterialDetailsActivity,
                                getString(R.string.material_failed, message),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Log.d(TAG, "onCreate: message: $message")
                            val material = response.data
                            material?.let { materialData ->

                                materialData.fileUri?.let { url ->
                                    downloadAndDisplayPDF(url, binding.pdfView, binding.progressBar)
                                }

                                /*materialData.quizStatus?.let {
                                    if (materialData.quizStatus) {
                                        binding.btnResultQuiz.visibility = View.VISIBLE
                                    } else {
                                        binding.btnResultQuiz.visibility = View.GONE
                                    }
                                }*/
                            }
                        }
                    }

                    is ResultState.Error -> {
                        Toast.makeText(
                            this@MaterialDetailsActivity,
                            getString(R.string.material_failed, resultState.message),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {}
                }
            }

            questionListViewModel.questionQuizList.observe(this@MaterialDetailsActivity) { resultState ->
                when (resultState) {
                    is ResultState.Success -> {

                        val response = resultState.data
                        val message = response.message

                        if (response.error == true) {
                            Toast.makeText(
                                this@MaterialDetailsActivity,
                                getString(R.string.question_failed, message),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Log.d(TAG, "onCreate: questions size: ${response.data?.size}")
                            if (categoryId != null && data.id != null) {
                                if (response.data?.size!! > 0) {
                                    goToQuizPage(response, categoryId, data.id)
                                } else {
                                    Toast.makeText(
                                        this@MaterialDetailsActivity,
                                        "Data Ujian tidak tersedia",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }

                    is ResultState.Error -> {
                        Toast.makeText(
                            this@MaterialDetailsActivity,
                            getString(R.string.question_failed, resultState.message),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {}
                }
            }

            questionDiscussionViewModel.questionQuizDiscussion.observe(this@MaterialDetailsActivity) { resultState ->
                when (resultState) {
                    is ResultState.Success -> {

                        val response = resultState.data
                        val message = response.message

                        if (response.error == true) {
                            Toast.makeText(
                                this@MaterialDetailsActivity,
                                getString(R.string.question_failed, message),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Log.d(TAG, "onCreate: questions size: ${response.data?.size}")
                            if (categoryId != null && data.id != null) {
                                if (response.data?.size!! > 0) {
                                    type?.let { typeData -> goToDiscussionPage(typeData, response) }
                                } else {
                                    Toast.makeText(
                                        this@MaterialDetailsActivity,
                                        "Data Ujian tidak tersedia",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }

                    is ResultState.Error -> {
                        Toast.makeText(
                            this@MaterialDetailsActivity,
                            getString(R.string.question_failed, resultState.message),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {}
                }
            }

            binding.btnResultQuiz.setOnClickListener {
                if (categoryId != null && data.id != null) {
                    questionDiscussionViewModel.getQuizDiscussion(categoryId, data.id)
                }
            }

            binding.btnStartQuiz.setOnClickListener {
                if (categoryId != null && data.id != null) {
                    questionListViewModel.getQuizQuestions(categoryId, data.id)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dataMaterial.id?.let { id -> materialDetailViewModel.getMaterialDetail(id) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            R.id.menu_material -> {
                goToMaterialVideo(dataMaterial, typeMaterial)
                Toast.makeText(this@MaterialDetailsActivity, "Masuk video", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.material_menu, menu)
        return true
    }

    private fun downloadAndDisplayPDF(url: String, pdfView: PDFView, progressBar: ProgressBar) {
        val fileName = getFileNameFromUrl(url)
        val file = File(cacheDir, fileName)

        if (file.exists()) {
            // File sudah ada di cache
            runOnUiThread {
                pdfView.fromFile(file).load()
            }
        } else {
            // Tampilkan ProgressBar
            runOnUiThread { progressBar.visibility = View.VISIBLE }

            // File belum ada, download dari URL
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    runOnUiThread { progressBar.visibility = View.GONE }
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body?.let { body ->
                        val inputStream = body.byteStream()
                        val outputStream = FileOutputStream(file)

                        inputStream.use { input -> outputStream.use { output -> input.copyTo(output) } }

                        // Tampilkan PDF setelah download selesai
                        runOnUiThread {
                            // Sembunyikan ProgressBar
                            progressBar.visibility = View.GONE
                            // Tampilkan PDF
                            pdfView.fromFile(file).load()
                        }
                    }
                }
            })

            // Bersihkan file cache lama
            cleanOldCacheFiles()
        }
    }

    private fun getFileNameFromUrl(url: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(url.toByteArray())
        val hashBytes = md5.digest()
        return hashBytes.joinToString("") { "%02x".format(it) } + ".pdf"
    }

    private fun cleanOldCacheFiles() {
        val files = cacheDir.listFiles()
        if (files != null && files.size > 10) {
            files.sortedBy { it.lastModified() }
                .take(files.size - 10)
                .forEach { it.delete() }
        }
    }

    private fun goToMaterialVideo(material: MaterialDto, type: String) {
        val intent = Intent(this@MaterialDetailsActivity, MaterialVideoActivity::class.java)
        intent.putExtra(Constants.MATERIAL_DATA, material)
        intent.putExtra(Constants.MATERIAL_TYPE, type)
        startActivity(intent)
    }

    private fun goToDiscussionPage(type: String, data: QuizDiscussionResponse) {
        val intent = Intent(this@MaterialDetailsActivity, QuizDiscussionActivity::class.java)
        intent.putExtra(Constants.DISCUSSION_DATA, data)
        intent.putExtra(Constants.QUESTION_TYPE, type)
        startActivity(intent)
        finish()
    }

    private fun goToQuizPage(data: QuestionListResponse, categoryId: String, materialId: String) {
        val intent = Intent(this@MaterialDetailsActivity, QuizActivity::class.java)
        intent.putExtra(Constants.QUESTION_DATA, data)
        intent.putExtra(Constants.QUESTION_TYPE, QuestionType.QUIZ.type)
        intent.putExtra(Constants.CATEGORY_ID, categoryId)
        intent.putExtra(Constants.MATERIAL_ID, materialId)
        startActivity(intent)
    }

    companion object {
        var TAG: String = MaterialDetailsActivity::class.java.name
    }

}