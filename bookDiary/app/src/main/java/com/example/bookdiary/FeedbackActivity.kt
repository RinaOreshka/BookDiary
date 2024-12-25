package com.example.bookdiary


import java.util.*
import javax.mail.*
import javax.mail.internet.*
import android.app.Notification
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.MessagingException
import javax.mail.Authenticator
import javax.mail.Message.RecipientType
import java.util.Properties

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        enableEdgeToEdge()
        val name: EditText = findViewById(R.id.edit_name_feed)
        val email: EditText = findViewById(R.id.edit_email)
        val message: EditText = findViewById(R.id.edit_message)
        val check: CheckBox = findViewById(R.id.check_agreement)
        val button: Button = findViewById(R.id.button_submit)

        button.setOnClickListener {
            if (check.isChecked) {
                val userName = name.text.toString()
                val userEmail = email.text.toString()
                val userMessage = message.text.toString()

                // Создание объекта Feedback
                val feedback = Feedback(userName, userEmail, userMessage)

                // Сохранение в базу данных
                val databaseHelper = DBFormer(this, null)
                databaseHelper.insertFeedback(feedback)

                Toast.makeText(this, "Ваш отзыв успешно сохранен!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Пожалуйста, примите соглашение", Toast.LENGTH_SHORT).show()
            }
        }
    }
}