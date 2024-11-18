package com.learn.nasho.ui.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.learn.nasho.R
import com.learn.nasho.data.enums.CategoryType
import com.learn.nasho.data.remote.dto.CategoryDto
import com.learn.nasho.databinding.ActivityMaterialListBinding
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable

class MaterialListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data: CategoryDto? = intent.parcelable(Constants.CATEGORY_DATA)
        if (data != null) {
            binding.appBar.apply {
                tvTitle.text = getString(R.string.learn_title, data.type)

                ivBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }

            if (data.type == CategoryType.NAHWU.type) {
                binding.ivIllustration.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.img_onboard_nahwu)
                )
            } else if (data.type == CategoryType.SHARAF.type) {
                binding.ivIllustration.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.img_onboard_sharaf)
                )
            }

        }

    }
}