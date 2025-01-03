package com.learn.nasho.ui.views

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivitySplashScreenBinding
import com.learn.nasho.ui.viewmodels.user.TokenAccessViewModel
import com.learn.nasho.ui.viewmodels.user.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySplashScreenBinding

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory: UserViewModelFactory =
            UserViewModelFactory.getInstance(this@SplashScreenActivity)
        val tokenAccessViewModel: TokenAccessViewModel by viewModels {
            factory
        }


        displayAppVersion()

//        activityScope.launch(Dispatchers.Main) {
//            delay(2000)
//            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        tokenAccessViewModel.getTokenAccess().observe(this@SplashScreenActivity) {
            if (it.isNullOrEmpty()) {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            }
            finish()
        }

    }

    private fun displayAppVersion() {
        binding.apply {
            try {
                val pInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
                tvAppVersion.text = buildString {
                    append("V ")
                    append(pInfo.versionName)
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}