package com.learn.nasho.ui.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.learn.nasho.R
import com.learn.nasho.databinding.ActivityQuizDiscussionBinding

class QuizDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizDiscussionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}