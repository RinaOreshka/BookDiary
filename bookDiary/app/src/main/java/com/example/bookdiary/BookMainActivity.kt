package com.example.bookdiary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookMainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_main)

        val buttonGoals: Button = findViewById(R.id.goals_button)
        val buttonQuotes: Button = findViewById(R.id.quotes_button)
        val buttonLibrary: Button = findViewById(R.id.library_button)
        val buttonAddBook: Button = findViewById(R.id.add_book)
        val buttonQuiz: Button = findViewById(R.id.quiz_button)
        val buttonBook: Button = findViewById(R.id.book_button)
        val buttonPersonal: Button = findViewById(R.id.personal_button)

        buttonPersonal.setOnClickListener{
            val intent = Intent(this, PersonalMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        buttonQuiz.setOnClickListener{
            val intent = Intent(this, QuizMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        buttonAddBook.setOnClickListener{
            val intent = Intent(this, BookFormActivity::class.java)
            startActivity(intent)
        }
        buttonLibrary.setOnClickListener{
            val intent = Intent(this, LibraryActivity::class.java)
            startActivity(intent)
        }
        buttonQuotes.setOnClickListener{
            val intent = Intent(this, QuotesListActivity::class.java)
            startActivity(intent)
        }
        buttonGoals.setOnClickListener{
            val intent = Intent(this, GoalsListActivity::class.java)
            startActivity(intent)
        }
    }
}