package com.learn.nasho.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.nasho.data.remote.response.QuizDiscussionResponse
import com.learn.nasho.databinding.ActivityQuizDiscussionBinding
import com.learn.nasho.ui.adapters.QuizDiscussionAdapter
import com.learn.nasho.utils.Constants
import com.learn.nasho.utils.parcelable

class QuizDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizDiscussionBinding
    private lateinit var quizDiscussionAdapter: QuizDiscussionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resp: QuizDiscussionResponse? = intent.parcelable(Constants.DISCUSSION_DATA)
        val type = intent.getStringExtra(Constants.QUESTION_TYPE)

        if (resp != null) {
            binding.apply {
                resp.data?.let { discussionData ->
                    quizDiscussionAdapter = QuizDiscussionAdapter(discussionData)
                }

                rvDiscussion.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@QuizDiscussionActivity)
                    adapter = quizDiscussionAdapter
                }

                btnBack.setOnClickListener {
                    finish()
                }
            }
        }
    }
}