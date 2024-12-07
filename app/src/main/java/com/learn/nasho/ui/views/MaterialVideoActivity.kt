package com.learn.nasho.ui.views

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.learn.nasho.data.remote.dto.MaterialDto
import com.learn.nasho.databinding.ActivityMaterialVideoBinding
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable


class MaterialVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaterialVideoBinding

    private var fullscreenContainer: FrameLayout? = null
    private var originalSystemUiVisibility: Int = 0
    private var originalOrientation: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: MaterialDto? = intent.parcelable(Constants.MATERIAL_DATA)
        val type: String? = intent.getStringExtra(Constants.MATERIAL_TYPE)

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
                val content = data.content?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT) }
                tvDescMaterial.text = content
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
                    decorView.addView(fullscreenContainer, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
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
}