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

        val buttonLists: Button = findViewById(R.id.lists_button)
        val buttonPlans: Button = findViewById(R.id.plans_button)
        val buttonGoals: Button = findViewById(R.id.goals_button)
        val buttonReviews: Button = findViewById(R.id.reviews_button)
        val buttonQuotes: Button = findViewById(R.id.quotes_button)
        val buttonLibrary: Button = findViewById(R.id.library_button)
        val buttonAddBook: Button = findViewById(R.id.add_book)
        val buttonQuiz: Button = findViewById(R.id.quiz_button)
        val buttonBook: Button = findViewById(R.id.book_button)
        val buttonPersonal: Button = findViewById(R.id.personal_button)

        buttonPersonal.setOnClickListener{
            val intent = Intent(this, PersonalMainActivity::class.java)
            startActivity(intent)
        }
        buttonQuiz.setOnClickListener{
            val intent = Intent(this, QuizMainActivity::class.java)
            startActivity(intent)
        }
        buttonBook.setOnClickListener{
            val intent = Intent(this, BookMainActivity::class.java)
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
        buttonLists.setOnClickListener{
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

    }
}