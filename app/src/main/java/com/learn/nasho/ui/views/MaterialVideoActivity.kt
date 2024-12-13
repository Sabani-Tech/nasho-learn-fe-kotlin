package com.learn.nasho.ui.views

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.learn.nasho.R
import com.learn.nasho.data.ResultState
import com.learn.nasho.data.enums.QuestionType
import com.learn.nasho.data.remote.dto.MaterialDto
import com.learn.nasho.data.remote.response.QuestionListResponse
import com.learn.nasho.databinding.ActivityMaterialVideoBinding
import com.learn.nasho.ui.viewmodels.material.MaterialViewModelFactory
import com.learn.nasho.ui.viewmodels.material.QuestionListViewModel
import com.learn.nasho.ui.views.MaterialListActivity.Companion.TAG
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable


class MaterialVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaterialVideoBinding

    private lateinit var factory: MaterialViewModelFactory
    private val questionListViewModel: QuestionListViewModel by viewModels {
        factory
    }

    private var fullscreenContainer: FrameLayout? = null
    private var originalSystemUiVisibility: Int = 0
    private var originalOrientation: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = MaterialViewModelFactory.getInstance(this@MaterialVideoActivity)

        binding = ActivityMaterialVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: MaterialDto? = intent.parcelable(Constants.MATERIAL_DATA)
        val type: String? = intent.getStringExtra(Constants.MATERIAL_TYPE)
        val categoryId: String? = intent.getStringExtra(Constants.CATEGORY_ID)

        if (data != null) {

            binding.appBar.apply {
                ivBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }

                tvTitle.text = data.title
                tvDesc.text = type
            }

            binding.apply {
                initializeWebView(wvVideo, data.embed)
                val content = data.content?.let {
                    HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT)
                }
                tvDescMaterial.text = content

                btnStartQuiz.setOnClickListener {
                    if (categoryId != null && data.id != null) {
                        questionListViewModel.getQuizQuestions(categoryId, data.id)
                    }
                }
            }
        }

        questionListViewModel.questionQuizList.observe(this@MaterialVideoActivity) { resultState ->
            when (resultState) {
                is ResultState.Success -> {

                    val response = resultState.data
                    val message = response.message

                    if (response.error == true) {
                        Toast.makeText(
                            this@MaterialVideoActivity,
                            getString(R.string.question_failed, message),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.d(TAG, "onCreate: questions size: ${response.data?.size}")
                        goToQuizPage(response)
                    }
                }

                is ResultState.Error -> {
                    Toast.makeText(
                        this@MaterialVideoActivity,
                        getString(R.string.question_failed, resultState.message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initializeWebView(webView: WebView, videoLink: String?) {
        if (videoLink.isNullOrEmpty()) {
            // Jika videoLink null atau kosong, sembunyikan WebView
            webView.visibility = View.GONE
            // Tampilkan pesan bahwa video tidak tersedia
            binding.tvDescMaterial.text = "Video is not available"
            return
        }

        webView.settings.apply {
            javaScriptEnabled = true
            mediaPlaybackRequiresUserGesture = false
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View, callback: CustomViewCallback) {
                if (fullscreenContainer == null) {
                    fullscreenContainer = FrameLayout(this@MaterialVideoActivity)
                    val decorView = window.decorView as FrameLayout
                    decorView.addView(
                        fullscreenContainer,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }

                fullscreenContainer?.apply {
                    visibility = View.VISIBLE
                    removeAllViews()
                    addView(view)
                }

                originalSystemUiVisibility = window.decorView.systemUiVisibility
                originalOrientation = requestedOrientation

                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            override fun onHideCustomView() {
                fullscreenContainer?.apply {
                    visibility = View.GONE
                    removeAllViews()
                }

                window.decorView.systemUiVisibility = originalSystemUiVisibility
                requestedOrientation = originalOrientation
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                // Jika terjadi error saat memuat halaman, sembunyikan WebView
                webView.visibility = View.GONE
                // Tampilkan pesan kesalahan
                binding.tvDescMaterial.text = "Webpage not available"
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }

        webView.loadUrl(videoLink)
    }

    private fun goToQuizPage(data: QuestionListResponse) {
        val intent = Intent(this@MaterialVideoActivity, QuizActivity::class.java)
        intent.putExtra(Constants.QUESTION_DATA, data)
        intent.putExtra(Constants.QUESTION_TYPE, QuestionType.QUIZ.type)
        startActivity(intent)
    }
}