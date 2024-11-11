package com.example.bookdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aut)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPass: EditText = findViewById(R.id.user_pass)
        val buttonAuth: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        linkToReg.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonAuth.setOnClickListener {
            val username = userLogin.text.toString()
            val password = userPass.text.toString()

            if(username == "" || password == "")
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
            else {
                val db = DBFormer(this, null)
                val isAuth: Boolean = db.getUser(username, password)
                if (isAuth) {
                    Toast.makeText(this, "Пользователь авторизован: $username", Toast.LENGTH_SHORT).show()

                    userLogin.text.clear()
                    userPass.text.clear()
                }
                else{
                    Toast.makeText(this, "Пользователь не зарегистрирован!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}