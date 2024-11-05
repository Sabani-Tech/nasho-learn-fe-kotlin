package com.learn.nasho.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivitySettingsBinding
import com.learn.nasho.ui.alerts.LogoutAlert

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar.toolbarTitleAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_settings)


        binding.apply {
            tvNameUser.text = getString(R.string.dummy_name_user)
            tvEmailUser.text = getString(R.string.dummy_email_user)


            cvLogout.setOnClickListener {
                val dialog = LogoutAlert()
                dialog.show(supportFragmentManager, "AlertLogout")
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