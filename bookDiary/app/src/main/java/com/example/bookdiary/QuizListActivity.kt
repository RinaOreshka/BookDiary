package com.example.bookdiary

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuizListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_list)
        enableEdgeToEdge()
        val quizList: RecyclerView = findViewById(R.id.recycler_view)
        val db = DBFormer(this, null)
        val quizzes = db.getQuizzes()
        val dividerItemDecoration = DividerItemDecoration(quizList.context, DividerItemDecoration.VERTICAL)
        quizList.addItemDecoration(dividerItemDecoration)

        quizList.layoutManager = LinearLayoutManager(this)
        quizList.adapter = QuizAdapter(quizzes, this)



    }
}