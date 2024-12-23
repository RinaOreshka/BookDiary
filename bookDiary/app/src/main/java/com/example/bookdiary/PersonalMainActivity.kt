package com.example.bookdiary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PersonalMainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_personal_main)
        val buttonPersonalData: Button = findViewById(R.id.personal_data_button)
        val buttonSettings: Button = findViewById(R.id.settings_button)
        val buttonFeedback: Button = findViewById(R.id.feedback_button)
        val buttonQuit: Button = findViewById(R.id.quit_button)
        val buttonDelete: Button = findViewById(R.id.delete_button)
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
        buttonPersonalData.setOnClickListener{
            val intent = Intent(this, PersonalDataActivity::class.java)
            startActivity(intent)
        }
        buttonSettings.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        buttonFeedback.setOnClickListener{
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }
        buttonQuit.setOnClickListener{
            val sharedPreferences = getSharedPreferences("app_pref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("user_id")
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}