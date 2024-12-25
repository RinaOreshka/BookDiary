package com.example.bookdiary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizMainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_main)

        val buttonList: Button = findViewById(R.id.list_button)
        val generate: Button = findViewById(R.id.create_button)
        val button: Button = findViewById(R.id.create)
        val buttonQuiz: Button = findViewById(R.id.quiz_button)
        val buttonBook: Button = findViewById(R.id.book_button)
        val buttonPersonal: Button = findViewById(R.id.personal_button)


        buttonPersonal.setOnClickListener{
            val intent = Intent(this, PersonalMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        buttonBook.setOnClickListener{
            val intent = Intent(this, BookMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        buttonList.setOnClickListener{
            val intent = Intent(this, QuizListActivity::class.java)
            startActivity(intent)
        }
        generate.setOnClickListener{
            val intent = Intent(this, QuizActActivity::class.java)
            intent.putExtra("quiz_id", "3")
            startActivity(intent)
        }
        button.setOnClickListener {
            val db = DBFormer(this, null)
            val check = db.isQuizEmpty()
            if (check) {
                db.createQuizzez()
                db.fillDatabase()
                db.createAnswers()
            }
        }
    }
}