package com.example.bookdiary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PersonalMainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_personal_main)
        val buttonFeedback: Button = findViewById(R.id.feedback_button)
        val buttonQuit: Button = findViewById(R.id.quit_button)
        val deleteButton: Button = findViewById(R.id.delete_button)
        val buttonQuiz: Button = findViewById(R.id.quiz_button)
        val buttonBook: Button = findViewById(R.id.book_button)
        val buttonPersonal: Button = findViewById(R.id.personal_button)

        buttonQuiz.setOnClickListener{
            val intent = Intent(this, QuizMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        buttonBook.setOnClickListener{
            val intent = Intent(this, BookMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        buttonFeedback.setOnClickListener{
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }

        fun saveAuthenticationStatus(isAuthenticated: Boolean) {
            val sharedPreferences = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_AUTHENTICATED", isAuthenticated)
            editor.apply()
        }

        buttonQuit.setOnClickListener{
            saveAuthenticationStatus(false)
            val intent = Intent(this, AutActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Подтверждение")
            builder.setMessage("Вы уверены, что хотите удалить текущего пользователя?")
            fun deleteUser() {

                val db = DBFormer(this, null)
                db.deleteUser()
                Toast.makeText(this, "Пользователь удален", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AutActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            builder.setPositiveButton("Да") { dialog, which ->
                deleteUser()
            }

            builder.setNegativeButton("Нет") { dialog, which ->
                dialog.dismiss() // Просто закрываем диалог
            }


            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}