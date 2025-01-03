package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivitySettingsBinding
import com.learn.nasho.ui.alerts.LogoutAlert
import com.learn.nasho.ui.viewmodels.user.LogoutViewModel
import com.learn.nasho.ui.viewmodels.user.ProfileUserViewModel
import com.learn.nasho.ui.viewmodels.user.UserViewModelFactory
import com.learn.nasho.utils.getDataProfileFromJson
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var userFactory: UserViewModelFactory
    private val logoutViewModel: LogoutViewModel by viewModels {
        userFactory
    }

    private val userViewModel: ProfileUserViewModel by viewModels {
        userFactory
    }

    private val isLogout: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userFactory = UserViewModelFactory.getInstance(this@SettingsActivity)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar.toolbarTitleAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_settings)

        val dialog = LogoutAlert(logoutViewModel)


        binding.apply {

            userViewModel.getProfileUserData().observe(this@SettingsActivity) { result ->
                val profile = getDataProfileFromJson(result ?: "")
                tvNameUser.text = profile.fullName ?: getString(R.string.dummy_name_user)
                tvEmailUser.text = profile.email ?: getString(R.string.dummy_email_user)
            }


            cvAboutUs.setOnClickListener {
                startActivity(Intent(this@SettingsActivity, AboutUsActivity::class.java))
            }

            cvLogout.setOnClickListener {
                dialog.show(supportFragmentManager, "AlertLogout")
            }
        }


        lifecycleScope.launch {
            logoutViewModel.logout.observe(this@SettingsActivity) { isSuccess ->
                if (isSuccess != null) {
                    if (isSuccess == true) {
                        isLogout.value = true
                        dialog.dismissDialog()
                        startActivity(Intent(this@SettingsActivity, LoginActivity::class.java))
                        finishAffinity()
                        Toast.makeText(
                            this@SettingsActivity,
                            getString(R.string.logout_is_success), Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        isLogout.value = false
                        Toast.makeText(
                            this@SettingsActivity,
                            getString(R.string.logout_is_failed), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}