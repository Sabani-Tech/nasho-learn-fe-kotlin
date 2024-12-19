package com.learn.nasho.ui.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivityMaterialDetailsBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar.toolbarTitleAppBar)
        supportActionBar?.title = getString(R.string.app_name)

        val pdfUrl =
            "https://dev-api.nasholearn.com/assets/1734514356_MATERI_13(INNA_DAN_SAUDARINYA).pdf"
        downloadAndDisplayPDF(pdfUrl, binding.pdfView, binding.progressBar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            R.id.menu_material -> {
//                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
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

}